package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Person {
    private final int id;
    private String firstName;
    private String lastName;
    private boolean permission;
    private final LocalDate dob;
    private String email;
    private String password;
    private String address;
    private String telephone;
}
