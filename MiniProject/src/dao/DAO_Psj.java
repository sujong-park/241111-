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

    public ArrayList<DTO_Psj> getEmpList() {
        ArrayList<DTO_Psj> list = new ArrayList<>();

        String sql = "SELECT * FROM emp WHERE sal > (SELECT sal from emp where ename = 'ALLEN') ORDER BY sal";
        try (Connection conn = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int empno = rs.getInt("empno");
                String ename = rs.getString("ename");
                String job = rs.getString("job");
                int mgr = rs.getInt("mgr");
                String hiredate = rs.getString("hiredate");
                int sal = rs.getInt("sal");
                int comm = rs.getInt("comm");
                int deptno = rs.getInt("deptno");
                
                list.add(new DTO_Psj(empno, ename, job, mgr, hiredate, sal, comm, deptno));

                
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
