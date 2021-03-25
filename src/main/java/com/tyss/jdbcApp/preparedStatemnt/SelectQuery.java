package com.tyss.jdbcApp.preparedStatemnt;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.Driver;

public class SelectQuery {
	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

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

			// 3. Issue "SQL Queries" via "Connection"
			String sqlquery = "select * from person_tb where id = ?";
			pstmt = conn.prepareStatement(sqlquery);
			pstmt.setInt(1, 10);
//			pstmt.setInt(1, Integer.parseInt(args[0]));
			rs = pstmt.executeQuery();

			// 4.Process the results returned by "SQL Queries"
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				System.out.println("\nPerson No : " + id);
				System.out.println("Person Name : " + name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				// 5. Close all the JDBC object
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
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
