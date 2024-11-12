package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.JHJDTO;



public class JHJDAO {
	private Connection connection;

	
	public JHJDAO() {
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	// 사용자 탈퇴 메서드
    public boolean delete(JHJDTO dto) { 
    	PreparedStatement pstmt  = null;
        PreparedStatement pstmt2 = null;

        String deleteBoardSQL = "DELETE FROM boards WHERE writer = ?";
        try {pstmt = connection.prepareStatement(deleteBoardSQL);
        	pstmt.setString(1, dto.getId());
            int boardRowsAffected = pstmt.executeUpdate();

            // 2. users 테이블에서 사용자 정보 삭제
            String deleteUserSQL = "DELETE FROM users WHERE id = ?";
            pstmt2 = connection.prepareStatement(deleteUserSQL);
            pstmt2.setString(1, dto.getId());
            int userRowsAffected = pstmt2.executeUpdate();

            // 탈퇴 성공 여부 판단
            if (boardRowsAffected > 0 && userRowsAffected > 0) {
                return true;  // 탈퇴 성공
            } else {
                return false; // 탈퇴 실패
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // 예외 발생 시 탈퇴 실패
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (pstmt2 != null) pstmt2.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}





