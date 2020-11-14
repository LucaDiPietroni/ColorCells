package application;

import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class WritableArtwork implements Calculations{
	private double width; //szerokoœæ
	private double height; // wysokoœæ
	private Canvas canvas;
	private GraphicsContext graphCntx;
	private WritableImage wrtImage;
	private PixelWriter pxlWrt;
	private PixelReader pxlRdr;
	
	public WritableArtwork(double width, double height) {
		this.canvas = new Canvas(width, height);
		this.graphCntx = canvas.getGraphicsContext2D();
		this.wrtImage = new WritableImage((int)width, (int)height);
		this.pxlRdr = wrtImage.getPixelReader();
		this.pxlWrt = wrtImage.getPixelWriter();
		
	}
	//standardowy getter
	public double getWidth() {
		return width;
	}
	//standardowy getter
	public double getHeight() {
		return height;
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
	public WritableImage getWritableImgFromArt() {
		return wrtImage;
	}
	//standardowy getter
	public PixelWriter getPixelWriterFromArt() {
		return pxlWrt;
	}
	//standardowy getter
	public PixelReader getPixelReaderFromArt() {
		return pxlRdr;
	}
	//metoda do de facto ryswania obrazka na p³ótnie
	public void setImage(Image inputImage) {
		graphCntx.drawImage(inputImage, 0, 0);
	}
}

