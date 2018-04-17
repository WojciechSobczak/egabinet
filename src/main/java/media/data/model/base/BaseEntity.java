package media.data.model.base;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class BaseEntity<T> implements Serializable {
	
	public abstract T getId();
	
	public boolean isNew() {
		return getId() == null;
	}
	
}
