package co.edu.umanizales.univesity_control.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Faculty {
    private String id;
    private String name;
    private String code;
    private String description;
    
    // Referencias a objetos para relaciones (no se serializan en CSV)
    @JsonManagedReference("faculty-departments")
    @JsonIgnore
    private List<Department> departments = new ArrayList<>();
    
    @JsonIgnore
    private Professor dean;
    
    public Faculty(String id, String name, String code, Professor dean, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.dean = dean;
        this.description = description;
    }
}
