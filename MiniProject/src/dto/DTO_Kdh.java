package dto;


public class DTO_Kdh {
    private int empno;    // 직원 번호
    private String ename; // 직원 이름
    private String dname; // 부서 이름

    public DTO_Kdh(int empno, String ename, String dname) {
        this.empno = empno;
        this.ename = ename;
        this.dname = dname;
    }

    public int getEmpno() {
        return empno;
    }

    public String getEname() {
        return ename;
    }

    public String getDname() {
        return dname;
    }

    @Override
    public String toString() {
        return empno + " - " + ename + " - " + dname;
    }
}
