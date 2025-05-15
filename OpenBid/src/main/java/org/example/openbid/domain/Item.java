package org.example.openbid.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser owner;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;
    private Double startingBid;


}

