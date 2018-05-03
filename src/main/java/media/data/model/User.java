package media.data.model;


import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import media.data.model.auth.Token;
import media.data.model.base.NumericIdBasedEntity;

@SuppressWarnings("serial")
@Getter @Setter
@Entity
@Table(name = "main_users")
public class User extends NumericIdBasedEntity {
	
	private String email;
	private String password;
	private LocalDateTime creationDate;
	private boolean active;
	
	private String name;
	private String surname;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Token> tokens;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Doctor doctor;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
	private boolean admin;
	
	public boolean hasPatient() {
		return patient != null && patient.getId() != null;
	}
	
	public boolean hasDoctor() {
		return doctor != null && doctor.getId() != null;
	}
	
}
