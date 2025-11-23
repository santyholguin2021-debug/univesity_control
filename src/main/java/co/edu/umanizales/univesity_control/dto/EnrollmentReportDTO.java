package co.edu.umanizales.univesity_control.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EnrollmentReportDTO {
    private String id;
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseName;
    private LocalDate enrollmentDate;
    private String semester;
    private String status;
    private Double grade;
}
