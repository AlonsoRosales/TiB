package com.example.tib.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CustomerDemographics")
@Getter
@Setter
public class CustomerDemoGraphics {
    @Id
    @Column(name = "CustomerTypeID", nullable = false, length = 10)
    private String id;

    @Lob
    @Column(name = "CustomerDesc")
    private String customerDesc;
}
