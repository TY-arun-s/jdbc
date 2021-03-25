package com.tyss.jdbcApp.common;

import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.cj.jdbc.Driver; // MySQL version 8.0.11

//import com.mysql.jdbc.Driver; // MySQL version 5.1.38

public class MyFirstJDBCProgram {

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			/*
			 * FileOutputStream outputStream = new FileOutputStream("mysqldb.properties");
			 * Properties pro = new Properties(); pro.setProperty("user", "root");
			 * pro.setProperty("password", "root"); pro.store(outputStream, "comments");
			 * System.out.println("Properties File Created");
			 */

			// Read Property File
			FileInputStream inputStream = new FileInputStream("mysqldb.properties");
			Properties pro = new Properties();
			pro.load(inputStream);

			String username = (String) pro.get("user");
			String password = (String) pro.get("password");

			// 1. Load The Driver
			Driver driver = new Driver();

			DriverManager.registerDriver(driver);

			// 2. get the "DB Connection" via "Driver"

			String dburl = "jdbc:mysql://localhost:3306/tyss_db?autoReconnect=true&useSSL=false";
			conn = DriverManager.getConnection(dburl, username, password);

			/*
			 * String dburl = "jdbc:mysql://localhost:3306/tyss_db?user=root&password=root";
			 * conn = DriverManager.getConnection(dburl);
			 */

			// 3. Issue "SQL Queries" via "Connection"
			String sqlquery = "select * from emp where empno = 7521";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlquery);

			// 4.Process the results returned by "SQL Queries"
			while (rs.next()) {
				int id = rs.getInt("empno");
				String name = rs.getString("ename");
				String job = rs.getString("job");

				System.out.println("\nEmployee No : " + id);
				System.out.println("Employee Name : " + name);
				System.out.println("Employee Job : " + job);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				// 5. Close all the JDBC object
				if (conn != null) {
					conn.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} // End of Inner try-catch block

		} // End of Outer try-catch-finally block

	} // End of main method

} // End of class
