package media.data.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import media.data.model.base.NumericIdBasedEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "patients")
@Getter @Setter
public class Patient extends NumericIdBasedEntity {
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Doctor> doctors;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private Set<Appointment> appointments;

}
