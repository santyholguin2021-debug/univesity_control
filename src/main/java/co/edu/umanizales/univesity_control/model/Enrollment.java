package co.edu.umanizales.univesity_control.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Enrollment {
    private String id;

    // IDs para persistencia CSV
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String studentId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String courseId;

    // Referencias a objetos para relaciones (no se serializan en CSV)
    @JsonBackReference("student-enrollments")
    private Student student;

    @JsonBackReference("course-enrollments")
    private Course course;

    private String enrollmentDate;
    private String semester;
    private String status;
    private double grade;

    public Enrollment(String id, Student student, Course course, String enrollmentDate,
                      String semester, String status, double grade) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.studentId = student != null ? student.getId() : null;
        this.courseId = course != null ? course.getId() : null;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
        this.status = status;
        this.grade = grade;
    }
}