package dao;


import dto.Kdh2022_dto;

import java.sql.*;
import java.util.ArrayList;

public class Kdh2022_dao {

private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
private static final String USER = "scott";
private static final String PASSWORD = "tiger";

// DB 연결 메서드
private Connection getConnection() throws SQLException {
return DriverManager.getConnection(URL, USER, PASSWORD);
}


//////// 등가조인 쿼리 사용 ///////////////////////////////////////////////////////////////////

// EMP와 DEPT 테이블을 등가 조인하여 직원과 부서 정보 조회
public ArrayList<Kdh2022_dto> getDeptList() {
ArrayList<Kdh2022_dto> deptList = new ArrayList<>();
// EMP와 DEPT 테이블을 등가 조인하여 직원과 부서 정보를 가져오는 SQL 쿼리
String query = "SELECT e.empno, e.ename, d.dname " +
"FROM emp e " +
"JOIN dept d ON e.deptno = d.deptno"; // EMP와 DEPT 테이블을 DEPTNO로 등가 조인

try (Connection conn = getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
ResultSet rs = pstmt.executeQuery()) {

while (rs.next()) {
int empno = rs.getInt("empno");
String ename = rs.getString("ename");
String dname = rs.getString("dname");

Kdh2022_dto dto = new Kdh2022_dto(empno, ename, dname);
deptList.add(dto);
}
} catch (SQLException e) {
e.printStackTrace();
}

return deptList;
}




public ArrayList<Kdh2022_dto> getEmpList() {
ArrayList<Kdh2022_dto> empList = new ArrayList<>();

String sql = "SELECT * FROM emp WHERE sal > (SELECT sal from emp where ename = 'ALLEN') ORDER BY sal";

try (Connection conn = getConnection();
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

empList.add(new Kdh2022_dto(empno, ename, job, mgr, hiredate, sal, comm, deptno));
}
} catch (SQLException e) {
e.printStackTrace();
}

return empList;
}


public ArrayList<Kdh2022_dto> getEmpListkdh() {
ArrayList<Kdh2022_dto> empList = new ArrayList<>();

String sql = "SELECT * FROM emp WHERE hiredate > (SELECT hiredate from emp where ename = 'SCOTT')";

try (Connection conn = getConnection();
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

empList.add(new Kdh2022_dto(empno, ename, job, mgr, hiredate, sal, comm, deptno));
}
} catch (SQLException e) {
e.printStackTrace();
}

return empList;
}
}
