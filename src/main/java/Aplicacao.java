import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@EnableTransactionManagement
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = {"model"})
public class Aplicacao {

    public static void main(String[] args) {
        SpringApplication.run(Aplicacao.class, args);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setJpaVendorAdapter(vendorAdapter);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("q1w2e3r4");
        dataSource.setUrl("jdbc:mysql://localhost:3306/power?useSSL=false");
        /**/
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        factoryBean.setDataSource(dataSource);

        Properties propriedadesHibernate = new Properties();
        propriedadesHibernate.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
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
