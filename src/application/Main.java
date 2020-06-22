package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start (Stage primeiraEtapa) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			Parent pai = carregador.load();
			
			Scene cenaPrincipal = new Scene(pai);
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
