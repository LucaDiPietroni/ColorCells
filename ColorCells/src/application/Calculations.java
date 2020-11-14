package application;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public interface Calculations {
	
	//Metoda obliczaj¹ca œredni¹ sk³adowych czerwonych w obrazku
	public default double avgRed(WritableImage wrtImage) {
		
		double width = wrtImage.getWidth();
		double height = wrtImage.getHeight();
		double redSum = 0;
		
		try {
			PixelReader SeekRdr = wrtImage.getPixelReader();
			Color imgColor;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					imgColor = SeekRdr.getColor(i, j);
					redSum = redSum + imgColor.getRed();
				}
			}
		}
		catch (Exception e) {
			return 0;
		}
		return redSum/(width * height);
	}
	//Metoda przerysowuj¹ca grafikê z jednego obrazu na drugi
	public default void redrawOn(int startX, int startY, PixelReader reader, PixelWriter writer) {
		for(int x = 0; x < 41; x++) {
			for(int y = 0; y < 41;y++) {
				Color color = reader.getColor(x + startX, y + startY);
				writer.setColor(x, y, Color.color(color.getRed(), color.getGreen(), color.getBlue()));
			}
		}
	}
	//Metoda zamieniaj¹ca grafiki na dwóch obrazach
	public default void reverse(WritableArtwork oneImg, WritableArtwork buffor, WritableArtwork otherImg) {
		redrawOn(0, 0, oneImg.getPixelReaderFromArt(), buffor.getPixelWriterFromArt());
		redrawOn(0, 0, otherImg.getPixelReaderFromArt(), oneImg.getPixelWriterFromArt());
		redrawOn(0, 0, buffor.getPixelReaderFromArt(), otherImg.getPixelWriterFromArt());
		oneImg.setImage(oneImg.getWritableImgFromArt());
	}
}
