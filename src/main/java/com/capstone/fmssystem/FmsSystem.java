package com.capstone.fmssystem;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import com.github.jasync.sql.db.mysql.util.URLParser;

@SpringBootApplication
@EnableR2dbcRepositories
@ComponentScan(basePackages=" com.capstone")
public class FmsSystem {

	public static void main(String[] args) {
		SpringApplication.run(FmsSystem.class, args);
	}
}

@Configuration
class MySqlConfiguration extends AbstractR2dbcConfiguration {

	@Override
	@Bean
	public io.r2dbc.spi.ConnectionFactory connectionFactory() {
		String url = "mysql://root:root@localhost:3306/fmsdb";
		return new JasyncConnectionFactory(
				new MySQLConnectionFactory(URLParser.INSTANCE.parseOrDie(url, StandardCharsets.UTF_8)));
	}

}