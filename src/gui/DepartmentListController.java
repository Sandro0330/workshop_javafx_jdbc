package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Departamento;

public class DepartmentListController implements Initializable {
	
	@FXML
	private TableView<Departamento> tbVisualDepartamento;  // TbViusalDp (tabela visual de departamento)

	@FXML
	private TableColumn<Departamento, Integer> colunaTabelaId;
	
	@FXML
	private TableColumn<Departamento, String> colunaTabelaNome;
	
	@FXML
	private Button btnNovo;
	
	@FXML
	public void onBtnNewAction() {
		System.out.println("onBtnNewAction");
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

}
