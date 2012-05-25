package net.kurochenko.pv230.backend.dao.jpa;

import net.kurochenko.pv230.backend.dao.CurrencyValueDAO;
import net.kurochenko.pv230.backend.model.Currency;
import net.kurochenko.pv230.backend.model.CurrencyValue;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Repository
public class JpaCurrencyValueDAO implements CurrencyValueDAO {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void create(CurrencyValue cv) {
        if (cv == null) {
            throw new IllegalArgumentException("Currency value is null");
        }

        em.persist(cv);
    }

    @Override
    public CurrencyValue find(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Currency value ID is null");
        }

        return em.find(CurrencyValue.class, id);
    }

    @Override
    public CurrencyValue find(String name, Date date) {
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }

        Query q = em.createQuery("SELECT c FROM CurrencyValue c WHERE c.currency.name = :name AND c.time = :date");
        q.setParameter("name", name);
        q.setParameter("date", date);

        List results = q.getResultList();
        return  (!results.isEmpty())
                ? (CurrencyValue) results.get(0)
                : null;
    }

    @Override
    public List<CurrencyValue> findRange(Currency currency, Date from) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency is null");
        }
        if (from == null) {
            throw new IllegalArgumentException("From is null");
        }

        Query q = em.createQuery("SELECT c FROM CurrencyValue c WHERE c.currency = :currency AND c.time > :from ORDER BY c.time");
        q.setParameter("currency", currency);
        q.setParameter("from", from);

        return q.getResultList();
    }

    @Override
    public void clear() {
        Query q = em.createQuery("DELETE FROM CurrencyValue  c");
        q.executeUpdate();
    }
}
