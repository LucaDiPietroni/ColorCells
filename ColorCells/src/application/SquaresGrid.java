package application;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class SquaresGrid {
	
	GridPane sqrGrid;
	WritableArtwork[][] squares;
	StackPane[][] sqrsPanes;
	
	//Konstruktor klasy siatki kwadratów
	public SquaresGrid () {
		sqrGrid = new GridPane();
		sqrGrid.setHgap(5.0);
		sqrGrid.setVgap(5.0);
		
		sqrsPanes = new StackPane[5][5];
		squares = new WritableArtwork[5][5];
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				squares[i][j] = new WritableArtwork(41, 41);
				sqrsPanes[i][j] = new StackPane();
				sqrsPanes[i][j].getChildren().add(squares[i][j].getCanvasFromArt());
				sqrsPanes[i][j].getStyleClass().add("canvas");
				sqrGrid.add(sqrsPanes[i][j], j, i);
			}
		}
	}
	//Standardowy getter
	public GridPane getGridPane() {
		return sqrGrid;
	}
	//Standardowy getter
	public WritableArtwork[][] getElements(){
		return squares;
	}
	//Metoda czyszcz¹ca siatkê kwadratów
	public void clearGrid() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sqrsPanes[i][j].getChildren().clear();
				
				squares[i][j] = new WritableArtwork(41, 41);
				sqrsPanes[i][j] = new StackPane();
				sqrsPanes[i][j].getChildren().add(squares[i][j].getCanvasFromArt());
				sqrsPanes[i][j].getStyleClass().add("canvas");
				sqrGrid.add(sqrsPanes[i][j], j, i);
			}
		}
	}
}
