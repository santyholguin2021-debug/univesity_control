package co.edu.umanizales.univesity_control.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
    private String id;
    private String name;
    private String code;
    private String deanId;
    private String description;
}
