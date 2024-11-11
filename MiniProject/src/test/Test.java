package test;

import java.util.List;

import DAO.Asy_DAO;
import DTO.Asy_DTO;

public class Test {
    public static void main(String[] args) {
        Asy_DAO dao = new Asy_DAO();
        List<Asy_DTO> deptStats = dao.getDepartmentStats();

        System.out.println("부서번호\t사원수\t최고급여\t최소급여\t급여합계\t평균급여");
        System.out.println("===============================================");
        for (Asy_DTO dept : deptStats) {
            System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n",
                    dept.getDeptno(), dept.getEmpCount(), dept.getMaxSal(),
                    dept.getMinSal(), dept.getTotalSal(), dept.getAvgSal());
        }
    }
}
