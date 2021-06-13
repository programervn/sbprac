package com.thaipd.sbprac.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    @Id
    //@Column(name="ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CUST_SEQ")
    @SequenceGenerator(sequenceName = "PERSON_SEQ", allocationSize = 1, name = "GEN_CUST_SEQ")
    private Long id;

    //@Column(name="FIRSTNAME")
    private String firstName;

    //@Column(name = "LASTNAME")
    private String lastName;
}
