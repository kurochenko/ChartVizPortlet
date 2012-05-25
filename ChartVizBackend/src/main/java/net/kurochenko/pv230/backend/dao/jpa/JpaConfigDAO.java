package net.kurochenko.pv230.backend.dao.jpa;

import net.kurochenko.pv230.backend.dao.ConfigDAO;
import net.kurochenko.pv230.backend.model.Config;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Repository
public class JpaConfigDAO implements ConfigDAO {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(Config config) {
        if (config == null) {
            throw new IllegalArgumentException("Config is null");
        }

        em.persist(config);
    }

    @Override
    public Config load() {
        Query q = em.createQuery("SELECT c FROM Config c");
        List queryResult = q.getResultList();

        return (queryResult.isEmpty()) ? null : (Config) queryResult.get(0);
    }
}
