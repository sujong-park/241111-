package dao;

import java.sql.*;
import java.util.ArrayList;

import dto.Asy_Board_DTO;

public class Asy_Board_DAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "scott";
	String passwd = "tiger";

	public Asy_Board_DAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean addBoard(Asy_Board_DTO board) {
		String query = "INSERT INTO memo (title, content) VALUES (?, ?)";
		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Asy_Board_DTO> viewMemo() {
		ArrayList<Asy_Board_DTO> memoList = new ArrayList<>();
		String query = "SELECT * FROM memo";
		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String content = rs.getString("content");
				memoList.add(new Asy_Board_DTO(id, title, content));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memoList;
	}

	public Asy_Board_DTO getMemoById(int id) {
		String query = "SELECT * FROM memo WHERE id = ?";
		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					String title = rs.getString("title");
					String content = rs.getString("content");
					return new Asy_Board_DTO(id, title, content);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public boolean deleteMemo(int id) {
//		String query = "DELETE FROM memo WHERE id = ?";
//		try (Connection con = DriverManager.getConnection(url, userid, passwd);
//				PreparedStatement pstmt = con.prepareStatement(query)) {
//			pstmt.setInt(1, id);
//			return pstmt.executeUpdate() > 0;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
}

