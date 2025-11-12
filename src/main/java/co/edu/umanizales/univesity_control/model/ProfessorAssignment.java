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
    private String professorId;
    private String courseId;
    
    // Referencias a objetos para relaciones (no se serializan en CSV)
    @JsonBackReference("professor-assignments")
    @JsonIgnore
    private Professor professor;
    
    @JsonBackReference("course-assignments")
    @JsonIgnore
    private Course course;
    
    private String semester;
    private String assignmentDate;
    private String schedule;
    
    public ProfessorAssignment(String id, String professorId, String courseId, 
                              String semester, String assignmentDate, String schedule) {
        this.id = id;
        this.professorId = professorId;
        this.courseId = courseId;
        this.semester = semester;
        this.assignmentDate = assignmentDate;
        this.schedule = schedule;
    }
}
