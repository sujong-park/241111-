package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.Asy_DAO;
import dao.DAO_Kdh;
import dto.Asy_DTO;
import dto.DTO_Kdh;
import dto.HJDTO;
import dao.HJDAO;

public class ui_Kdh extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<String> listbox;
    private DefaultListModel<String> listModel;
    DAO_Kdh dao = new DAO_Kdh();

    public ui_Kdh() {
        setTitle("직원 및 부서 정보");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JButton btnKdh1018 = new JButton("강두현");
        inputPanel.add(btnKdh1018);
        
        JButton btnKdh0222 = new JButton("김도현");
        inputPanel.add(btnKdh0222);
        
        JButton btnPsj = new JButton("박수종");
        inputPanel.add(btnPsj);
        
        JButton btnJhj = new JButton("전효정");
        inputPanel.add(btnJhj);
        
        JButton btnAsy = new JButton("안성엽");
        inputPanel.add(btnAsy);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        listbox = new JList<>(listModel);

        listPanel.add(new JScrollPane(listbox), BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);

        btnKdh1018.addActionListener(e -> btnFunc());
        btnKdh0222.addActionListener(e -> btnFunc());
        btnPsj.addActionListener(e -> btnFuncPsj());
        btnJhj.addActionListener(e -> Join());
        btnAsy.addActionListener(e -> btnFunc());

        setVisible(true);
    }

    // 버튼 클릭 시 호출되는 메소드
    public void btnFunc() {
        listModel.clear();
        ArrayList<DTO_Kdh> deptList = dao.getDeptList();  // DAO에서 직원 및 부서 정보 가져오기
        for (DTO_Kdh dto : deptList) {
            listModel.addElement(dto.getEmpno() + " - " + dto.getEname() + " - " + dto.getDname());
        }
    }
    
    public void btnFuncPsj() {
    	listModel.clear();
        ArrayList<DTO_Kdh> deptList = dao.getEmpList();
        
        listModel.addElement("ALLEN 보다 급여가 높은 사원 리스트");
        
        for (DTO_Kdh dto : deptList) {
            listModel.addElement(
            		dto.getEmpno() + " | " + dto.getEname() + " | " + dto.getJob() + " | " + 
            		dto.getMgr() + " | " + dto.getHiredate() + " | " + dto.getSal() + " | " + 
            		dto.getComm() + " | " + dto.getDeptno()
            		);
        }
    }
    
    public void btnFuncAsy() {
        listModel.clear();
        Asy_DAO dao = new Asy_DAO();
        List<Asy_DTO> deptList = dao.getDepartmentStats();  
        for (Asy_DTO dto : deptList) {
            String item = "부서번호: " + dto.getDeptno() +
                          ", 사원수: " + dto.getEmpCount() +
                          ", 최고급여: " + dto.getMaxSal() +
                          ", 최소급여: " + dto.getMinSal() +
                          ", 급여합계: " + dto.getTotalSal() +
                          ", 평균급여: " + dto.getAvgSal();
            listModel.addElement(item);
        }
    }

    public void Join() {
		listModel.clear();
		HJDAO dao = new HJDAO();
		ArrayList<HJDTO> empList = dao.RightOuterJoin();
		String displayText1 = "Emp No " + " Name " + " MGR No " + " Dept No " + " Job Title ";
		listModel.addElement(displayText1);
		String displayText2;
		for (HJDTO emp : empList) {

			if (emp.getEMPNO() != 0) {

				displayText2 = "  " + emp.getEMPNO() + "    " + emp.getENAME() + "    " + emp.getMGR() + "     "
						+ emp.getDEPTNO() + "         " + emp.getJOB();
 
			} else {
				displayText2 = "      " + emp.getEMPNO() + "          " + emp.getENAME() + "           " + emp.getMGR()
						+ "         " + emp.getDEPTNO() + "           " + emp.getJOB();
			}

			listModel.addElement(displayText2);
		}
	}

    public static void main(String[] args) {
        new ui_Kdh();
    }
}
