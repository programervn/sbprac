package com.thaipd.sbprac.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "team_Sequence")
    @SequenceGenerator(name = "team_Sequence", allocationSize = 1, sequenceName = "TEAM_SEQ")
    private Long id;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "Team {id=" + id + ", name=" + this.name + "}";
    }
}
