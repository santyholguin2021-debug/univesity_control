package co.edu.umanizales.univesity_control.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Professor extends Person {
    private String departmentId;
    private String specialization;
    private String hireDate;
    private double salary;

    public Professor(String id, String firstName, String lastName, String email, String phone, 
                     String address, String departmentId, String specialization, String hireDate, double salary) {
        super(id, firstName, lastName, email, phone, address);
        this.departmentId = departmentId;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.salary = salary;
    }
}
