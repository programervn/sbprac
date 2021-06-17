package com.thaipd.sbprac.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "roles_Sequence")
	@SequenceGenerator(name = "roles_Sequence", allocationSize = 1, sequenceName = "ROLES_SEQ")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role() {

	}

	public Role(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}