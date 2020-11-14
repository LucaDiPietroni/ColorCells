package application;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BigCanvas implements Calculations{
	StackPane artworkPane;
	ReadableArtwork basicCanvas;
	//Konstruktor klasy obrazka wraz z obramowaniem (pane'm)
	public BigCanvas() {
		this.basicCanvas = new ReadableArtwork(300, 360);
		this.artworkPane = new StackPane();
		this.artworkPane.getChildren().add(basicCanvas.getCanvasFromArt());
		this.artworkPane.getStyleClass().add("canvas");
	}
	//Standardowy getter
	public StackPane getCanvasPane() {
		return artworkPane;
	}
	//Standardowy getter
	public ReadableArtwork getMainCanvas() {
		return basicCanvas;
	}
	//Metoda wstawiaj¹cy wczytany z pliku obraz na p³ótno
	public void loadAnImage(FileChooser fileChooser, Stage stage) {
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(stage);
		this.basicCanvas.setImage(file);

	}
	//Metoda czyszcz¹ca g³ówny obraz
	public void clearBigCanvas() {
		this.artworkPane.getChildren().add(this.basicCanvas.getCanvasFromArt());
		this.artworkPane.getStyleClass().add("canvas");
	}

}
