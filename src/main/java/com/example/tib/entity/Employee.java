package com.example.tib.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Employees")
@Getter
@Setter
@JsonIgnoreProperties({"photo","reportsTo","photoPath"})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID", nullable = false)
    private Integer id;

    @Column(name = "LastName", nullable = false, length = 20)
    private String lastName;

    @Column(name = "FirstName", nullable = false, length = 10)
    private String firstName;

    @Column(name = "Title", length = 30)
    private String title;

    @Column(name = "TitleOfCourtesy", length = 25)
    private String titleOfCourtesy;

    @Column(name = "BirthDate")
    private java.sql.Date birthDate;

    @Column(name = "HireDate")
    private java.sql.Date hireDate;

    @Column(name = "Address", length = 60)
    private String address;

    @Column(name = "City",length = 15)
    private String city;

    @Column(name = "Region", length = 15)
    private String region;

    @Column(name = "PostalCode", length = 10)
    private String postalCode;

    @Column(name = "Country", length = 15)
    private String country;

    @Column(name = "HomePhone", length = 24)
    private String homePhone;

    @Column(name = "Extension",length = 4)
    private String extension;

    @Column(name = "Photo")
    private byte[] photo;

    @Lob
    @Column(name = "Notes", nullable = false)
    private String notes;

    @Column(name = "ReportsTo",length = 11)
    private Integer reportsTo;

    @Column(name = "PhotoPath")
    private String photoPath;

    @Column(name = "Salary")
    private Float salary;

}
