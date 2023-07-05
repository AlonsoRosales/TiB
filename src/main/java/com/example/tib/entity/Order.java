package com.example.tib.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID", nullable = false, length = 11)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customerID;

    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    private Employee employeeID;

    @Column(name = "OrderDate")
    private Date orderDate;

    @Column(name = "RequiredDate")
    private Date requiredDate;

    @Column(name = "ShippedDate")
    private Date shippedDate;

    @ManyToOne
    @JoinColumn(name = "ShipVia")
    private Shipper shipVia;

    @Column(name = "Freight", precision = 10, scale = 4)
    private BigDecimal freight;

    @Column(name = "ShipName", length = 40)
    private String shipName;

    @Column(name = "ShipAddress", length = 60)
    private String shipAddress;

    @Column(name = "ShipCity", length = 15)
    private String shipCity;

    @Column(name = "ShipRegion", length = 15)
    private String shipRegion;

    @Column(name = "ShipPostalCode", length = 10)
    private String shipPostalCode;

    @Column(name = "ShipCountry", length = 15)
    private String shipCountry;
}
