package configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
public class DatabaseConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setJpaVendorAdapter(vendorAdapter);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("q1w2e3r4");
        dataSource.setUrl("jdbc:mysql://localhost:3306/power");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        factoryBean.setDataSource(dataSource);

        Properties propriedadesHibernate = new Properties();
        propriedadesHibernate.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        propriedadesHibernate.setProperty("hibernate.show_sql", "true");
        propriedadesHibernate.setProperty("hibernate.hbm2ddl.auto", "update");

        factoryBean.setJpaProperties(propriedadesHibernate);

        factoryBean.setPackagesToScan("model");

        return factoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        entityManagerFactory.createEntityManager();
        return new JpaTransactionManager(entityManagerFactory);
    }
}

