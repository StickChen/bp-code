package com.shallop.bpc.collection.basic.java.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author chenxuanlong
 * @date 2017/8/31
 */
public class JdbcTemplateDemo {

	private static class JdbcTemplateHolder{

		private static JdbcTemplate jdbcTemplate;

		static {
			jdbcTemplate = new JdbcTemplate();

		}

	}



}
