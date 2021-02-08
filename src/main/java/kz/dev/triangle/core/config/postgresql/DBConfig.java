package kz.dev.triangle.core.config.postgresql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Slf4j
@Service
public class DBConfig {

	private final static String driver = "org.postgresql.Driver";
	
	private String path;
	private String dbName;
	private String userName;
	private String password;
	
	private DataSource dataSource;
	
	@Autowired
	Environment env;
	
	private void initInternalFields(String urlPropName, String userNamePropName, String passwordPropName) {
		StringBuilder bld = new StringBuilder(env.getProperty(urlPropName));
		path = bld.substring(0, bld.lastIndexOf("/")+1);
		dbName = bld.substring(bld.lastIndexOf("/")+1);
		userName = env.getProperty(userNamePropName);
		password = env.getProperty(passwordPropName);
	}
	
	private void checkAccessDriver() throws Exception{
		Class.forName(driver);
		try (Connection cnt = DriverManager.getConnection(path + "template1",userName,password);){	
			if (cnt==null) throw new SQLException();
		}
	}
	
	private boolean isExistDataBase() throws Exception{
		try (Connection cnt = DriverManager.getConnection(path+dbName, userName, password);
				Statement statement = cnt.createStatement();){		
			String sql = "SELECT datname FROM pg_catalog.pg_database WHERE datname='" + dbName + "'";
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				if (resultSet.getString(1).equals(dbName)) {
					log.info("isExistDataBase result: ", resultSet.toString());
					try (Connection cnt1 = DriverManager.getConnection(path + dbName, userName, password)){
					log.info("Try to get connection with path: "+ path + dbName +" userName:  " + userName + ", password: " + password );
						return true;
					}
				}
			}
		} catch (Exception e) {
			log.error("Не удалось подключится к БД, для проверки наличия базы данных.",e);
		}
		return false;
	}
	
	private void instanceDB() throws Exception{
		try (Connection cnt = DriverManager.getConnection(path, userName, password);
				Statement statement = cnt.createStatement();){
			statement.execute("CREATE DATABASE " + dbName);
			try( Connection cnt1 = DriverManager.getConnection(path + dbName, userName,password)){
				if (cnt1==null) throw new Exception("Не удалось установить соединение с вновь созданной базой данных");
			}
		}
	}
	
	private void instanceDataSource() {
		log.info("Проверка доступности драйвера JDBC "+driver);
		try {				
			checkAccessDriver();	
			log.info("Драйвер - ОК!");	
		} catch (Exception e) {
			log.error("Драйвер недоступен \n", e);
			return;
		}		
		log.info("Проверка наличия базы данных "+dbName + " Path+dbname: " + path + dbName);
		try {
			if (isExistDataBase())
				log.info("Подключение прошло успешно");
			else {
				log.warn("База данных не найдена. Создание новой...");
				try {
					instanceDB();
					log.info("База создана. Соединение прошло успешно");
				} catch (Exception e1) {
					log.info("Не удалось создать базу данных или получить к ней доступ");
				}
			}
		} catch (Exception e) {
			log.error("Не удалось проверить наличие базы данных.",e);
		}
		
		dataSource = new DriverManagerDataSource(path+dbName, userName, password);
		
	}

	public DataSource getDataSource(String urlPropName, String userNamePropName, String passwordPropName) {
		if (dataSource==null) {
			initInternalFields(urlPropName, userNamePropName, passwordPropName);
			instanceDataSource();
		}
		return dataSource;
	}
}
