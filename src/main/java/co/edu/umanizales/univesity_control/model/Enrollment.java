package co.edu.umanizales.univesity_control.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    private String id;
    private String studentId;
    private String courseId;
    private String enrollmentDate;
    private String semester;
    private String status;
    private double grade;
}
