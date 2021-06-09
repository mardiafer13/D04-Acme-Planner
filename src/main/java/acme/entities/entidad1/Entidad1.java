package acme.entities.entidad1;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.entities.shouts.Shout;
import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Entidad1 extends DomainEntity {

	protected static final long	serialVersionUID	= 1L;
	
	@NotBlank
    @Pattern(regexp = "([12]\\d{3}/(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01]))", message = "Invalid format")
    protected String atributo1;
	
	@NotNull
    @Temporal(TemporalType.TIMESTAMP)
	protected Date atributo2;
	
	@Valid
    @NotNull
	protected Money atributo3;
	
	@NotNull
	protected Boolean atributo4;
	
	@Valid
	@OneToOne(mappedBy = "entidad1")
	protected Shout shout;
}
