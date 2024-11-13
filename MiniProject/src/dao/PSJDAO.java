package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.JHJDTO;
import dto.PSJDTO;

public class PSJDAO {
	private Connection connection;

	public PSJDAO() {
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// emp 테이블에서 특정 조건에 맞는 직원 목록을 가져오는 메서드
	public ArrayList<JHJDTO> getAllMembers() {
	    ArrayList<JHJDTO> list = new ArrayList<>();
	    String sql =
	    		"SELECT id " +
	             "FROM users";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            String id = rs.getString("id");

	            list.add(new JHJDTO(id));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

   
	public boolean addUser(String id, String password) {
		String sql = "INSERT INTO users (id, password) VALUES (?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			int n = pstmt.executeUpdate();
			System.out.println(n + "개의 레코드가 저장");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteUserAndPosts(String id, String password) {
		String userDeleteQuery = "DELETE FROM users WHERE id = ? AND password = ?";
		String postsDeleteQuery = "DELETE FROM posts WHERE user_id = ?";

		try {
			// 트랜잭션 시작
			connection.setAutoCommit(false);

			try (PreparedStatement postsStmt = connection.prepareStatement(postsDeleteQuery);
					PreparedStatement userStmt = connection.prepareStatement(userDeleteQuery)) {

				// 게시글 삭제
				postsStmt.setString(1, id);
				postsStmt.executeUpdate();

				// 사용자 삭제
				userStmt.setString(1, id);
				userStmt.setString(2, password);
				int result = userStmt.executeUpdate();

				// 트랜잭션 커밋
				connection.commit();

				return result > 0; // 삭제 성공 여부 반환
			} catch (SQLException e) {
				connection.rollback(); // 오류 발생 시 롤백
				e.printStackTrace();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// PSJDAO.java - 예시


	    // 회원 삭제 메서드
	    public boolean delete(JHJDTO member) {
	        // 회원 삭제 로직을 작성
	        return true; // 예시로 성공했다고 가정
	    }
	}













	

