package co.edu.umanizales.univesity_control.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {
    private String id;
    private String name;
    private String building;
    private int capacity;
    private String type;
    private String equipment;
}
