package com.zg.db.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) throws SQLException, Exception {

		Class.forName("org.h2.Driver");
		// app upgrade try to use jdbc:h2:~/o;IFEXISTS=TRUE since it indicates
		// that previous db must be existing.
		try (Connection conn = DriverManager.getConnection("jdbc:h2:~/booksystem", "sa", "sa")) {
			String init_sql_case_0 = "DROP TABLE IF EXISTS TEST;"
					+ "CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));"
					+ "INSERT INTO TEST VALUES(1, 'Hello');" + "INSERT INTO TEST VALUES(2, 'World');";
			String init_sql_case_1 = "CREATE TABLE IF NOT EXISTS TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));"
					+ "INSERT INTO TEST VALUES(3, 'Hello');" + "INSERT INTO TEST VALUES(4, 'World');";
			String query_sql = "SELECT * FROM user ORDER BY ID";
			String update_sql = "UPDATE TEST SET NAME='Hi' WHERE ID=1;";
			String del_sql = "DELETE FROM TEST WHERE ID=2;";
//			conn.prepareStatement(init_sql_case_0).execute();
			PreparedStatement ps = conn.prepareStatement(query_sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getNString(1)+","+rs.getNString(2)+","+rs.getNString(3));
//				System.out.println(rs.getArray(1)+","+rs.getArray(2));
			}
			// add application code here
		}
	}

}
