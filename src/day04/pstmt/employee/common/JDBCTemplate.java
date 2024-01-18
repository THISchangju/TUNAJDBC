package day04.pstmt.employee.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Singletone {
	private static Singletone instance;

	private Singletone() {
	}

	public static Singletone getInstance() {
		if (instance == null) {
			instance = new Singletone();
		}
		return instance;
	}
}

public class JDBCTemplate {
	private static JDBCTemplate instance;
	private static Connection conn;

	private JDBCTemplate() {
	} // 아무도 객체 생성을 못하도록 함

	public static JDBCTemplate getInstance() {
		if (instance == null) { // 객체가 만들어져 있는지 체크 후
			instance = new JDBCTemplate(); // 없으면 만들어줌
		}
		return instance; // 만들어
	}

	final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final String USERNAME = "student";
	final String PASSWORD = "student";

	private Connection getConnection() throws Exception {
		if (conn == null || conn.isClosed()) {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		}
		return conn;
	}
}