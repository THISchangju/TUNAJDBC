package day04.pstmt.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import day04.pstmt.member.common.JDBCTemplate;
import day04.pstmt.member.model.vo.Member;

public class MemberDAO {
	private JDBCTemplate jdbcTemplate;
	
	public MemberDAO() {
//		jdbcTemplate = new JDBCTemplate();
		jdbcTemplate = JDBCTemplate.getInstance();
		
	}

	/*
	 * 1. 드라이버 등록 2. DB 연결 생성 3. 쿼리문 실행 준비 4. 쿼리문 실행 및 5. 결과 받기 6. 자원해제(close())
	 */
	public void updateMember(Member member) {
//		String query = "UPDATE MEMBER_TBL SET MEMBER_PWD = '" + member.getMemberPw() + "'" + ", EMAIL = '"
//				+ member.getEmail() + "', PHONE = '" + member.getPhone() + "', ADDRESS = '" + member.getAddress()
//				+ "', HOBBY = '" + member.getHobby() + "' WHERE MEMBER_ID = '" + member.getMemberId() + "'";
		String query = "UPDATE MEMBER_TBL SET MEMBER_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ?, WHERE MEMBER_ID = ?";
//		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
//			int result = stmt.executeUpdate(query);
			pstmt.setString(1, member.getMemberPw()); // 1번째 홀덤값
			pstmt.setString(2, member.getEmail());	// 2번째 홀덤값
			pstmt.setString(3, member.getPhone());	// 3번째 홀덤값
			pstmt.setString(4, member.getAddress());	// 4번째 홀덤값
			pstmt.setString(5, member.getHobby());	// 5번째 홀덤값
			pstmt.setString(6, member.getMemberId());	// 6번째 홀덤값
			// 실행하기 전에 위치홀더 값 셋팅
			int result = pstmt.executeUpdate(); // query변수가 필요없음(실행할땐 전달값 필요없음)
			if (result > 0) {
				// 성공 후 커밋
			} else {
				// 실패면 롤백
			}
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMember(String memberId) {
//		String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
		String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = ?";
//		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
//			int result = stmt.executeUpdate(query); // DML의 경우 INT로 받음
			int result = pstmt.executeUpdate(); // DML의 경우 INT로 받음
			if (result > 0) {
				// 성공 후 커밋
			} else {
				// 실패면 롤백
			}
//			stmt.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Member selectOneById(String memberId) {
//		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?";
		Member member = null;
//		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query); // 쿼리문 미리 실행
//			ResultSet rset = stmt.executeQuery(query);
			pstmt.setString(1, memberId);	// 쿼리문 실행 전 위치 홀더 값 셋팅
			ResultSet rset = pstmt.executeQuery();		
			if (rset.next()) {
				member = this.rsetToMember(rset);
			}
//			rset.close();
//			stmt.close();
			pstmt.close(); // stmt대신 탄생
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

	public void insertMember(Member member) {
//		String query = "INSERT INTO MEMBER_TBL" + " VALUES('" + member.getMemberId() + "', '" + member.getMemberPw()
//				+ "', '" + member.getMemberName() + "', '" + member.getGender() + "', " + member.getAge() + ", '"
//				+ member.getEmail() + "', '" + member.getPhone() + "', '" + member.getAddress() + "', '"
//				+ member.getHobby() + "', sysdate)";
		String query = "INSERT INTO MEMBER_TBL" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
//		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender()+""); // setChar란 매소드는 없기 때문에 강제 형변환 필요
//			pstmt.setString(4, String.valueOf(member.getGender())); // 둘 중 한가지 방법
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());	// 실행하기전 위치홀더(?)값 세팅
//				ResultSet rset = stmt.executeQuery(query);
//			int result = stmt.executeUpdate(query); // DML의 경우 호출하는 메소드
			int result = pstmt.executeUpdate(); // 실행할 때 전달값이 필요없음()안에 query불필요
			if (result > 0) { // autocommit이 되기 때문에 안해도 됨
				// insert 성공
			} else {
				// insert 실패
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Member selectLoginInfo(Member mOne) {

//		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '" + mOne.getMemberId() + "' AND MEMBER_PWD = '"
//		+ mOne.getMemberPw() + "'";
		
		// ?는 위치홀덤
		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?" + "AND MEMBER_PWD = ?"; ///////////////////////////// 바뀌는
																								///////////////////////////// 부분
																								///////////////////////////// 1번째
		Member member = null;
		Connection conn = null;
//		Statement stmt = null; // sql인젝션 공격에 노출됨
		PreparedStatement pstmt = null; /////////////////////////////// 바뀌는 부분 2번째
		ResultSet rset = null;
		
		// 싱글톤 미적용 시
//		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		try {
			conn = jdbcTemplate.getConnection();
			//////////////////////// 바뀌는 부분2
			pstmt = conn.prepareStatement(query); // 쿼리문을 미리 컴파일
			//////////////////////// 바뀌는 부분3
			pstmt.setString(1, mOne.getMemberId()); // ?(위치홀더)에 값을을 넣는 코드
			pstmt.setString(2, mOne.getMemberPw()); // 시작은 1로 하고 마지막 수는 물음표의 갯수와 같다.(물음표 = 위치홀더)
			//////////////////////// 바뀌는 부분4
			rset = pstmt.executeQuery(); // 쿼리문을 미리 컴파일하고 위치홀더 값을 셋팅하고 쿼리문 실행 및 결과 받기

//			stmt = conn.createStatement();
//			rset = stmt.executeQuery(query);
			if (rset.next()) {
				member = this.rsetToMember(rset);
			}
			rset.close();
//			stmt.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return member;
	}

	// 클래스 안에 코드 못 씀
	// 이 메소드 안에 코드를 적어야 함.
	public List<Member> selectAll() {

		String query = "SELECT * FROM MEMBER_TBL"; // 전체는 preapreStatement를 쓸 필요가 없음
		List<Member> mList = null;
		// 메소드 안에 코드를 씀
//		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		try {
			Connection conn = jdbcTemplate.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query); // 초록색 재생버튼 누름
			// rset 전부다 담겨있기 때문에 한 행씩 꺼내서 출력해줘야 함
			mList = new ArrayList<Member>();
			while (rset.next()) {
				Member member = new Member();
				member = this.rsetToMember(rset);
				mList.add(member); // 누락주의! 하나의 행 데이터를 List에 반복적으로 저장
				// 후처리 : SELECT한 결과값 자바영역인 List에다가 옮기는 작업
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mList;
	}

	

	private Member rsetToMember(ResultSet rset) throws SQLException {
		Member member = new Member();
		member = new Member();
		member.setMemberId(rset.getString("MEMBER_ID"));
		member.setMemberPw(rset.getString("MEMBER_PWD"));
		member.setMemberName(rset.getString("MEMBER_NAME"));
		member.setGender(rset.getString("GENDER").charAt(0)); // GENDER는 한글자라서 문자처리
		member.setAge(rset.getInt("AGE")); // AGE는 getINT로 사용
		member.setEmail(rset.getString("EMAIL"));
		member.setPhone(rset.getString("PHONE"));
		member.setAddress(rset.getString("ADDRESS"));
		member.setHobby(rset.getString("HOBBY"));
		member.setEnrollDate(rset.getDate("ENROLL_DATE")); // getDATE 사용
		return member;
	}

}
