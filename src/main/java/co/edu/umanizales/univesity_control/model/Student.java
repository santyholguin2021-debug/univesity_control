package co.edu.umanizales.univesity_control.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {
    private String enrollmentDate;
    private String major;
    private double gpa;

    public Student(String id, String firstName, String lastName, String email, String phone, 
                   String address, String enrollmentDate, String major, double gpa) {
        super(id, firstName, lastName, email, phone, address);
        this.enrollmentDate = enrollmentDate;
        this.major = major;
        this.gpa = gpa;
    }
}
