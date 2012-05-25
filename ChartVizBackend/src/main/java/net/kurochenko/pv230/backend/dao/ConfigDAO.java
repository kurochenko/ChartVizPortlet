package net.kurochenko.pv230.backend.dao;

import net.kurochenko.pv230.backend.model.Config;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface ConfigDAO {

    void save(Config config);
    Config load();

}
