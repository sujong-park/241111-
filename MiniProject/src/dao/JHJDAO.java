package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import dto.JHJDTO;

public class JHJDAO {
    private Connection connection;

    // 생성자: DB 연결
    public JHJDAO() {
        try {
            // DB 연결
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
            connection.setAutoCommit(false); // 트랜잭션을 사용할 수 있도록 자동 커밋 비활성화
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 사용자 탈퇴 메서드
    public boolean delete(JHJDTO dto) { 
        PreparedStatement pstmt = null;

        // 사용자 정보 삭제 쿼리
        String deleteUserSQL = "DELETE FROM users WHERE id = ?";

        try {
            // 사용자 삭제
            pstmt = connection.prepareStatement(deleteUserSQL);
            pstmt.setString(1, dto.getId());
            int userRowsAffected = pstmt.executeUpdate();
            System.out.println("삭제된 사용자 수: " + userRowsAffected); // 디버깅을 위한 로그

            // 탈퇴 성공 여부 판단
            if (userRowsAffected > 0) {
                connection.commit();  // 성공적으로 삭제된 경우 트랜잭션 커밋
                return true;  // 탈퇴 성공
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
            return false;  // 예외 발생 시 탈퇴 실패
        } finally {
            try {
                // PreparedStatement 닫기
                if (pstmt != null) pstmt.close();
                // 커넥션 닫기
                if (connection != null) {
                    connection.setAutoCommit(true); // 트랜잭션 후 자동 커밋 활성화
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
