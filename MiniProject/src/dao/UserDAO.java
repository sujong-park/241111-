package dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private Connection connection;

	public UserDAO() {
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
 
	public boolean validateUser(String id, String password) {
		String query = "SELECT * FROM users WHERE id = ? AND password = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			return rs.next(); // 결과가 있으면 true 반환
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
}