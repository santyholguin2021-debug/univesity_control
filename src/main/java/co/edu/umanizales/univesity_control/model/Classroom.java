package co.edu.umanizales.univesity_control.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Classroom {
    private String id;
    private String name;
    private String building;
    private int capacity;
    private String type;
    private String equipment;

    public Classroom(String id, String name, String building, int capacity, String type, String equipment) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.capacity = capacity;
        this.type = type;
        this.equipment = equipment;
    }
}
