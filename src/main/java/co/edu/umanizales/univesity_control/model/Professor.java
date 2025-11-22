
package co.edu.umanizales.univesity_control.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Professor extends Person {
    // ID para persistencia CSV

    // Referencia a objeto para relaciones (no se serializa en CSV)
    @JsonBackReference("department-professors")
    private Department department;

    private String specialization;
    private String hireDate;
    private double salary;

    @JsonManagedReference("professor-assignments")
    @JsonIgnore
    private List<ProfessorAssignment> assignments = new ArrayList<>();

    public Professor(String id, String firstName, String lastName, String email, String phone,
                     String address, Department department, String specialization, String hireDate, double salary) {
        super(id, firstName, lastName, email, phone, address);
        this.department = department;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.salary = salary;
    }
}
