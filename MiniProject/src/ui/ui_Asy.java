package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import java.util.List;  // List로 변경
import dao.Asy_DAO;    // Asy_DAO로 변경
import dto.Asy_DTO;    // Asy_DTO로 변경

public class ui_Asy extends JFrame {

    private JList<String> listbox;
    private DefaultListModel<String> listModel;
    Asy_DAO asyDao = new Asy_DAO();  // Asy_DAO 인스턴스 사용

    public ui_Asy() {
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

        // 버튼 클릭 시 btnFunc() 메서드 호출
        btnAsy.addActionListener(e -> btnFunc());

        setVisible(true);
    }

    // btnAsy 버튼 클릭 시 호출되는 메소드
    public void btnFunc() {
        listModel.clear();
        List<Asy_DTO> deptList = asyDao.getDepartmentStats();  
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

    public static void main(String[] args) {
        new ui_Asy();
    }
}
