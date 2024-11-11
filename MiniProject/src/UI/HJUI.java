package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import DAO.HJDAO;
import DTO.HJDTO;

public class HJUI extends JFrame {

	private JList<String> listbox;
	private DefaultListModel<String> listModel;

	HJDAO dao = new HJDAO();

	public HJUI() {
		setTitle("회원 정보");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());

		JButton btnHJ = new JButton("전효정");
		inputPanel.add(btnHJ);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());

		listModel = new DefaultListModel<>();
		listbox = new JList<>(listModel);

		listPanel.add(new JScrollPane(listbox), BorderLayout.CENTER);

		add(inputPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);

		btnHJ.addActionListener(e -> Join());

		setVisible(true);
	}

	public void Join() {
		listModel.clear();
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
		new HJUI();
	}
}