package co.edu.umanizales.univesity_control.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
