package model;

import lombok.Data;

@Data
public class Gun {
    private final int id;
    private final String name;
    private final double caliber;
}
