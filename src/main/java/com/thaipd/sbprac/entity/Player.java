package com.thaipd.sbprac.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "player_Sequence")
    @SequenceGenerator(name = "player_Sequence", allocationSize = 1, sequenceName = "PLAYER_SEQ")
    private Long id;

    @Column(name = "name")
    private String name;

    //player's number is unique
    @Column(name = "num", unique=true)
    private Integer num;

    @Column(name = "position")
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Team team;

//    @Override
//    public String toString() {
//        return "Player {team=" + this.team.getName() + ", name=" + this.name + "}";
//    }
}
