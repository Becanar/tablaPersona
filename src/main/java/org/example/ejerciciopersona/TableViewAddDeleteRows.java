package org.example.ejerciciopersona;
//los comentarios de linea los he puesto para aclararme, ya sé que no son necesarios
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
/**
 * Clase que representa una aplicación JavaFX para agregar y eliminar filas en una tabla de personas.
 * Permite la entrada de datos de la persona y su visualización en una tabla.
 */
public class TableViewAddDeleteRows extends Application {
    private TextField fNameField;// Campo para el nombre
    private TextField lNameField;// Campo para el apellido
    private DatePicker dobField;// Selector de fecha para la fecha de nacimiento
    private TableView<Person> table;// Tabla para mostrar las personas
    private Label lblError;// Etiqueta para mostrar mensajes de error
    private int nextId = 6; // ID del siguiente persona a agregar
    public static void main(String[] args) {
        Application.launch(args);
    }//main

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        // Inicializa los campos de entrada
        fNameField = new TextField();
        fNameField.setPromptText("Enter your first name");
        lNameField = new TextField();
        lNameField.setPromptText("Enter your last name");
        dobField = new DatePicker();
        dobField.setPromptText("Enter your birth date");
        // Tooltip para el selector de fecha
        Tooltip tp1=new Tooltip("Select the date");
        Tooltip.install(dobField,tp1);
        // Inicializa la tabla con la lista de personas
        table = new TableView<>(PersonTableUtil.getPersonList());
        // Configura el modo de selección de la tabla
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);
        // Agrega las columnas a la tabla
        table.getColumns().addAll(PersonTableUtil.getIdColumn(), PersonTableUtil.getFirstNameColumn(), PersonTableUtil.getLastNameColumn(), PersonTableUtil.getBirthDateColumn());
        // Crea el panel de entrada de datos
        GridPane newDataPane  = this.getNewPersonDataPane();
        // Botón para restaurar filas
        Button restoreBtn = new Button("Restore Rows");
        Tooltip tp2=new Tooltip("Restore the rows to the default ones");
        Tooltip.install(restoreBtn,tp2);
        restoreBtn.setOnAction(e -> restoreRows());
        // Botón para eliminar filas seleccionadas
        Button deleteBtn = new Button("Delete Selected Rows");
        Tooltip tp3=new Tooltip("Delete the selected row");
        Tooltip.install(deleteBtn,tp3);
        deleteBtn.setOnAction(e -> deleteSelectedRows());
        // Etiqueta para mostrar mensajes de error
        lblError=new Label();
        lblError.setVisible(false);
        // Configura el diseño de la interfaz de usuario
        VBox root = new VBox(newDataPane, new HBox(restoreBtn, deleteBtn),lblError, table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        // Crea y muestra la escena
        Scene scene = new Scene(root,400,500);
        stage.setScene(scene);
        stage.setMaxWidth(450);
        stage.setMinWidth(375);
        stage.setMaxHeight(600);
        stage.setMinHeight(400);
        stage.setTitle("Adding/Deleting Rows in a TableViews");
        stage.show();
    }
    /**
     * Crea y devuelve un panel de entrada de datos para una nueva persona.
     *
     * @return Un GridPane que contiene los campos para ingresar datos de la persona.
     */
    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.addRow(0, new Label("First Name:"), fNameField);
        pane.addRow(1, new Label("Last Name:"), lNameField);
        pane.addRow(2, new Label("Birth Date:"), dobField);
        // Botón para agregar una nueva persona
        Button addBtn = new Button("Add");
        Tooltip tp=new Tooltip("Add the Person to the table");
        Tooltip.install(addBtn,tp);
        addBtn.setOnAction(e -> addPerson());
        pane.add(addBtn, 2, 0);
        return pane;
    }
    /**
     * Verifica si el texto dado es válido (solo letras).
     * No vale que esté vacío.
     * @param txt Texto a verificar.
     * @return true si el texto es válido; false en caso contrario.
     */
    private boolean txtValido(String txt) {
        if(txt.length()==0){
            return false;
        }
        for (int i = 0; i < txt.length(); i++) {
            if (!Character.isLetter(txt.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    /**
     * Elimina las filas seleccionadas de la tabla.
     */
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
        // Elimina las filas seleccionadas en orden invers
        for(int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
        recalcularID();
    }
    /**
     * Recalcula los IDs de las personas en la tabla después de una eliminación o restauración.
     */
    private void recalcularID() {
        int id = 1; // Reinicia los IDs desde 1 o el valor que desees
        for (Person person : table.getItems()) {
            person.setPersonId(id++);
        }
        nextId=id;
    }
    /**
     * Restaura las filas de la tabla a los valores predeterminados.
     */
    public void restoreRows() {
        table.getItems().clear();
        table.getItems().addAll(PersonTableUtil.getPersonList());
        recalcularID();
    }
    /**
     * Crea un nuevo objeto Persona a partir de los campos de entrada.
     *
     * @return Un objeto Person con los datos ingresados.
     */
    public Person getPerson() {
        return new Person(nextId++,fNameField.getText(), lNameField.getText(), dobField.getValue());
    }
    /**
     * Agrega una nueva persona a la tabla.
     */
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
        // Crea y agrega la nueva persona a la tabla
        Person p = getPerson();
        table.getItems().add(p);
        clearFields();
        lblError.setText("Persona agregada correctamente.");
        lblError.setStyle("-fx-text-fill: green;");
        lblError.setVisible(true);
    }
    /**
     * Limpia los campos de entrada de datos.
     */
    public void clearFields() {
        fNameField.setText(null);
        lNameField.setText(null);
        dobField.setValue(null);
    }
}