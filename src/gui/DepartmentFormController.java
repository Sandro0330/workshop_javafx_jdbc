package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.constrains;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {
	
	
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
}
