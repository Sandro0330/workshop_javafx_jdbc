package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	public void onBtnNewAction() {
		System.out.println("onBtnNewAction");
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

}
