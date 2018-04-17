package media.data.dao.base;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import media.data.model.base.BaseEntity;

@SuppressWarnings({ "serial", "deprecation" })
public abstract class BaseDao<ID, T extends BaseEntity<ID>> implements IBaseDao<ID, T> {

	public abstract Class<T> getEntityClass();

	protected abstract Session getSession();

	protected CriteriaQuery<T> createCriteriaQuery() {
		return getSession().getCriteriaBuilder().createQuery(getEntityClass());
	}

	protected Criteria createCriteria() {
		return getSession().createCriteria(getEntityClass());
	}

	protected Query createQuery(String query) {
		return getSession().createQuery(query);
	}
	
	protected NativeQuery<T> createSQLQuery(String query) {
		NativeQuery<T> sqlQuery = getSession().createSQLQuery(query);
		return sqlQuery;
	}

	@SuppressWarnings("unchecked")
	public T load(Long id) {
		return (T) getSession().load(this.getEntityClass(), id);
	}

	@SuppressWarnings("unchecked")
	public T findById(Long id) {
		return (T) getSession().get(this.getEntityClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getByIds(Collection<Long> ids) {
		Query query = createQuery("FROM " + getEntityClass().getName() + " WHERE id IN :ids");
		query.setParameter("ids", ids);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList() {
		Query query = createQuery("FROM " + getEntityClass().getName());
		return query.getResultList();
	}

	public long save(T t) {
		return (long) getSession().save(t);
	}

	public void update(T t) {
		getSession().update(t);
	}

	public void delete(T t) {
		getSession().delete(t);
		getSession().flush();
	}

	public void delete(Long id) {
		Query query = createQuery("DELETE FROM " + getEntityClass().getName() + " WHERE id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

	public void delete(Collection<Long> ids) {
		Query query = createQuery("DELETE FROM " + getEntityClass().getName() + " WHERE id IN :ids");
		query.setParameter("ids", ids);
		query.executeUpdate();
	}

	public void saveOrUpdate(T t) {
		getSession().saveOrUpdate(t);
	}

	public long count() {
		Query query = createQuery("SELECT COUNT(*) FROM " + getEntityClass().getName());
		return (long) query.getSingleResult();
	}

}