package media.data.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import media.data.model.auth.Token;
import media.data.model.base.NumericIdBasedEntity;

@SuppressWarnings("serial")
@Entity
@Getter @Setter
@Table(name = "doctors")
public class Doctor extends NumericIdBasedEntity {
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Patient> patients;
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
	private Set<Appointment> appointments;
	
}
