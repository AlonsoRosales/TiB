package com.example.tib.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "Shippers")
@Getter
@Setter
public class Shipper {
    @Id
    @Column(name = "ShipperID", nullable = false, length = 11)
    private Integer id;

    @Column(name = "CompanyName", nullable = false, length = 40)
    private String companyName;

    @Column(name = "Phone", length = 24)
    private String phone;

}
