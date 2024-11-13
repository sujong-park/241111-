package dto;





public class PSJDTO {
	//	member
	private String id;
	private String password;

	
	//	dept
	private int deptno;
	private String dname;
	private String loc;

	
	//	emp
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private String hiredate;
	private int sal;
	private int comm;

	//	boards
	private int boardNo;
	private String title;
	private String content;
	private String writer;
	private String DATE;

	public PSJDTO(String id, String password) {
		this.id = id;
		this.password = password;
	}

	

	public PSJDTO(String id, int empno, String ename, String job, int mgr, String hiredate, int sal, int comm, int deptno) {
	    this.id = id;
	    this.empno = empno;
	    this.ename = ename;
	    this.job = job;
	    this.mgr = mgr;
	    this.hiredate = hiredate;
	    this.sal = sal;
	    this.comm = comm;
	    this.deptno = deptno;
	}

 

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}



	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}



	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}



	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}



	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
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



	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}



	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}



	

}