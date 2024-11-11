package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.DTO_Psj;

public class DAO_Psj {

    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String userid = "scott";
    String passwd = "tiger";

    public DAO_Psj() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DTO_Psj> getDeptList() {
        ArrayList<DTO_Psj> list = new ArrayList<>();

        String sql = "SELECT deptno, dname, loc FROM dept";
        try (Connection conn = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int deptno = rs.getInt("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                list.add(new DTO_Psj(deptno, dname, loc));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
