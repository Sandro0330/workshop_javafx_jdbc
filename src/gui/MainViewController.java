package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import jdk.internal.loader.Loader;
import model.services.ServicoDepartamento;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemVendedorAction() {
		System.out.println("onMenuItemVendedorAction");
	}
	
	@FXML
	public void onMenuItemDepartamentoAction() {
		loadView2("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
			
	}
	
	private synchronized void loadView(String nomeAbsoluto) {
		try {
			FXMLLoader carregando = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			VBox newVBox = carregando.load();
			
			Scene cenaPrincipal  = Main.getCenaPrincipal();
			VBox vBoxPrincipal = (VBox)((ScrollPane) cenaPrincipal.getRoot()).getContent();
			
			Node menuPrincipal = vBoxPrincipal.getChildren().get(0);
			vBoxPrincipal.getChildren().clear();	
			vBoxPrincipal.getChildren().add(menuPrincipal);
			vBoxPrincipal.getChildren().addAll(newVBox.getChildren());
			
			
		}
		catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a página", e.getMessage() , AlertType.ERROR);
		}
	}
	
	private synchronized void loadView2(String nomeAbsoluto) {
		try {
			FXMLLoader carregando = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			VBox newVBox = carregando.load();
			
			Scene cenaPrincipal  = Main.getCenaPrincipal();
			VBox vBoxPrincipal = (VBox)((ScrollPane) cenaPrincipal.getRoot()).getContent();
			
			Node menuPrincipal = vBoxPrincipal.getChildren().get(0);
			vBoxPrincipal.getChildren().clear();	
			vBoxPrincipal.getChildren().add(menuPrincipal);
			vBoxPrincipal.getChildren().addAll(newVBox.getChildren());
			
			// Atualizando os dados na tela
			DepartmentListController controller = carregando.getController();
			controller.setServicoDepartamento(new ServicoDepartamento());
			controller.updateTableView();
		}
		catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a página", e.getMessage() , AlertType.ERROR);
		}
	}

}
