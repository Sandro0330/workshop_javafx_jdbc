package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import gui.util.constrains;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Departamento;
import model.entities.Vendedor;
import model.exceptions.ValidationException;
import model.services.ServicoDepartamento;
import model.services.ServicoVendedor;

public class SellerFormController implements Initializable {
	
	private Vendedor entidade;
	
	private ServicoVendedor service;
	
	private ServicoDepartamento servicoDepartamento;
	
	private List<DataChangeListener> dataChangeListener = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpDataNasc;
	
	@FXML
	private TextField txtSalario;
	
	@FXML
	private ComboBox<Departamento> comboBoxDepartamento;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorDataNasc;
	
	@FXML
	private Label labelErrorSalario;
	
	@FXML
	private Button btnSalvar;
	
	@FXML 
	private Button btnCancelar;
	

	private ObservableList<Departamento> obsList;
	

	public void setVendedor(Vendedor entidade) {
		this.entidade = entidade;
	}
	
	public void setServicos (ServicoVendedor service, ServicoDepartamento servicoDepartamento) {
		this.service = service;
		this.servicoDepartamento = servicoDepartamento;
		
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
		constrains.setTextFieldMaxLength(txtNome, 70);
		constrains.setTextFieldDouble(txtSalario); //definindo salário como Double
		constrains.setTextFieldMaxLength(txtEmail, 50);
		Utils.formatDatePicker(dpDataNasc, "dd/MM/yyyy");
		
		inicializandoComboBoxDerpartamento();
	}
	
	//obtendo os dados do departamento entidade e populando as caixa de texto dos formulario
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade é nula!");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());
		txtEmail.setText(entidade.getEmail());
		Locale.setDefault(Locale.US);
		txtSalario.setText(String.format("%.2f", entidade.getSalarioBase()));
		if(entidade.getDataNasc() != null) {
			
			dpDataNasc.setValue(LocalDate.ofInstant(entidade.getDataNasc().toInstant(), ZoneId.systemDefault())); //data e hora local
		}
		if(entidade.getDepartamento() == null) {
			comboBoxDepartamento.getSelectionModel().selectFirst();
		} else {			
			comboBoxDepartamento.setValue(entidade.getDepartamento());
		}
	}
	
	public void carregarObjetosAssociados() {
		if(servicoDepartamento == null) {
			throw new IllegalStateException("Serviço Departamento estava nulo!");
		}
		List<Departamento> list = servicoDepartamento.findAll(); //buscando todos os departamentos do banco
		obsList = FXCollections.observableArrayList(list);//enviando departamentos para detro das listas
		comboBoxDepartamento.setItems(obsList);
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome")); //setando no label a mensagem correspondente ao campo nome
		}
	}
	
	private void inicializandoComboBoxDerpartamento() {
		Callback<ListView<Departamento>, ListCell<Departamento>> factory = lv -> new ListCell<Departamento>() {
			
			@Override
			protected void updateItem(Departamento item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		
		comboBoxDepartamento.setCellFactory(factory);
		comboBoxDepartamento.setButtonCell(factory.call(null));	
	}
}



