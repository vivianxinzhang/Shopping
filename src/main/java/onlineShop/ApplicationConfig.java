package onlineShop;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class ApplicationConfig {

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("onlineShop.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        // 只需要修改红色部分, 保留其他内容

//        dataSource.setUrl("jdbc:mysql://YOUR_RDS_IP:3306/ecommerce?createDatabaseIfNotExist=true&serverTimezone=UTC");
        dataSource.setUrl("jdbc:mysql://mysqldatabase.cw8kc8mzwiml.us-east-2.rds.amazonaws.com:3306/ecommerce?createDatabaseIfNotExist=true&serverTimezone=UTC");
        dataSource.setUsername("USERNAME");
        dataSource.setPassword("PASSWORD");

        return dataSource;
    }


    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"); // if mysql version is 8, MySQL5InnoDBDialect
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        return hibernateProperties;
    }
}