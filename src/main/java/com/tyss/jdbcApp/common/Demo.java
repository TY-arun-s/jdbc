package com.tyss.jdbcApp.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;


public class Demo {

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			// 1. Load The Driver
			Driver driver=new Driver();

			DriverManager.registerDriver(driver);

			// 2. get the "DB Connection" via "Driver"

			String dburl = "jdbc:mysql://localhost:3306/tyss_db?autoReconnect=true&useSSL=false";
			conn = DriverManager.getConnection(dburl, "root", "root");

			// 3. Issue "SQL Queries" via "Connection"
			String sqlquery = "select * from person_tb where id = ?";
			pstmt = conn.prepareStatement(sqlquery);

			for (int i = 0; i < 20; i++) {
				pstmt.setInt(1, i);

				rs = pstmt.executeQuery();

				// 4.Process the results returned by "SQL Queries"
				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");

					System.out.println("\nEmployee No : " + id);
					System.out.println("Employee Name : " + name);
				}

				Thread.sleep(1000);
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







