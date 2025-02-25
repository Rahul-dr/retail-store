package com.project.retail_store.entity;

import com.project.retail_store.Enum.ERole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;  // ROLE_CUSTOMER or ROLE_ADMIN

    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateModified;
    private boolean active = true;
}
