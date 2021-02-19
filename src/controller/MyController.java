package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Etudiant;
import services.EtudiantService;
import services.IEtudiantService;

public class MyController implements Initializable {

	@FXML
	private Menu menu;
	@FXML
	private Pane formulaire;
	@FXML
	private Pane afficheEtudiant;
	@FXML
	private Menu exit;
	@FXML
	private Menu help;
	@FXML
	private TableView<Etudiant> table;
	@FXML
	private TableColumn<Etudiant, String> id;
	@FXML
	private TableColumn<Etudiant, String> nom;
	@FXML
	private TableColumn<Etudiant, String> prenom;
	@FXML
	private TableColumn<Etudiant, String> mdp;
	@FXML
	private TableColumn<Etudiant, String> date;
	@FXML
	private TableColumn<Etudiant, Etudiant> delete;
	@FXML
	private TableColumn<Etudiant, Etudiant> modification;
	@FXML
	private TableColumn<Etudiant, Etudiant> affiche;

	@FXML
	private TextField search;
	@FXML
	private TextField nomEtudiant;
	@FXML
	private TextField prenomEtudiant;
	@FXML
	private DatePicker naissanceEtudiant;
	@FXML
	private TextField mdpEtudiant;
	@FXML
	private TextField lienPhoto;
	@FXML
	private Label etudiantNom;
	@FXML
	private Label etudiantPrenom;
	@FXML
	private Label etudiantNaissance;
	@FXML
	private ImageView photo;

	@FXML
	private Button buttonPhoto;
	@FXML
	private Button validationButton;
	@FXML
	private Pagination pagination;

	private String pathString;

	private Etudiant etudiant;

	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	private IEtudiantService iserviceEtudiant = new EtudiantService();
	private static final int ROWS_PER_PAGE = 4;

	private FilteredList<Etudiant> filteredData;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		formulaire.setVisible(true);
		table.setVisible(false);
		afficheEtudiant.setVisible(false);
		exit.showingProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue) {
				Platform.exit();
				System.exit(0);
			}
		});

//		search.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(etudiant -> newValue == null || newValue.isEmpty()
//					|| etudiant.getNomString().toLowerCase().contains(newValue.toLowerCase())
//					|| etudiant.getPrenomString().toLowerCase().contains(newValue.toLowerCase()));
//			changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
//		});
//
//		int totalPage = (int) (Math.ceil(5 * 1.0 / ROWS_PER_PAGE));
//		pagination.setPageCount(totalPage);
//		pagination.setCurrentPageIndex(0);
//		changeTableView(0, ROWS_PER_PAGE);
//		pagination.currentPageIndexProperty()
//				.addListener((observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));
//
//	}
//
//	private void changeTableView(int index, int limit) {
//
//		int fromIndex = index * limit;
//		int toIndex = Math.min(fromIndex + limit, table.getItems().size());
//
//		int minIndex = Math.min(toIndex, filteredData.size());
//		SortedList<Etudiant> sortedData = new SortedList<>(
//				FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
//		sortedData.comparatorProperty().bind(table.comparatorProperty());
//
//		table.setItems(sortedData);
//
	}

	public void browser() {

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(new Stage());
		System.out.println(file.getAbsolutePath());
		pathString = file.getAbsolutePath();
		lienPhoto.setText(file.getAbsolutePath().toString());

	}

	public void ajouterEtudiant(ActionEvent e) {

		table.setVisible(false);
		formulaire.setVisible(true);
		afficheEtudiant.setVisible(false);
	}

	public void validation(ActionEvent e) {
		if (table.getItems().size() == 0) {
			for (Etudiant et : iserviceEtudiant.listEtudiant()) {

				table.getItems().add(et);
			}
		}
		LocalDate localDate = naissanceEtudiant.getValue();

		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);

		if (validationButton.getText().toString().contains("Validation")) {
			etudiant = new Etudiant(nomEtudiant.getText(), prenomEtudiant.getText(), mdpEtudiant.getText(), pathString,
					date);
			System.out.println("validation");
			
			iserviceEtudiant.ajouterEtudiant(etudiant);

			table.getItems().add(iserviceEtudiant.chercherUnetudiantParSonNom(etudiant.getNomString()));
			
		} else {
			System.out.println("modification");
			etudiant.setNomString(nomEtudiant.getText());
			etudiant.setPrenomString(prenomEtudiant.getText());
			etudiant.setMotDePasseString(mdpEtudiant.getText());
			etudiant.setImageString(pathString);
			etudiant.setDatenaissance(date);
			System.out.println("modification");
			iserviceEtudiant.modifierEtudiant(etudiant);
			table.getItems().remove(etudiant);
			table.getItems().add(iserviceEtudiant.chercherUnEtudiantParSonId(etudiant.getId()));
			validationButton.setText("Validation");
		}

	}

	public void addFile(ActionEvent e) {
		System.out.println("AddFile");
	}

	public void afficherListe(ActionEvent e) {

		validationButton.setText("Validation");
		formulaire.setVisible(false);
		afficheEtudiant.setVisible(false);

		id = new TableColumn<Etudiant, String>("Id");
		id.setStyle("-fx-alignment:center;-fx-font-weight:bold;");
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		nom = new TableColumn<Etudiant, String>("Nom");
		nom.setStyle("-fx-alignment:center;-fx-font-weight:bold;");
		nom.setCellValueFactory(new PropertyValueFactory<>("nomString"));
		prenom = new TableColumn<Etudiant, String>("Prenom");
		prenom.setStyle("-fx-alignment:center;-fx-font-weight:bold;");
		prenom.setCellValueFactory(new PropertyValueFactory<>("prenomString"));
		mdp = new TableColumn<Etudiant, String>("Mot de Passe");
		mdp.setStyle("-fx-alignment:center;-fx-font-weight:bold;");
		mdp.setCellValueFactory(new PropertyValueFactory<>("motDePasseString"));
		date = new TableColumn<Etudiant, String>("Date naissance");
		date.setStyle("-fx-alignment:center;-fx-font-weight:bold;");
		date.setCellValueFactory(new PropertyValueFactory<>("datenaissance"));
		delete = new TableColumn<Etudiant, Etudiant>("Supprimer");
		delete.setStyle("-fx-alignment: center; -fx-font-weight:bold; -fx-width:300px;");
		delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		delete.setCellFactory(param -> new TableCell<Etudiant, Etudiant>() {
			private final Button deleteButton = new Button("Delete");

			@Override
			protected void updateItem(Etudiant etudiant, boolean empty) {
				super.updateItem(etudiant, empty);
				deleteButton.setStyle("-fx-color:red;");
				deleteButton.setMaxWidth(150);
				if (etudiant == null) {
					setGraphic(null);
					return;
				}

				setGraphic(deleteButton);

				deleteButton.setOnAction(event -> {
					getTableView().getItems().remove(etudiant);
					iserviceEtudiant.supprimeUnEtudiant(etudiant.getId());
				}

				);

			}

		});
		modification = new TableColumn<Etudiant, Etudiant>("Modification");
		modification.setStyle("-fx-alignment: center; -fx-width:150px");
		modification.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		modification.setCellFactory(param -> new TableCell<Etudiant, Etudiant>() {
			private final Button modificationButton = new Button("Edit");

			@Override
			protected void updateItem(Etudiant etudiant1, boolean empty) {
				super.updateItem(etudiant1, empty);
				modificationButton.setStyle("-fx-color:grey;");
				modificationButton.setMaxWidth(150);
				if (etudiant1 == null) {
					setGraphic(null);
					return;
				}

				setGraphic(modificationButton);
				modificationButton.setOnAction(event -> {
					etudiant = etudiant1;
					validationButton.setText("Modification");
					formulaire.setVisible(true);
					table.setVisible(false);
					nomEtudiant.setText(etudiant1.getNomString());
					prenomEtudiant.setText(etudiant1.getPrenomString());
					mdpEtudiant.setText(etudiant1.getMotDePasseString());
					lienPhoto.setText(etudiant1.getImageString());

				}

				);

			}

		});
		affiche = new TableColumn<Etudiant, Etudiant>("Affiche");
		affiche.setStyle("-fx-alignment: center; -fx-width:150px");
		affiche.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		affiche.setCellFactory(param -> new TableCell<Etudiant, Etudiant>() {
			private Button afficheButton = new Button("Affiche");

			@Override
			protected void updateItem(Etudiant etudiant, boolean empty) {
				super.updateItem(etudiant, empty);

				afficheButton.setStyle("-fx-color:green;");
				afficheButton.setMaxWidth(150);

				if (etudiant == null) {
					setGraphic(null);
					return;
				}

				setGraphic(afficheButton);

				afficheButton.setOnAction(event -> {

					afficheEtudiant.setVisible(true);
					etudiantNom.setText(etudiant.getNomString());
					etudiantPrenom.setText(etudiant.getPrenomString());
					etudiantNaissance.setText(format.format(etudiant.getDatenaissance()));

					FileInputStream inputstream;
					try {
						inputstream = new FileInputStream(etudiant.getImageString());
						Image image = new Image(inputstream);
						photo.setImage(image);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				);

			}

		});

		if (table.getColumns().size() == 0) {
			table.getColumns().add(id);
			table.getColumns().add(nom);
			table.getColumns().add(prenom);
			table.getColumns().add(mdp);
			table.getColumns().add(date);
			table.getColumns().add(delete);
			table.getColumns().add(modification);
			table.getColumns().add(affiche);
		}

		if (table.getItems().size() == 0) {
			for (Etudiant et : iserviceEtudiant.listEtudiant()) {

				table.getItems().add(et);
			}
		}
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Etudiant> filteredData = new FilteredList<>(table.getItems(), p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		search.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(etudiant -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (etudiant.getNomString().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (etudiant.getPrenomString().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
			// 3. Wrap the FilteredList in a SortedList.
			SortedList<Etudiant> sortedData = new SortedList<>(filteredData);

			// 4. Bind the SortedList comparator to the TableView comparator.
			sortedData.comparatorProperty().bind(table.comparatorProperty());

			// 5. Add sorted (and filtered) data to the table.
			table.setItems(sortedData);
		});

		table.setVisible(true);
	}

	public void actionHelp(ActionEvent e) {
		System.out.println("Help");
	}

}
