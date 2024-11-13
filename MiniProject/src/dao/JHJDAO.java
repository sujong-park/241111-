package dao;

import java.sql.*;
import java.util.ArrayList;
import dto.JHJDTO;

public class JHJDAO {
	private Connection connection;

	// 생성자: DB 연결
	public JHJDAO() {
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			connection.setAutoCommit(false); // 트랜잭션을 사용할 수 있도록 자동 커밋 비활성화
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 모든 회원 정보를 가져오는 메서드
	public ArrayList<JHJDTO> getAllMembers() {
		ArrayList<JHJDTO> memberList = new ArrayList<>();
		String query = "SELECT id FROM users"; // users 테이블에서 ID 조회

		try (PreparedStatement pstmt = connection.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				String id = rs.getString("id");
				memberList.add(new JHJDTO(id)); // ID 값을 JHJDTO로 래핑하여 리스트에 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberList;
	}

	// 사용자 탈퇴 메서드
	public boolean delete(JHJDTO dto) {
		PreparedStatement pstmt = null;
		String deleteUserSQL = "DELETE FROM users WHERE id = ?";

		try {
			pstmt = connection.prepareStatement(deleteUserSQL);
			pstmt.setString(1, dto.getId());
			int userRowsAffected = pstmt.executeUpdate();
			System.out.println("삭제된 사용자 수: " + userRowsAffected); // 디버깅을 위한 로그

			if (userRowsAffected > 0) {
				connection.commit(); // 성공적으로 삭제된 경우 트랜잭션 커밋
				return true; // 탈퇴 성공
			} else {
				connection.rollback(); // 삭제되지 않은 경우 롤백
				return false; // 탈퇴 실패
			}

		} catch (SQLException e) {
			try {
				connection.rollback(); // 예외 발생 시 롤백
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null) {
					connection.setAutoCommit(true);
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
