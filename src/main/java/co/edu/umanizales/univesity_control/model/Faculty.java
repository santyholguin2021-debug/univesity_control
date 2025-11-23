
package co.edu.umanizales.univesity_control.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    // ID para persistencia CSV
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String deanId;

    // Referencias a objetos para relaciones (no se serializan en CSV)
    @JsonManagedReference("faculty-departments")
    @JsonIgnore
    private List<Department> departments = new ArrayList<>();

    private Professor dean;

    public Faculty(String id, String name, String code, Professor dean, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.dean = dean;
        this.deanId = dean != null ? dean.getId() : null;
        this.description = description;
    }
}
