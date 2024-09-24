package org.example.ejerciciopersona;
//los comentarios de linea los he puesto para aclararme, ya sé que no son necesarios
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * Clase para gestionar la representación en tabla de objetos Persona.
 * Proporciona métodos para crear una lista observable de personas y definir columnas de tabla.
 */
public class PersonTableUtil {
    // Contador de ID para asignar un ID único a cada persona.
    private static int idCounter = 1;
    /**
     * Devuelve una lista observable de objetos Persona con datos de ejemplo.
     *
     * @return Una lista observable que contiene objetos Persona.
     */
    public static ObservableList<Person> getPersonList() {
        Person p1 = new Person(idCounter++,"Ashwin", "Sharan", LocalDate.of(2012, 10, 11));
        Person p2 = new Person(idCounter++,"Advik", "Sharan", LocalDate.of(2012, 10, 11));
        Person p3 = new Person(idCounter++,"Layne", "Estes", LocalDate.of(2011, 12, 16));
        Person p4 = new Person(idCounter++,"Mason", "Boyd", LocalDate.of(2003, 4, 20));
        Person p5 = new Person(idCounter++,"Babalu", "Sharan", LocalDate.of(1980, 1, 10));
        return FXCollections.<Person>observableArrayList(p1, p2, p3, p4, p5);
    }

    /**
     * Crea y devuelve una columna de tabla para mostrar el ID de la persona.
     *
     * @return La columna de tabla que representa el ID de la persona.
     */
    public static TableColumn<Person, Integer> getIdColumn() {
        TableColumn<Person, Integer> personIdCol = new TableColumn<>("Id");
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));
        return personIdCol;
    }

    /**
     * Crea y devuelve una columna de tabla para mostrar el nombre de la persona.
     *
     * @return La columna de tabla que representa el nombre de la persona.
     */
    public static TableColumn<Person, String> getFirstNameColumn() {
        TableColumn<Person, String> fNameCol = new TableColumn<>("First Name");
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        return fNameCol;
    }

    /**
     * Crea y devuelve una columna de tabla para mostrar el apellido de la persona.
     *
     * @return La columna de tabla que representa el apellido de la persona.
     */
    public static TableColumn<Person, String> getLastNameColumn() {
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        return lastNameCol;
    }

    /**
     * Crea y devuelve una columna de tabla para mostrar la fecha de nacimiento de la persona.
     *
     * @return La columna de tabla que representa la fecha de nacimiento de la persona.
     */
    public static TableColumn<Person, LocalDate> getBirthDateColumn() {
        TableColumn<Person, LocalDate> bDateCol = new TableColumn<>("Birth Date");
        bDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        return bDateCol;
    }

}