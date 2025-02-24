package com.project.retail_store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    private LocalDateTime dateCreated;
    private String userCreated;
    private LocalDateTime dateModified;
    private Long userModified;
    private boolean active = true;
}