package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.Asy_Board_DAO;
import dao.JHJDAO;
import dto.Asy_Board_DTO;
import dto.JHJDTO;

public class teamwork extends JFrame {

	private JPanel mainPanel;
	private JPanel boardViewPanel;
	private DefaultTableModel tableModel;
	private Asy_Board_DAO dao;
	private DefaultListModel<String> listModel; // 회원 목록을 표시할 리스트 모델
	private JHJDAO dao2; // JHJDAO 객체 생성

	public teamwork() {
		setTitle("게시판 프로그램");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		dao = new Asy_Board_DAO();
		dao2 = new JHJDAO(); // JHJDAO 초기화

		// 메인화면 구성
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());

		JButton viewBoardButton = new JButton("게시판 조회");
		JButton writeBoardButton = new JButton("게시판 글쓰기");
		JButton viewMembersButton = new JButton("회원 조회"); // 회원 조회 버튼 추가

		mainPanel.add(viewBoardButton);
		mainPanel.add(writeBoardButton);
		mainPanel.add(viewMembersButton); // 회원 조회 버튼 추가

		add(mainPanel, BorderLayout.NORTH);

		// 게시판 조회 화면 설정
		boardViewPanel = new JPanel();
		boardViewPanel.setLayout(new BorderLayout());

		String[] columnNames = { "ID", "제목", "내용" };
		tableModel = new DefaultTableModel(columnNames, 0);

		JTable boardTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(boardTable);

		boardViewPanel.add(new JLabel("게시판 조회 화면"), BorderLayout.NORTH);
		boardViewPanel.add(scrollPane, BorderLayout.CENTER);

		viewBoardButton.addActionListener(e -> showBoardView());
		writeBoardButton.addActionListener(e -> showBoardWriteDialog());
		viewMembersButton.addActionListener(e -> showMemberViewDialog()); // 회원 조회 버튼 이벤트 추가

		setVisible(true);
	}

	private void showBoardView() {
		tableModel.setRowCount(0); // 기존 데이터를 지우고 새로운 데이터 추가

		for (Asy_Board_DTO board : dao.viewboards()) {
			tableModel.addRow(new Object[] { board.getId(), board.getTitle(), board.getContent() });
		}

		getContentPane().removeAll();
		add(mainPanel, BorderLayout.NORTH);
		add(boardViewPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	private void showBoardWriteDialog() {
		JDialog writeDialog = new JDialog(this, "게시글 작성", true);
		writeDialog.setSize(400, 300);
		writeDialog.setLocationRelativeTo(this);

		JPanel writePanel = new JPanel();
		writePanel.setLayout(new BorderLayout());

		JPanel titlePanel = new JPanel();
		JTextField titleField = new JTextField(30);
		titlePanel.add(new JLabel("제목: "));
		titlePanel.add(titleField);

		JPanel contentPanel = new JPanel();
		JTextArea contentArea = new JTextArea(10, 30);
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);
		JScrollPane contentScrollPane = new JScrollPane(contentArea);
		contentPanel.add(new JLabel("내용: "));
		contentPanel.add(contentScrollPane);

		JButton saveButton = new JButton("글 저장");
		saveButton.addActionListener(e -> {
			String title = titleField.getText();
			String content = contentArea.getText();
			if (!title.isEmpty() && !content.isEmpty()) {
				Asy_Board_DTO board = new Asy_Board_DTO(0, title, content); // ID는 임시로 0 설정
				if (dao.addMemo(board)) {
					JOptionPane.showMessageDialog(writeDialog, "게시글이 저장되었습니다.");
					writeDialog.dispose();
				} else {
					JOptionPane.showMessageDialog(writeDialog, "게시글 저장에 실패했습니다.");
				}
			} else {
				JOptionPane.showMessageDialog(writeDialog, "제목과 내용을 입력해주세요.");
			}
		});

		writePanel.add(titlePanel, BorderLayout.NORTH);
		writePanel.add(contentPanel, BorderLayout.CENTER);
		writePanel.add(saveButton, BorderLayout.SOUTH);

		writeDialog.add(writePanel);
		writeDialog.setVisible(true);
	}

	// 회원조회 팝업창 메서드
	private void showMemberViewDialog() {
		// JDialog로 회원 조회 팝업창 띄우기
		JDialog memberDialog = new JDialog(this, "회원 조회", true);
		memberDialog.setSize(400, 300);
		memberDialog.setLocationRelativeTo(this);

		JPanel memberPanel = new JPanel();
		memberPanel.setLayout(new BorderLayout());

		// 회원 목록을 표시할 JList
		listModel = new DefaultListModel<>();
		JList<String> memberList = new JList<>(listModel);
		JScrollPane scrollPane = new JScrollPane(memberList);
		memberPanel.add(new JLabel("회원 목록"), BorderLayout.NORTH);
		memberPanel.add(scrollPane, BorderLayout.CENTER);

		// 회원 조회 버튼: DB에서 회원 목록을 불러와서 표시
		ArrayList<JHJDTO> memberListFromDB = dao2.getAllMembers(); // DB에서 모든 회원 조회

		for (JHJDTO member : memberListFromDB) {
			listModel.addElement(member.getId()); // 각 회원의 ID를 리스트에 추가
		}

		// 회원 탈퇴 버튼
		JButton deleteMemberButton = new JButton("회원 탈퇴");
		deleteMemberButton.addActionListener(e -> {
			String selectedUserId = memberList.getSelectedValue(); // 선택된 회원 ID
			if (selectedUserId != null) {
				int response = JOptionPane.showConfirmDialog(memberDialog, "정말 " + selectedUserId + "님을 탈퇴하시겠습니까?",
						"회원 탈퇴 확인", JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {
					JHJDTO memberToDelete = new JHJDTO(selectedUserId);
					boolean result = dao2.delete(memberToDelete); // DB에서 회원 탈퇴 처리
					if (result) {
						JOptionPane.showMessageDialog(memberDialog, "회원 탈퇴가 완료되었습니다.");
						listModel.removeElement(selectedUserId); // 리스트에서 해당 회원 삭제
					} else {
						JOptionPane.showMessageDialog(memberDialog, "회원 탈퇴에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(memberDialog, "탈퇴할 회원을 선택해주세요.");
			}
		});

		// 팝업 창에 컴포넌트 추가
		memberPanel.add(deleteMemberButton, BorderLayout.SOUTH);
		memberDialog.add(memberPanel);
		memberDialog.setVisible(true);
	}

	public static void main(String[] args) {
		new teamwork();
	}
}
