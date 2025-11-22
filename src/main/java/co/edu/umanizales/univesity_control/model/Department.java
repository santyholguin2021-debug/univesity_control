
package co.edu.umanizales.univesity_control.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Department {
    private String id;
    private String name;
    private String code;

    // Referencias a objetos para relaciones (no se serializan en CSV)
    @JsonBackReference("faculty-departments")
    private Faculty faculty;

    @JsonBackReference("department-head")
    private Professor headProfessor;

    @JsonManagedReference("department-courses")
    @JsonIgnore
    private List<Course> courses = new ArrayList<>();

    @JsonManagedReference("department-professors")
    @JsonIgnore
    private List<Professor> professors = new ArrayList<>();

    @JsonManagedReference("department-employees")
    @JsonIgnore
    private List<AdministrativeEmployee> employees = new ArrayList<>();

    public Department(String id, String name, String code, Faculty faculty, Professor headProfessor) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.faculty = faculty;
        this.headProfessor = headProfessor;
    }
}
