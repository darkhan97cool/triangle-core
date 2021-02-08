package kz.dev.triangle.core.config.postgresql;

import kz.dev.triangle.core.config.postgresql.DBConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgreConfiguration {

	
	@Autowired
	DBConfig dbConfig;
	
	private final static String userName = "username";
	private final static String url = "url";
	private final static String password = "password";
	private final static String prefix = "spring.datasource.";		

	
	@Bean(name="dataSource")
	@ConditionalOnResource(resources = "classpath:application.properties")	
	@ConditionalOnProperty(prefix=prefix,
	  name = {url,userName,password})							
	@ConditionalOnMissingBean
	public DataSource initDB(){
		return dbConfig.getDataSource(prefix.concat(url), prefix.concat(userName), prefix.concat(password));
	}
	
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize(DataSize.ofKilobytes(128));
//        factory.setMaxRequestSize(DataSize.ofKilobytes(128));;
//        return factory.createMultipartConfig();
//    }
	
//	@Bean
//	public DataFormater getDataFormater() {
//		return new DataFormater();
//	}
	
}
