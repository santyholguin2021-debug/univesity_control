package co.edu.umanizales.univesity_control.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdministrativeEmployee extends Person {
    private String position;

    // ID para persistencia CSV
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String departmentId;

    // Referencia a objeto para relaciones (no se serializa en CSV)
    @JsonBackReference("department-employees")
    private Department department;

    private String hireDate;
    private double salary;

    public AdministrativeEmployee(String id, String firstName, String lastName, String email, String phone,
                                  String address, String position, Department department, String hireDate, double salary) {
        super(id, firstName, lastName, email, phone, address);
        this.position = position;
        this.department = department;
        this.departmentId = department != null ? department.getId() : null;
        this.hireDate = hireDate;
        this.salary = salary;
    }
}