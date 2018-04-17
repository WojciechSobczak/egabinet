package media.service.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import media.data.model.base.BaseEntity;

public interface IBaseService<ID, T extends BaseEntity<ID>> extends Serializable {
	
	public T load(Long id) throws NullPointerException;
	
	public long save(T t) throws NullPointerException;
	
	public void update(T t) throws NullPointerException;
	
	public void saveOrUpdate(T t) throws NullPointerException;
	
	public void delete(T t) throws NullPointerException;
	
	public void delete(Long id) throws NullPointerException;
	
	public void delete(Collection<Long> ids) throws NullPointerException;;
	
	public List<T> getByIds(Collection<Long> ids) throws NullPointerException;;
	
	public T getById(Long id) throws NullPointerException;
	
	public List<T> getList();
	
	public long count();
	
}
