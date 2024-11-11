package dto;



public class Kdh2022_dto {
    private int empno;    // 직원 번호
    private String ename; // 직원 이름
    private String dname; // 부서 이름
    
	String job;
	int mgr;
	String hiredate;
	int sal;
	int comm;
	int deptno;

    public Kdh2022_dto(int empno, String ename, String dname) {
        this.empno = empno;
        this.ename = ename;
        this.dname = dname;
    }
    
    public Kdh2022_dto(int empno, String ename, String job, int mgr, String hiredate, int sal, int comm, int deptno) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.job = job;
		this.mgr = mgr;
		this.hiredate = hiredate;
		this.sal = sal;
		this.comm = comm;
		this.deptno = deptno;
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
    
    public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getMgr() {
		return mgr;
	}

	public void setMgr(int mgr) {
		this.mgr = mgr;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public int getSal() {
		return sal;
	}

	public void setSal(int sal) {
		this.sal = sal;
	}

	public int getComm() {
		return comm;
	}

	public void setComm(int comm) {
		this.comm = comm;
	}
	
	public int getDeptno() {
		return deptno;
	}
	
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	
	
	
}