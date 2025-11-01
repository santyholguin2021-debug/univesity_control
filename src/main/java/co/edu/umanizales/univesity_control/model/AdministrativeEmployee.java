package co.edu.umanizales.univesity_control.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdministrativeEmployee extends Person {
    private String position;
    private String departmentId;
    private String hireDate;
    private Double salary;

    public AdministrativeEmployee(String id, String firstName, String lastName, String email, String phone, 
                                  String address, String position, String departmentId, String hireDate, Double salary) {
        super(id, firstName, lastName, email, phone, address);
        this.position = position;
        this.departmentId = departmentId;
        this.hireDate = hireDate;
        this.salary = salary;
    }
}
