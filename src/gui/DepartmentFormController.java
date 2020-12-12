package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Utils;
import gui.util.constrains;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Departamento;
import model.services.ServicoDepartamento;

public class DepartmentFormController implements Initializable {
	
	private Departamento entidade;
	
	private ServicoDepartamento service;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Button btnSalvar;
	
	@FXML 
	private Button btnCancelar;
	

	public void setDepartamento(Departamento entidade) {
		this.entidade = entidade;
	}
	
	public void setServicoDepartamento (ServicoDepartamento service) {
		this.service = service;
	}

	@FXML
	private void onBtnSalvarAction(ActionEvent evento) {
		if(entidade == null) {
			throw new IllegalStateException("A minha entidade estava nula!");
		}
		if(service ==null) {
			throw new IllegalStateException("O servi�o estava nulo");
		}
		try {
			
			entidade = getFormData();
			service.salvarOuAtualizar(entidade);
			Utils.currentStage(evento).close(); // fechar a janela ap�s salvar o objeto no Banco
		} catch (DbException e) {
			Alerts.showAlert("ERRO ao salvar o objeto ", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Departamento getFormData() { // reponsavel em pegar os objetos e criar um novo departamento
		Departamento obj = new Departamento();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setNome(txtNome.getText());
		return obj;
	}

	@FXML
	private void onBtnCancelarAction(ActionEvent evento) {
		Utils.currentStage(evento).close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		constrains.setTextFieldInteger(txtId);
		constrains.setTextFieldMaxLength(txtNome, 30);
	}
	
	//obtendo os dados do departamento entidade e populando as caixa de texto dos formulario
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade � nula ");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());		
	}
}



