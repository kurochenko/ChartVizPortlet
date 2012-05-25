package net.kurochenko.pv230.backend.service;

import net.kurochenko.pv230.backend.model.Config;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface ConfigService {

    void save(Config config);
    Config load();

}
