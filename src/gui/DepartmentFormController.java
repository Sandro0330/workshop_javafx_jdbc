package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.constrains;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Departamento;

public class DepartmentFormController implements Initializable {
	
	private Departamento entidade;
	
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

	@FXML
	private void onBtnSalvarAction() {
		System.out.println("onBtnSalvarAction");
	}
	
	@FXML
	private void onBtnCancelarAction() {
		System.out.println("onBtnCancelarAction");
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
			throw new IllegalStateException("Entidade é nula ");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());		
	}
}



