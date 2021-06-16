package com.thaipd.sbprac.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Ramesh Fadatare
 */
@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "roles_Sequence")
    @SequenceGenerator(name = "roles_Sequence", allocationSize = 1, sequenceName = "ROLES_SEQ")
    private Integer id;
    @Column(nullable = false, unique = true)
    @NotEmpty
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
