package org.example.ejerciciopersona;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;

import javafx.scene.layout.HBox;
import org.example.ejerciciopersona.Person;
import org.example.ejerciciopersona.PersonTableUtil;

import static javafx.scene.control.TableView.TableViewSelectionModel;

public class TableViewAddDeleteRows extends Application {
    private TextField fNameField;
    private TextField lNameField;
    private DatePicker dobField;
    private TableView<Person> table;
    private Label lblError;
    private int nextId = 6;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        fNameField = new TextField();
        fNameField.setPromptText("Enter your first name");
        lNameField = new TextField();
        lNameField.setPromptText("Enter your last name");
        dobField = new DatePicker();
        dobField.setPromptText("Enter your birth date");
        Tooltip tp1=new Tooltip("Select the date");
        Tooltip.install(dobField,tp1);
        table = new TableView<>(PersonTableUtil.getPersonList());
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);
        table.getColumns().addAll(PersonTableUtil.getIdColumn(), PersonTableUtil.getFirstNameColumn(), PersonTableUtil.getLastNameColumn(), PersonTableUtil.getBirthDateColumn());
        GridPane newDataPane  = this.getNewPersonDataPane();
        Button restoreBtn = new Button("Restore Rows");
        Tooltip tp2=new Tooltip("Restore the rows to the default ones");
        Tooltip.install(restoreBtn,tp2);
        restoreBtn.setOnAction(e -> restoreRows());
        Button deleteBtn = new Button("Delete Selected Rows");
        Tooltip tp3=new Tooltip("Delete the selected row");
        Tooltip.install(deleteBtn,tp3);
        deleteBtn.setOnAction(e -> deleteSelectedRows());
        lblError=new Label();
        lblError.setVisible(false);
        VBox root = new VBox(newDataPane, new HBox(restoreBtn, deleteBtn),lblError, table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        Scene scene = new Scene(root,400,500);
        stage.setScene(scene);
        stage.setMaxWidth(450);
        stage.setMinWidth(375);
        stage.setMaxHeight(600);
        stage.setMinHeight(400);
        stage.setTitle("Adding/Deleting Rows in a TableViews");
        stage.show();
    }

    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.addRow(0, new Label("First Name:"), fNameField);
        pane.addRow(1, new Label("Last Name:"), lNameField);
        pane.addRow(2, new Label("Birth Date:"), dobField);
        Button addBtn = new Button("Add");
        Tooltip tp=new Tooltip("Add the Person to the table");
        Tooltip.install(addBtn,tp);
        addBtn.setOnAction(e -> addPerson());
        pane.add(addBtn, 2, 0);
        return pane;
    }
    private boolean txtValido(String txt) {
        for (int i = 0; i < txt.length(); i++) {
            if (!Character.isLetter(txt.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public void deleteSelectedRows() {
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        if (tsm.isEmpty()) {
            System.out.println("Please select a row to delete.");
            return;
        }

        ObservableList<Integer> list = tsm.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        Arrays.sort(selectedIndices);

        for(int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
        recalculateID();
    }

    private void recalculateID() {
        int id = 1; // Reinicia los IDs desde 1 o el valor que desees
        for (Person person : table.getItems()) {
            person.setPersonId(id++);
        }
        nextId=id;
    }

    public void restoreRows() {
        table.getItems().clear();
        table.getItems().addAll(PersonTableUtil.getPersonList());
        recalculateID();
    }

    public Person getPerson() {
        return new Person(nextId++,fNameField.getText(), lNameField.getText(), dobField.getValue());
    }

    public void addPerson() {
        lblError.setVisible(false);
        lblError.setText("");
        String firstName = fNameField.getText();
        String lastName = lNameField.getText();
        if (!txtValido(firstName)) {
            lblError.setText("ERROR! NOMBRE NO VÁLIDO");
            lblError.setVisible(true);
            lblError.setStyle("-fx-text-fill: red;");
            return;
        }
        if (!txtValido(lastName)) {
            lblError.setText("ERROR! APELLIDO NO VÁLIDO");
            lblError.setVisible(true);
            lblError.setStyle("-fx-text-fill: red;");
            return;
        }
        if (dobField.getValue() == null) {
            lblError.setText("ERROR! FECHA NO VÁLIDA");
            lblError.setVisible(true);
            lblError.setStyle("-fx-text-fill: red;");
            return;
        }
        Person p = getPerson();
        table.getItems().add(p);
        clearFields();
        lblError.setText("Persona agregada correctamente.");
        lblError.setStyle("-fx-text-fill: green;");
        lblError.setVisible(true);
    }

    public void clearFields() {
        fNameField.setText(null);
        lNameField.setText(null);
        dobField.setValue(null);
    }
}