package co.edu.umanizales.univesity_control.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdministrativeEmployee extends Person {
    private String position;
    
    // ID para persistencia CSV
    private String departmentId;
    
    // Referencia a objeto para relaciones (no se serializa en CSV)
    @JsonBackReference("department-employees")
    @JsonIgnore
    private Department department;
    
    private String hireDate;
    private double salary;

    public AdministrativeEmployee(String id, String firstName, String lastName, String email, String phone, 
                                  String address, String position, String departmentId, String hireDate, double salary) {
        super(id, firstName, lastName, email, phone, address);
        this.position = position;
        this.departmentId = departmentId;
        this.hireDate = hireDate;
        this.salary = salary;
    }
}
