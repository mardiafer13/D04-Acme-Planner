/*
 * Shout.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.entities.controlCheck;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ControlCheck extends DomainEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				momento;
	
	@Column(unique=true)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				date;
	
	@NotNull
	protected Money				money;

	@NotNull
	protected Boolean       	isCheck;


	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
