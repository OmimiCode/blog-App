package com.blogApplication;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class BlogApplicationTests {
	@Autowired
	DataSource datasource;
	@Test
	void appConnectionToDatabaseTest() {
		assertThat(datasource).isNotNull();
		log.info("Datasource object created");
		try(Connection connection = datasource.getConnection()){
			assertThat(connection).isNotNull();
			assertThat(connection.getCatalog()).isEqualTo("blogdb");
			log.info("Connection --> {} ", connection.getCatalog());

		}catch (SQLException throwables){
			log.info("Exception occurred while connecting to database --> {}", throwables.getMessage());
		}

	}

	@Test
	void readValuesFromPropertiesTest(){

	}

}
