package org.example.openbid.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String name;
    private Double price;
    private String category;
    private Date date;
    private AppUser bidder;
    private AppUser owner;
    private String image;
    private String description;
}

