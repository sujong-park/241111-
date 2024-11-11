package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Asy_DAO {
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String user = "scott";
    private String password = "tiger";

 
    public List<dto.Asy_DTO> getDepartmentStats() {
        List<dto.Asy_DTO> deptList = new ArrayList<>(); 
        String query = """
            SELECT deptno, COUNT(*) AS empCount, MAX(sal) AS maxSal,
                   MIN(sal) AS minSal, SUM(sal) AS totalSal, ROUND(AVG(sal)) AS avgSal
            FROM emp
            GROUP BY deptno
            ORDER BY totalSal DESC
            """;

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
           
            	dto.Asy_DTO dept = new dto.Asy_DTO();
                dept.setDeptno(rs.getInt("deptno"));
                dept.setEmpCount(rs.getInt("empCount"));
                dept.setMaxSal(rs.getInt("maxSal"));
                dept.setMinSal(rs.getInt("minSal"));
                dept.setTotalSal(rs.getInt("totalSal"));
                dept.setAvgSal(rs.getInt("avgSal"));

       
                deptList.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deptList;
    }
}
