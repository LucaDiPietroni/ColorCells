package application;

import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ReadableArtwork {
	private double width; //szerokoœæ
	private double height; // wysokoœæ
	private Canvas canvas;
	private GraphicsContext graphCntx;
	private Image image;
	private PixelReader pxlRdr;
	
	//Konstruktor klasy obrazu z którego mo¿na zczytywaæ grafikê
	public ReadableArtwork(double width, double height) {
		this.width = width;
		this.height = height;
		this.canvas = new Canvas(width, height);
		this.graphCntx = canvas.getGraphicsContext2D();
	}
	//standardowy getter
	public Canvas getCanvasFromArt() {
		return canvas;
	}
	//standardowy getter
	public GraphicsContext getGraphicsCntxFromArt() {
		return graphCntx;
	}
	//standardowy getter
	public Image getImageFromArt() {
		return image;
	}
	//standardowy getter
	public PixelReader getPixelReaderFromArt() {
		return pxlRdr;
	}
	//metoda do de facto ryswania obrazka na p³ótnie
	public void setImage(File imageFile) {
		image = new Image(imageFile.toURI().toString(), width, height, false, true);
		graphCntx.drawImage(image, 0, 0);
		pxlRdr = image.getPixelReader();
	}
	//Metoda czyszcz¹ca obrazek
	public void clearReadableArtwork() {
		graphCntx.clearRect(0, 0, 300, 360);
		pxlRdr = null;
		
	}
}
