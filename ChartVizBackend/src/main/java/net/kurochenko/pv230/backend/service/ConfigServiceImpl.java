package net.kurochenko.pv230.backend.service;

import net.kurochenko.pv230.backend.dao.ConfigDAO;
import net.kurochenko.pv230.backend.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Service
@Transactional
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigDAO configDAO;


    @Override
    public void save(Config config) {
        if (config == null) {
            throw new IllegalArgumentException("COnfig is null");
        }

        configDAO.save(config);
    }

    @Override
    @Transactional(readOnly = true)
    public Config load() {
        return configDAO.load();
    }
}
