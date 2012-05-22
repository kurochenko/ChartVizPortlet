package net.kurochenko.pv230.backend.dao.jpa;

import net.kurochenko.pv230.backend.dao.CurrencyDAO;
import net.kurochenko.pv230.backend.model.Currency;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Repository
public class JpaCurrencyDAO implements CurrencyDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency is null");
        }

        em.persist(currency);
    }

    @Override
    public Currency find(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is null");
        }

        return em.find(Currency.class, id);
    }

    @Override
    public Currency find(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }

        Query q = em.createQuery("SELECT c FROM Currency c WHERE c.name = :name");
        q.setParameter("name", name);
        return (Currency) q.getSingleResult();
    }

    @Override
    public List<Currency> findAll() {
        Query q = em.createQuery("SELECT c FROM Currency c");
        return  q.getResultList();
    }
}
