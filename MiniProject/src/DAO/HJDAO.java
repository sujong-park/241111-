package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.HJDTO;

public class HJDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";

	String userid = "scott";
	String passwd = "tiger";

	Connection connection = null;;
	PreparedStatement pstmt = null;;

	public HJDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

//	public void getMeaning(String word) {
//		String query = "SELECT emp FROM words WHERE word = ?";
//		try {
//			connection = DriverManager.getConnection(url, userid, passwd);
//			pstmt = connection.prepareStatement(query);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	public ArrayList<HJDTO> RightOuterJoin() {
		String query = "SELECT E1.EMPNO, E1.ENAME, E1.MGR, E2.EMPNO AS MGR_EMPNO, E2.ENAME AS MGR_ENAME "
                + "FROM EMP E1 "
                + "right OUTER JOIN EMP E2 ON (E1.MGR = E2.EMPNO) "
                + "ORDER BY E1.EMPNO";
		ArrayList<HJDTO> list = new ArrayList<>();
		
		try {
			connection = DriverManager.getConnection(url, userid, passwd);
			pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				HJDTO dto = new HJDTO(rs.getInt("EMPNO"), // EMPNO
						rs.getString("ENAME"), // ENAME
						rs.getString("MGR_ENAME"), // JOB or MGR_ENAME in this example
						rs.getInt("MGR"), // MGR
						null, // HIREDATE, 임의로 null 설정
						0, // SAL, 임의로 0 설정
						0, // COMM, 임의로 0 설정
						rs.getInt("MGR_EMPNO") // DEPTNO or MGR_EMPNO in this example
				);
				list.add(dto);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}

}
