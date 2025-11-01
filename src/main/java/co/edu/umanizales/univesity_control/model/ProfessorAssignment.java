package co.edu.umanizales.univesity_control.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorAssignment {
    private String id;
    private String professorId;
    private String courseId;
    private String semester;
    private String assignmentDate;
    private String schedule;
}
