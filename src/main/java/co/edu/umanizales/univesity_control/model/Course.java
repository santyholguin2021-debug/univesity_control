
package co.edu.umanizales.univesity_control.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Course {
    private String id;
    private String name;
    private String code;
    private int credits;
    private String description;

    // ID para persistencia CSV
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String departmentId;

    // Referencias a objetos para relaciones (no se serializan en CSV)
    @JsonBackReference("department-courses")
    private Department department;

    @JsonManagedReference("course-enrollments")
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();

    @JsonManagedReference("course-assignments")
    @JsonIgnore
    private List<ProfessorAssignment> professorAssignments = new ArrayList<>();

    public Course(String id, String name, String code, int credits, String description, Department department) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.description = description;
        this.department = department;
        this.departmentId = department != null ? department.getId() : null;
    }
}
