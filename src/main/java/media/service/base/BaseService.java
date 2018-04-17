package media.service.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import media.data.dao.base.IBaseDao;
import media.data.model.base.BaseEntity;

@SuppressWarnings("serial")
@Transactional(rollbackFor = Exception.class)
public abstract class BaseService<ID, T extends BaseEntity<ID>> implements IBaseService<ID, T> {
	
	public abstract IBaseDao<ID, T> getServiceBaseDao();

	@Override
	public T load(Long id) throws NullPointerException {
		if (id == null) {
			throw new NullPointerException("EntityId to load = null");
		}
		return getServiceBaseDao().load(id);
	}

	@Override
	public long save(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("Entity to save = null");
		}
		return getServiceBaseDao().save(t);
	}
	
	@Override
	public void update(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("Entity to update = null");
		}
		getServiceBaseDao().update(t);
	}

	@Override
	public void saveOrUpdate(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("Entity to save/update = null");
		}
		getServiceBaseDao().saveOrUpdate(t);
	}

	@Override
	public void delete(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("Entity to delete = null");
		}
		getServiceBaseDao().delete(t);
	}

	@Override
	public void delete(Long id) throws NullPointerException {
		if (id == null) {
			throw new NullPointerException("EntityId to delete = null");
		}
		getServiceBaseDao().delete(id);
	}

	@Override
	public void delete(Collection<Long> ids) throws NullPointerException {
		if (ids == null) {
			throw new NullPointerException("Entities IdList = null");
		}
		if (ids.isEmpty()) {
			return;
		}
		getServiceBaseDao().delete(ids);
	}

	@SuppressWarnings("unchecked")
	public List<T> getByIds(Collection<Long> ids) {
		if (ids == null) {
			throw new NullPointerException("Entities IdList = null");
		}
		if (ids.isEmpty()) {
			return new ArrayList<>();
		}
		return getServiceBaseDao().getByIds(ids);
	}

	@Override
	public T getById(Long id) throws NullPointerException {
		if (id == null) {
			throw new NullPointerException("EntityId to find = null");
		}
		return getServiceBaseDao().findById(id);
	}

	@Override
	public List<T> getList() {
		return getServiceBaseDao().getList();
	}
	
	
	@Override
	public long count() {
		return getServiceBaseDao().count();
	}
	

}
