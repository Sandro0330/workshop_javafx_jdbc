package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.ServicoDepartamento;

public class DepartmentListController implements Initializable {
	
	private ServicoDepartamento service;
	
	@FXML
	private TableView<Departamento> tbVisualDepartamento;  // TbViusalDp (tabela visual de departamento)

	@FXML
	private TableColumn<Departamento, Integer> colunaTabelaId;
	
	@FXML
	private TableColumn<Departamento, String> colunaTabelaNome;
	
	@FXML
	private Button btnNovo;
	
	private ObservableList<Departamento> obsList;
	
	@FXML
	public void onBtnNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Departamento obj = new Departamento();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}
	
	public void setServicoDepartamento(ServicoDepartamento service) {
		this.service = service;
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {	
		inicializandoNodes();
		
	}

	private void inicializandoNodes() {
		colunaTabelaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaTabelaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		//definindo coulunas e tabelas do tamanho da janela
		Stage stage = (Stage) Main.getCenaPrincipal().getWindow();
		tbVisualDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("O serviço esta nulo");
		}
		List<Departamento> lista = service.findAll();  
		obsList = FXCollections.observableArrayList(lista);
		tbVisualDepartamento.setItems(obsList);
			
	}

	private void createDialogForm(Departamento obj, String nomeAbsoluto, Stage parentStage) {
		try {
			FXMLLoader carregando = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			Pane painel = carregando.load();
			
			DepartmentFormController controller = carregando.getController();
			controller.setDepartamento(obj);
			controller.setServicoDepartamento(new ServicoDepartamento());
			controller.updateFormData();
			
			Stage dialoStage = new Stage();
			dialoStage.setTitle("Informe os dados do Departamento");
			dialoStage.setScene(new Scene(painel));
			dialoStage.setResizable(false);
			dialoStage.initOwner(parentStage);
			dialoStage.initModality(Modality.WINDOW_MODAL);
			dialoStage.showAndWait();
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error losding view", e.getMessage(), AlertType.ERROR);
	
		}
	}
	
	
}
