
package co.edu.umanizales.univesity_control.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessorAssignment {
    private String id;

    // IDs para persistencia CSV

    // Referencias a objetos para relaciones (no se serializan en CSV)
    @JsonBackReference("professor-assignments")
    private Professor professor;

    @JsonBackReference("course-assignments")
    private Course course;

    private String semester;
    private String assignmentDate;
    private String schedule;

    public ProfessorAssignment(String id, Professor professor, Course course,
                               String semester, String assignmentDate, String schedule) {
        this.id = id;
        this.professor = professor;
        this.course = course;
        this.semester = semester;
        this.assignmentDate = assignmentDate;
        this.schedule = schedule;
    }
}
