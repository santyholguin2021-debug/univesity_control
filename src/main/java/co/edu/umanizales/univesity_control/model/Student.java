package co.edu.umanizales.univesity_control.model;

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
public class Student extends Person {
    private String enrollmentDate;
    private String major;
    private double gpa;
    
    // Referencias a objetos para relaciones (no se serializan en CSV)
    @JsonManagedReference("student-enrollments")
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(String id, String firstName, String lastName, String email, String phone, 
                   String address, String enrollmentDate, String major, double gpa) {
        super(id, firstName, lastName, email, phone, address);
        this.enrollmentDate = enrollmentDate;
        this.major = major;
        this.gpa = gpa;
    }
}
