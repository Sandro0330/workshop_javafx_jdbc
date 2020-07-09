package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
	
	@FXML // inicialização do controller do Department List Controller
	public void onMenuItemDepartamentoAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) ->{
			controller.setServicoDepartamento(new ServicoDepartamento());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x ->{});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
			
	}
	
	private synchronized <T> void loadView(String nomeAbsoluto, Consumer<T> iniciAcao) {
		try {
			FXMLLoader carregando = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			VBox newVBox = carregando.load();
			
			Scene cenaPrincipal  = Main.getCenaPrincipal();
			VBox vBoxPrincipal = (VBox)((ScrollPane) cenaPrincipal.getRoot()).getContent();
			
			Node menuPrincipal = vBoxPrincipal.getChildren().get(0);
			vBoxPrincipal.getChildren().clear();	
			vBoxPrincipal.getChildren().add(menuPrincipal);
			vBoxPrincipal.getChildren().addAll(newVBox.getChildren());
			
			T controller = carregando.getController();
			iniciAcao.accept(controller);
			
		}
		catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a página", e.getMessage() , AlertType.ERROR);
		}
	}
}
