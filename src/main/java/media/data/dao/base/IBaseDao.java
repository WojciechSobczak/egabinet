package media.data.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import media.data.model.base.BaseEntity;

	
public interface IBaseDao<ID, T extends BaseEntity<ID>> extends Serializable {
	
	public T load(Long id);
	
	public long save(T t);
	
	public void update(T t);
	
	public void saveOrUpdate(T t);
	
	public void delete(T t);
	
	public void delete(Long id);
	
	public void delete(Collection<Long> ids);
	
	public List<T> getByIds(Collection<Long> ids);
	
	public T findById(Long id);
	
	public List<T> getList();
	public long count();
	

}
