package media.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import media.config.security.SecurityCorsFilter;

@Configuration
@ComponentScan("media")
@EnableTransactionManagement
@EnableWebMvc
public class SpringConfiguration extends WebMvcConfigurerAdapter {
	
	public static final boolean LOCAL = false;
	public static final boolean BACKEND_TEST = false;
	
	public static final boolean SWAGGER = true;
	

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		if (LOCAL) {
			dataSource.setUrl("jdbc:mysql://192.168.1.11:3306/media?useUnicode=yes&characterEncoding=UTF-8&useSSL=true");
			dataSource.setUsername("root");
			dataSource.setPassword("superniepewnetohaslopowiemwam");
		} else {
			dataSource.setUrl("jdbc:mysql://37.187.180.163:3306/media?useUnicode=yes&characterEncoding=UTF-8&useSSL=true");
			dataSource.setUsername("root");
			dataSource.setPassword("motznehaslo");
		}
		
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(dataSource());
		localSessionFactoryBean.setPackagesToScan("media.data.*");
		localSessionFactoryBean.setHibernateProperties(hibernateProperties());
		return localSessionFactoryBean;
	}

	public Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();	
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.jdbc.batch_size", "20");
		
		hibernateProperties.setProperty("hibernate.connection.characterEncoding", "utf8");
		hibernateProperties.setProperty("hibernate.connection.CharSet", "utf8");
		hibernateProperties.setProperty("hibernate.connection.useUnicode", "true");
		return hibernateProperties;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setDataSource(dataSource());
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("*").allowCredentials(true).allowedHeaders("*");
	}
	
	@Bean(name = "corsFilter")
	public SecurityCorsFilter corsFilter() {
		return new SecurityCorsFilter();
	}
	
}