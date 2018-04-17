package media.data.model.base;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter @Setter
public abstract class StringIdBasedEntity extends BaseEntity<String> {

	@Id
	private String id;
	
}
