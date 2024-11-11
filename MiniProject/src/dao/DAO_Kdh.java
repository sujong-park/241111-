package dao;


import dto.DTO_Kdh;

import java.sql.*;
import java.util.ArrayList;

public class DAO_Kdh {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";

    // DB 연결 메서드
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

  
    //////// 등가조인 쿼리 사용 ///////////////////////////////////////////////////////////////////
    
    // EMP와 DEPT 테이블을 등가 조인하여 직원과 부서 정보 조회
    public ArrayList<DTO_Kdh> getDeptList() {
        ArrayList<DTO_Kdh> deptList = new ArrayList<>();
        // EMP와 DEPT 테이블을 등가 조인하여 직원과 부서 정보를 가져오는 SQL 쿼리
        String query = "SELECT e.empno, e.ename, d.dname " +
                       "FROM emp e " +
                       "JOIN dept d ON e.deptno = d.deptno";  // EMP와 DEPT 테이블을 DEPTNO로 등가 조인

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int empno = rs.getInt("empno");
                String ename = rs.getString("ename");
                String dname = rs.getString("dname");

                DTO_Kdh dto = new DTO_Kdh(empno, ename, dname);
                deptList.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deptList;
    }
}
