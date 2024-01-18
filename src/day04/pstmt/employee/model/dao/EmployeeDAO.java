package day04.pstmt.employee.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import day04.pstmt.employee.model.vo.Employee;
import day04.pstmt.member.common.JDBCTemplate;

public class EmployeeDAO {
	
	private JDBCTemplate jdbcTemplate;
	
	public EmployeeDAO() {
		jdbcTemplate = JDBCTemplate.getInstance();
	}
	
	
	/*
	 * 1. 드라이버 등록
	 * 2. DBMS 연결 생성
	 * 3. Statement 생성
	 * 4. 쿼리문 실행 및 5. 결과받기
	 * 6. 자원해제
	 * 
	 */

	
	public int updateEmployee(Employee emp) {
		String query = "UPADTE EMPLOYEE SET EMAIL = ?, ?" + "WHERE EMP_ID ?";
		int result = -1;
		Connection conn;
		try {
			conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setString(3, emp.getEmpId());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	
	
	
	public void insertEmployee(Employee emp) {
		String query = "INSERT INTO EMPLOYEE" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, null, 'N')";
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setString(11, emp.getManagerId());
			int result = pstmt.executeUpdate();
			if(result > 0) {
				// 성공 - commit
			}else {
				// 실패 - rollback
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Employee> selectAllEmployees() {
		String query = "SELECT * FROM EMPLOYEE";
		List<Employee> eList = null;
		try {
			Connection conn = jdbcTemplate.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			eList = new ArrayList<Employee>();
			// 후처리
			while(rset.next()) {
				Employee employee = new Employee();
				employee = this.rsetEmployee(rset);
				eList.add(employee);
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eList;
	}


	public int deleteEmployee(String empId) {
		String query = "DELETE FROM EMPLOYEE WHERE EMP_ID = ?";
		int result = -1;
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, empId);
//			result = stmt.executeUpdate(query);
			result = pstmt.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	private Employee rsetEmployee(ResultSet rset) throws SQLException {
		Employee employee = new Employee();
		employee.setEmpId(rset.getString("EMP_ID"));
		employee.setEmpName(rset.getString("EMP_NAME"));
		employee.setEmpNo(rset.getString("EMP_NO"));
		employee.setEmail(rset.getString("EMAIL"));
		employee.setPhone(rset.getString("PHONE"));
		employee.setDeptCode(rset.getString("DEPT_CODE"));
		employee.setJobCode(rset.getString("JOB_CODE"));
		employee.setSalLevel(rset.getString("SAL_LEVEL"));
		employee.setSalary(rset.getInt("SALARY")); // setter 메소드가 int를 매개변수로 받기 때문 getInt() 사용
		employee.setBonus(rset.getDouble("BONUS")); // setter 메소드가 double을 매개변수로 받기 때문 getDouble() 사용
		employee.setManagerId(rset.getString("MANAGER_ID"));
		employee.setHireDate(rset.getDate("HIRE_DATE"));
		employee.setEntDate(rset.getDate("ENT_DATE"));// setter 메소드가 date을 매개변수로 받기 때문 getDate() 사용
		employee.setEntYn(rset.getString("ENT_YN"));
		return employee;
	}
}







