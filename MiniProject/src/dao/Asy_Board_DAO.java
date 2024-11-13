package dao;

import java.sql.*;
import java.util.ArrayList;
import dto.Asy_Board_DTO;

public class Asy_Board_DAO {
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String userid = "scott";
    private String passwd = "tiger";

    public Asy_Board_DAO() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean addMemo(Asy_Board_DTO board) {
        String query = "INSERT INTO boards (title, content, writer) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setString(3, "writer_id");  // 작성자 ID를 실제 값으로 대체
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Asy_Board_DTO> viewboards() {
        ArrayList<Asy_Board_DTO> boardsList = new ArrayList<>();
        String query = "SELECT * FROM boards";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("boardNo");
                String title = rs.getString("title");
                String content = rs.getString("content");
                boardsList.add(new Asy_Board_DTO(id, title, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardsList;
    }

    public boolean deleteboards(int id) {
        String query = "DELETE FROM boards WHERE boardNo = ?";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
