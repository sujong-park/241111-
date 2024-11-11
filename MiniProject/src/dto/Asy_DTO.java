package dto;

public class Asy_DTO {
	private int deptno; // 부서번호
	private int empCount; // 사원수
	private int maxSal; // 최고 급여
	private int minSal; // 최소 급여
	private int totalSal; // 급여 총합
	private int avgSal; // 급여 평균
	
    public Asy_DTO() {}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public int getEmpCount() {
		return empCount;
	}

	public void setEmpCount(int empCount) {
		this.empCount = empCount;
	}

	public int getMaxSal() {
		return maxSal;
	}

	public void setMaxSal(int maxSal) {
		this.maxSal = maxSal;
	}

	public int getMinSal() {
		return minSal;
	}

	public void setMinSal(int minSal) {
		this.minSal = minSal;
	}

	public int getTotalSal() {
		return totalSal;
	}

	public void setTotalSal(int totalSal) {
		this.totalSal = totalSal;
	}

	public int getAvgSal() {
		return avgSal;
	}

	public void setAvgSal(int avgSal) {
		this.avgSal = avgSal;
	}

}
