package gui;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Vendedor;
import model.services.ServicoVendedor;

public class SellerListController implements Initializable, DataChangeListener {
	
	private ServicoVendedor service;
	
	@FXML
	private TableView<Vendedor> tbVisualVendedor;  // TbViusalDp (tabela visual de departamento)

	@FXML
	private TableColumn<Vendedor, Integer> colunaTabelaId;
	
	@FXML
	private TableColumn<Vendedor, String> colunaTabelaNome;
	
	@FXML
	private TableColumn<Vendedor, Vendedor>tableColumnEDIT;
	
	@FXML
	private TableColumn<Vendedor, Vendedor> TableColumnRemove; 
	
	@FXML
	private Button btnNovo;
	
	private ObservableList<Vendedor> obsList;
	
	@FXML
	public void onBtnNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Vendedor obj = new Vendedor();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}
	
	public void setServicoVendedor(ServicoVendedor service) {
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
		tbVisualVendedor.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("O serviço esta nulo");
		}
		List<Vendedor> lista = service.findAll();  
		obsList = FXCollections.observableArrayList(lista);
		tbVisualVendedor.setItems(obsList);
		initEditiButtons();
		initRemoveButtons();
			
	}

	private void createDialogForm(Vendedor obj, String nomeAbsoluto, Stage parentStage) {
//		try {
//			FXMLLoader carregando = new FXMLLoader(getClass().getResource(nomeAbsoluto));
//			Pane painel = carregando.load();
//			
//			DepartmentFormController controller = carregando.getController();
//			controller.setVendedor(obj);
//			controller.setServicoVendedor(new ServicoVendedor());
//			controller.subscribeDataChangeListener(this);
//			controller.updateFormData();
//			
//			Stage dialoStage = new Stage();
//			dialoStage.setTitle("Informe os dados do Vendedor");
//			dialoStage.setScene(new Scene(painel));
//			dialoStage.setResizable(false);
//			dialoStage.initOwner(parentStage);
//			dialoStage.initModality(Modality.WINDOW_MODAL);
//			dialoStage.showAndWait();
//			
//		}
//		catch (IOException e) {
//			Alerts.showAlert("IO Exception", "Error losding view", e.getMessage(), AlertType.ERROR);
//	
//		}
	}

	@Override
	public void onDataChanged() {
		updateTableView(); 
	}
	
	private void initEditiButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Vendedor, Vendedor>(){
			private final Button button = new Button("edit");
			
			@Override
			protected void updateItem(Vendedor obj, boolean empty) {
				super.updateItem(obj, empty);
				
				if(obj == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));
			}
		
		});
	}
	
	private void initRemoveButtons() { // Cria método remove em cada linha
		TableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		TableColumnRemove.setCellFactory(param -> new TableCell<Vendedor, Vendedor>(){
			private final Button button = new Button("remove");
			
			@Override
			protected void updateItem(Vendedor obj, boolean empty) {
				super.updateItem(obj, empty);
				
				if(obj == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
					
			}
		});
	}

	private void removeEntity(Vendedor obj) {
		//Mostrar umm alerta para o usuário
		Optional<ButtonType> resultado = Alerts.showConfirmation("Confirmação", "Você tem certeza que quer deletar ?");
	
		if(resultado.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("SErviço esta nulo ");
			}
			try {
				
				service.remove(obj);
				updateTableView();
				
			}catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover o objeto", null, e.getMessage(), AlertType.ERROR);
			}		
		}
	}
	
}







