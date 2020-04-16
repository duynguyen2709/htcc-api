package htcc.gateway.service.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Datasource for HTCC_Admin
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "htcc.gateway.service.repository.jpa.admin",
        entityManagerFactoryRef = "adminEntityManager",
        transactionManagerRef = "adminTransactionManager")
public class AdminDataSourceConfig {

    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("adminDataSource")
    private DataSource adminDataSource;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean adminEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(adminDataSource);
        em.setPackagesToScan("htcc.gateway.service.entity.jpa.admin");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager adminTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(adminEntityManager().getObject());
        return transactionManager;
    }

}
