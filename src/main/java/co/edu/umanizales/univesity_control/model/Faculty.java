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
    private String deanId; // ID para persistencia CSV
    private String description;
    
    // Referencias a objetos para relaciones (no se serializan en CSV)
    @JsonManagedReference("faculty-departments")
    @JsonIgnore
    private List<Department> departments = new ArrayList<>();
    
    public Faculty(String id, String name, String code, String deanId, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.deanId = deanId;
        this.description = description;
    }
}
