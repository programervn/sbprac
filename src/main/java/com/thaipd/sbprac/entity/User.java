package com.thaipd.sbprac.entity;

import java.util.List;


import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "users_Sequence")
    @SequenceGenerator(name = "users_Sequence", allocationSize = 1, sequenceName = "USERS_SEQ")
    private Long id;
    @Column(nullable = false)
    @NotEmpty
    private String name;
    @Column(nullable = false, unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;
    @Column(nullable = false)
    @javax.validation.constraints.NotEmpty
    @Size(min = 4)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles;
}
