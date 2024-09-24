package org.example.ejerciciopersona;
//los comentarios de linea los he puesto para aclararme, ya sé que no son necesarios
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Representa una Persona con un ID único, nombre, apellido y fecha de nacimiento.
 */
public class Person {
    // AtomicInteger para generar IDs únicos para cada instancia de persona.
    private static AtomicInteger personSequence = new AtomicInteger(0);
    // Campos que representan los atributos de la persona.
    private int personId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    /**
     * Enumeración que representa diferentes categorías de edad basadas en la fecha de nacimiento.
     */
    public enum AgeCategory {
        BABY, CHILD, TEEN, ADULT, SENIOR, UNKNOWN
    };
    /**
     * Constructor por defecto para crear una instancia de Persona con valores predeterminados.
     */
    public Person() {
        this(0,null, null, null);
    }
    /**
     * Constructor para crear una instancia de Persona con atributos específicos.
     *
     * @param personId  El ID único de la persona.
     * @param firstName El nombre de la persona.
     * @param lastName  El apellido de la persona.
     * @param birthDate La fecha de nacimiento de la persona.
     */
    public Person(int personId,String firstName, String lastName, LocalDate birthDate) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
    /**
     * @return ID de la persona.
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Establece ID de la persona.
     *
     * @param personId El nuevo ID a asignar a la persona.
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * @return nombre de la persona
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Establece el nombre de la persona.
     *
     * @param firstName El nombre a asignar.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return El apellido de la persona.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Establece el apellido de la persona.
     *
     * @param lastName El apellido a asignar.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return La fecha de nacimiento de la persona.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }
    /**
     * Establece la fecha de nacimiento de la persona.
     *
     * @param birthDate La fecha de nacimiento a asignar.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    /**
     * Verifica si la fecha de nacimiento dada es válida según las reglas de negocio.
     *
     * @param bdate La fecha de nacimiento a validar.
     * @return True si la fecha de nacimiento es válida, false de lo contrario.
     */
    public boolean isValidBirthDate(LocalDate bdate) {
        return isValidBirthDate(bdate, new ArrayList<>());
    }

    /**
     * Valida la fecha de nacimiento y agrega mensajes de error a la lista proporcionada.
     *
     * @param bdate     La fecha de nacimiento a validar.
     * @param errorList La lista para almacenar mensajes de error de validación.
     * @return True si la fecha de nacimiento es válida, false de lo contrario.
     */
    public boolean isValidBirthDate(LocalDate bdate, List<String> errorList) {
        if (bdate == null) {
            return true;
        }
        // Birth date cannot be in the future
        if (bdate.isAfter(LocalDate.now())) {
            errorList.add("Birth date must not be in future.");
            return false;
        }
        return true;
    }

    /**
     * Valida los atributos de la Persona y agrega mensajes de error a la lista.
     *
     * @param errorList La lista para almacenar mensajes de error de validación.
     * @return True si los atributos de la Persona son válidos, false de lo contrario.
     */
    public boolean isValidPerson(List<String> errorList) {
        return isValidPerson(this, errorList);
    }

    /**
     * Valida los atributos de la Persona especificada y agrega mensajes de error a la lista.
     *
     * @param p         El objeto Persona a validar.
     * @param errorList La lista para almacenar mensajes de error de validación.
     * @return True si los atributos de la Persona son válidos, false de lo contrario.
     */
    public boolean isValidPerson(Person p, List<String> errorList) {
        boolean isValid = true;
        String fn = p.getFirstName();
        if (fn == null || fn.trim().length() == 0) {
            errorList.add("First name must contain minimum one character.");
            isValid = false;
        }
        String ln = p.getLastName();
        if (ln == null || ln.trim().length() == 0) {
            errorList.add("Last name must contain minimum one character.");
            isValid = false;
        }
        if (!isValidBirthDate(this.getBirthDate(), errorList)) {
            isValid = false;
        }
        return isValid;
    }
    /**
     * Categoriza a la Persona en un grupo de edad basado en su fecha de nacimiento.
     *
     * @return La categoría de edad de la persona.
     */
    public AgeCategory getAgeCategory() {
        if (birthDate == null) {
            return AgeCategory.UNKNOWN;
        }
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (years >= 0 && years < 2) {
            return AgeCategory.BABY;
        } else if (years >= 2 && years < 13) {
            return AgeCategory.CHILD;
        } else if (years >= 13 && years <= 19) {
            return AgeCategory.TEEN;
        } else if (years > 19 && years <= 50) {
            return AgeCategory.ADULT;
        } else if (years > 50) {
            return AgeCategory.SENIOR;
        } else {
            return AgeCategory.UNKNOWN;
        }
    }


    /**
     * Intenta guardar la información de la Persona si es válida.
     *
     * @param errorList La lista para almacenar mensajes de error de validación.
     * @return True si la Persona fue guardada correctamente, false de lo contrario.
     */
    public boolean save(List<String> errorList) {
        boolean isSaved = false;
        if (isValidPerson(errorList)) {
            System.out.println("Saved " + this.toString());
            isSaved = true;
        }
        return isSaved;
    }
    /**
     * Devuelve una representación en cadena del objeto Persona.
     *
     * @return Una cadena que contiene el ID de la persona, nombre, apellido y fecha de nacimiento.
     */
    @Override
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]";
    }
}