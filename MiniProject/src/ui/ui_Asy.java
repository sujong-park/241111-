package ui;

import java.awt.BorderLayout;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.Asy_Board_DAO;

import dto.Asy_Board_DTO;

public class ui_Asy extends JFrame {
	private JTextField titleField; // 메모 제목 적는 텍스트필드
	private JTextArea contentArea; // 메모 내용 적는 텍스트 영역
	private JButton addButton, viewButton; // 메모 추가, 목록 보는 버튼
	private JList<String> memoList; // 메모 목록보는 JList
	private DefaultListModel<String> listModel; // 메모 목록 데이터 모델
	private Asy_Board_DAO dao; // 메모 DAO 객체를 초기화해줍니다.

	public ui_Asy() {
		dao = new Asy_Board_DAO(); // DAO 객체 초기화
		setTitle("게시판");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel(new BorderLayout()); // JPanel 클래스로부터 생성된 inputPanel 객체

		// 제목 필드와 라벨
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.add(new JLabel("제목"), BorderLayout.NORTH);
		titleField = new JTextField(); // 메모 제목 입력 필드
		titlePanel.add(titleField, BorderLayout.CENTER);

		// 내용 필드와 라벨
		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.add(new JLabel("내용"), BorderLayout.NORTH);
		contentArea = new JTextArea(5, 20);
		contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

		// 메모 목록 필드와 라벨
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.add(new JLabel("메모 목록"), BorderLayout.NORTH);
		listModel = new DefaultListModel<>();
		memoList = new JList<>(listModel);
		listPanel.add(new JScrollPane(memoList), BorderLayout.CENTER); // 메모 많이 쌓이면 스크롤 가능

		// Input 패널에 제목, 내용 패널 추가
		inputPanel.add(titlePanel, BorderLayout.NORTH);
		inputPanel.add(contentPanel, BorderLayout.CENTER);

		addButton = new JButton("게시판추가");
		addButton.addActionListener(e -> {
			String title = titleField.getText();
			String content = contentArea.getText();
			Asy_Board_DTO memo = new Asy_Board_DTO(0, title, content);
			if (dao.addBoard(memo)) {
				JOptionPane.showMessageDialog(null, "게시판에 추가되었습니다.");
				titleField.setText("");
				contentArea.setText("");
				viewMemoList(); // 메모 추가 후 목록 갱신
			} else {
				JOptionPane.showMessageDialog(null, "게시판 추가 실패.");
			}
		});

		viewButton = new JButton("게시판조회");
		viewButton.addActionListener(e -> {
			int selectedIndex = memoList.getSelectedIndex();
			if (selectedIndex != -1) {
				String selectedValue = memoList.getSelectedValue();
				int id = Integer.parseInt(selectedValue.split(":")[0]);
				Asy_Board_DTO memo = dao.getMemoById(id);
				if (memo != null) {
					titleField.setText(memo.getTitle());
					contentArea.setText(memo.getContent());
				}
			} else {
				JOptionPane.showMessageDialog(null, "조회할 메모를 선택하세요.");
			}
		});

		viewMemoList(); // 초기 화면에 메모 목록 표시

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(viewButton);

		// 프레임에 각 패널 추가
		add(inputPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void viewMemoList() {
		listModel.clear(); // 기존 목록을 지움
		ArrayList<Asy_Board_DTO> memos = dao.viewMemo(); // DAO에서 메모 목록을 가져옴
		for (Asy_Board_DTO memo : memos) {
			listModel.addElement(memo.getId() + ": " + memo.getTitle()); // 목록에 추가
		}
	}

	public static void main(String[] args) {
		new ui_Asy();
	}
}
