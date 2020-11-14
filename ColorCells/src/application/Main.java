package application;

import java.io.File;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Main extends Application implements Calculations {
	//Indeksy potrzebne do monitorowania uzupe³niania pól
	private int rowIndx = 1;
	private int columnIndx = 1;
	BigCanvas mainCanvas;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Tworzenie okna g³ównego, pod³¹czanie pliku ze stylem, wy³¹czanie zmiany rozmiaru i przypisanie stylu scenie
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 700, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			root.getStyleClass().add("scene");
			
			//Tworzenie okna z g³ównym obrazem, etykiety informacyjnej, oraz siatki ma³ych kwadratów
			mainCanvas = new BigCanvas();
			Label ofPieces = new Label("Wycinki w kolejnoœci malej¹cej œredniej\nwartoœci sk³adowej czerwonej.");
			SquaresGrid sqrGrid = new SquaresGrid();
			
			//Tworzenie obiektu pozwalaj¹cego na wyœwietlenie okna dialogowego i wczytanie pliku
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
				     new FileChooser.ExtensionFilter("PNG", "*.png"),
				     new FileChooser.ExtensionFilter("JPEG", "*.jpg")
				);
			
			//Tworzenie przycisku ³adowania obrazu, nadawanie funkcjonalnoœci i stylu
			Button load = new Button("Wczytaj");
			load.setOnAction(event -> {
				try {
					//Resetowanie siatki kwadratów
					sqrGrid.clearGrid();
					//Resetowanie wartoœci indeksów monitoruj¹cych iloœæ uzupe³nionych pól
					rowIndx = 1;
					columnIndx = 1;
					mainCanvas.loadAnImage(fileChooser, primaryStage);
				} catch(Exception ine) {
					//Definiowanie okienka ostrzegaj¹cego przed b³êdem
					Alert AlarmWin = new Alert(AlertType.INFORMATION);
					AlarmWin.setTitle("B³¹d!");
					AlarmWin.setHeaderText(null);
					AlarmWin.setContentText("Operacja nie powiod³a siê");
					AlarmWin.showAndWait();
				};
			});
			load.getStyleClass().add("Button");
			
			//Definiowanie akcji, które wydarz¹ siê jeœli u¿ytkownik wciœnie g³ówny obrazek
			mainCanvas.getMainCanvas().getCanvasFromArt().addEventHandler(
					MouseEvent.MOUSE_PRESSED,
					event -> {
						try {
							//Tworzenie obiektu przechowuj¹cego obraz wskazany przez u¿ytkownika, oraz bufor potrzebny do zamiany obrazów
							WritableArtwork clickedSqr = new WritableArtwork(41, 41);
							WritableArtwork buffor = new WritableArtwork(41, 41);
							
							//Przerysowywanie kwadratu o œrodku wskazanym przez u¿ytkownika, oraz obliczanie œredniej sk³adowej czerwonej
							redrawOn((int) event.getX() - 20, (int) event.getY() - 20, mainCanvas.getMainCanvas().getPixelReaderFromArt(), clickedSqr.getPixelWriterFromArt());
							double pressedPieceRedVal = avgRed(clickedSqr.getWritableImgFromArt());
							
							//Przemalowywanie obrazka na kwadrat siatki przy uwzglêdnieniu iloœci zapisanych obrazków
							for(int i = 0; i < rowIndx; i++) {
								if(i <= rowIndx - 2 && rowIndx >= 0) {
									for(int j = 0; j < 5; j++) {
										double squareRedVal = avgRed(sqrGrid.getElements()[i][j].getWritableImgFromArt());
										if (pressedPieceRedVal >= squareRedVal) {
											reverse(sqrGrid.getElements()[i][j], buffor, clickedSqr);
											pressedPieceRedVal = avgRed(sqrGrid.getElements()[i][j].getWritableImgFromArt());	
										}
									}
								}
								else if (i == rowIndx - 1) {
									for(int j = 0; j < columnIndx; j++) {
										double squareRedVal = avgRed(sqrGrid.getElements()[i][j].getWritableImgFromArt());
										
										if (pressedPieceRedVal >= squareRedVal) {	
											reverse(sqrGrid.getElements()[i][j], buffor, clickedSqr);
											pressedPieceRedVal = avgRed(sqrGrid.getElements()[i][j].getWritableImgFromArt());
										}
										
									}
								}
								
							}
							//Ustawienie prze³¹czników/indeksów w zale¿noœci od iloœci zapisanych obrazków
							if (columnIndx < 5 && rowIndx <= 5) {
								columnIndx++;
							}
							else if (columnIndx == 5 && rowIndx < 5) {
								columnIndx = 1;
								rowIndx++;
							}
							else if (columnIndx == 5 && rowIndx == 5) {
								columnIndx = 5;
								rowIndx = 5;
							}
						}
						catch (Exception e) {
							//Definiowanie okienka ostrzegaj¹cego przed b³êdem
							Alert AlarmWin = new Alert(AlertType.INFORMATION);
							AlarmWin.setTitle("B³¹d!");
							AlarmWin.setHeaderText(null);
							AlarmWin.setContentText("Wybrano fragment wychodz¹cy poza obraz lub go nie wczytano");
							AlarmWin.showAndWait();
						}
					}
					);
			
			//Tworzenie przycisku czyszcz¹cego siatkê kwadratów
			Button clear = new Button("Czyœæ");
			clear.setOnAction(event -> {
				try {
					//Czyszczenie i ustawianie indeksów
					sqrGrid.clearGrid();
					rowIndx = 1;
					columnIndx = 1;
				}
				catch (Exception e) {
					
				}
			});
			clear.getStyleClass().add("Button");
			
			//Tworzenie kontenerów na elementy
			VBox rightSide = new VBox(10);
			HBox buttonsHBox = new HBox(10);
			
			//Ustawiania wszystkich elementów i kontenerów
			buttonsHBox.getChildren().addAll(load, clear);
			rightSide.getChildren().addAll(buttonsHBox, ofPieces, sqrGrid.getGridPane());
			root.setLeft(mainCanvas.getCanvasPane());
			root.setRight(rightSide);
			
			primaryStage.show();
			
		} catch(Exception e) {
			//Definiowanie okienka ostrzegaj¹cego przed b³êdem
			Alert AlarmWin = new Alert(AlertType.INFORMATION);
			AlarmWin.setTitle("B³¹d!");
			AlarmWin.setHeaderText(null);
			AlarmWin.setContentText("Coœ posz³o nie tak!");
			AlarmWin.showAndWait();
		}
	}
	

	
	public static void main(String[] args) {
		launch(args);
	}
}
