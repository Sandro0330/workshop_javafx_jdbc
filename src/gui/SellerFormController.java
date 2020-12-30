package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import gui.util.constrains;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Vendedor;
import model.exceptions.ValidationException;
import model.services.ServicoVendedor;

public class SellerFormController implements Initializable {
	
	private Vendedor entidade;
	
	private ServicoVendedor service;
	
	private List<DataChangeListener> dataChangeListener = new ArrayList<>();
	
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
	

	public void setVendedor(Vendedor entidade) {
		this.entidade = entidade;
	}
	
	public void setServicoVendedor (ServicoVendedor service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);
	}

	@FXML
	private void onBtnSalvarAction(ActionEvent evento) {
		if(entidade == null) {
			throw new IllegalStateException("A minha entidade estava nula!");
		}
		if(service ==null) {
			throw new IllegalStateException("O serviço estava nulo");
		}
		try {
			
			entidade = getFormData();
			service.salvarOuAtualizar(entidade);
			notifyDataChangeListener();
			Utils.currentStage(evento).close(); // fechar a janela após salvar o objeto no Banco
		} 
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("ERRO ao salvar o objeto ", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListener() {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChanged();
		}
		
	}

	private Vendedor getFormData() { // reponsavel em pegar os objetos e criar um novo departamento
		Vendedor obj = new Vendedor();
		
		ValidationException exception = new ValidationException("Erro de validação");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals(" ")) {
			exception.addError("nome", "O campo não pode ser vazio");
		}		
		obj.setNome(txtNome.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		  
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
			throw new IllegalStateException("Entidade é nula ");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());		
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome")); //setando no label a mensagem correspondente ao campo nome
		}
	}
}



