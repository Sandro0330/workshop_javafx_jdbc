package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start (Stage primeiraEtapa) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = carregador.load();
			
			// ScrollPane preenche toda a janela 
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			Scene cenaPrincipal = new Scene(scrollPane);
			primeiraEtapa.setScene(cenaPrincipal);
			primeiraEtapa.setTitle("Amostra de aplicação Javafx");
			primeiraEtapa.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
