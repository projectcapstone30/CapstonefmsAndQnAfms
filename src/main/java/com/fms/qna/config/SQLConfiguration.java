package com.fms.qna.config;

import io.r2dbc.spi.ConnectionFactory;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import com.github.jasync.sql.db.mysql.util.URLParser;

@Configuration
public class SQLConfiguration extends AbstractR2dbcConfiguration{
	
	@Override
	@Bean
	public ConnectionFactory connectionFactory() {
		String url="mysql://root:root123@localhost:3306/fms";
		
		return new JasyncConnectionFactory(new MySQLConnectionFactory(URLParser.INSTANCE.parseOrDie(url, StandardCharsets.UTF_8)));
	}
	

}
