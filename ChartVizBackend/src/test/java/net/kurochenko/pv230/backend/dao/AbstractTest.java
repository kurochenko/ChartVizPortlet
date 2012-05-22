package net.kurochenko.pv230.backend.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/test-data-source.xml", "classpath:/META-INF/spring-backend.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AbstractTest {
}
