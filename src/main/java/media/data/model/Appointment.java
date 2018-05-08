package media.data.model;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import media.data.model.base.NumericIdBasedEntity;

@SuppressWarnings("serial")
@Entity
@Getter @Setter
@Table(name = "appointments")
public class Appointment extends NumericIdBasedEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
	private LocalDateTime start;
	
	private LocalDateTime end;

}













