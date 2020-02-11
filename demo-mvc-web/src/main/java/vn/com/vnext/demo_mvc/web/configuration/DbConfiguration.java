package vn.com.vnext.demo_mvc.web.configuration;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import vn.com.vnext.demo_mvc.dao.strategy.MySqlQueryGenerator;
import vn.com.vnext.demo_mvc.dao.strategy.QueryGenerator;


@Configuration
@PropertySource({"classpath:/datasource/db.properties"})
@EnableJpaRepositories(basePackages = {"vn.com.vnext.demo_mvc.dao"},
        entityManagerFactoryRef = "entityManager", transactionManagerRef = "transactionManager")
public class DbConfiguration {

    @Autowired
    private Environment env;

    /**
     * Initialization LocalContainerEntityManagerFactoryBean for Common database.
     * @return {@link LocalContainerEntityManagerFactoryBean} instance
     */
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"jp.co.sdc.discovery_faq.model", "jp.co.sdc.discovery_faq.model.base"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.order_inserts", "true");
        properties.put("hibernate.order_updates", "true");
        properties.put("hibernate.jdbc.batch_size", "2");
        properties.put("hibernate.show_sql", env.getProperty("show_sql"));

        em.setJpaPropertyMap(properties);

        return em;
    }

    /**
     * Initialization DataSource for Common database.
     * @return {@link DataSource} instance
     */
    @Primary
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));

        return dataSource;
    }

    /**
     * Initialization PlatformTransactionManager for Common database.
     * @return {@link PlatformTransactionManager} instance
     */
    @Primary
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }

    /**
     * PersistenceExceptionTranslationPostProcessor is a bean post processor which adds an advisor to any bean annotated
     * with Repository so that any platform-specific exceptions are caught and then re-thrown as one Spring's unchecked
     * data access exceptions (i.e. a subclass of DataAccessException).
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Identify strategy for Native Query Generator.
     * Default is {@link MySqlQueryGenerator}
     * @return {@link QueryGenerator} instance from [nativeQuery.className] configure value.
     */
    @Bean
    public QueryGenerator queryGenerator() {
        QueryGenerator generator = null;
        String className = env.getProperty("nativeQuery.className");
        try {
            generator = (QueryGenerator) Class.forName(className).newInstance();
        } catch (Exception ex) {
            generator = new MySqlQueryGenerator();
        }
        return generator;
    }

}
