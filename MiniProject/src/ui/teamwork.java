package ui;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.JHJDAO;
import dto.JHJDTO;

public class teamwork extends JFrame {

	private JPanel mainPanel;
	private JPanel boardViewPanel;
	private JPanel boardWritePanel;
	private JButton deleteButton;

	private Map<String, String> boardContents;

	public teamwork() {
		setTitle("게시판 프로그램");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boardContents = new HashMap<>();

		setLayout(new BorderLayout());

//메인화면구성하기----------------------------------------------------------------------------------------
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());

		JButton viewBoardButton = new JButton("게시판 조회");
		JButton writeBoardButton = new JButton("게시판 글쓰기");
		deleteButton = new JButton("회원 탈퇴");

		mainPanel.add(viewBoardButton);
		mainPanel.add(writeBoardButton);
		mainPanel.add(deleteButton);

		add(mainPanel, BorderLayout.NORTH);

//게시판 조회 화면-----------------------------------------------------------------------------
		boardViewPanel = new JPanel();
		boardViewPanel.setLayout(new BorderLayout());
		JTextArea boardTextArea = new JTextArea();
		boardTextArea.setEditable(false);
		boardViewPanel.add(new JLabel("게시판 조회 화면"), BorderLayout.NORTH);
		boardViewPanel.add(boardTextArea, BorderLayout.CENTER);

//게시판 글쓰기-----------------------------------------------------------------------------
		boardWritePanel = new JPanel();
		boardWritePanel.setLayout(new BorderLayout());

//제목 입력----------------------------------------------------------------------------------------
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.add(new JLabel("제목"), BorderLayout.NORTH);
		JTextField titleField = new JTextField(30);
		titleField.setPreferredSize(new Dimension(300, 30));
		titlePanel.add(titleField);

//제목을 상단에 추가-----------------------------------------------------------------------------
		boardWritePanel.add(titlePanel, BorderLayout.NORTH);

//내용 입력----------------------------------------------------------------------------------------
		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.add(new JLabel("내용"), BorderLayout.NORTH);
		JTextArea contentArea = new JTextArea(10, 30); // 더 큰 크기로 설정
		contentArea.setPreferredSize(new Dimension(300, 200));
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);
		contentPanel.add(contentArea, BorderLayout.CENTER);

//내용 스크롤----------------------------------------------------------------------------------------
		JScrollPane contentScrollPane = new JScrollPane(contentArea);
		contentPanel.add(contentScrollPane, BorderLayout.CENTER);

//내용 가운데에 추가----------------------------------------------------------------------------------------
		boardWritePanel.add(contentPanel, BorderLayout.CENTER);

//게시글 작성 버튼----------------------------------------------------------------------------------------
		JButton saveButton = new JButton("게시글 작성");
		boardWritePanel.add(saveButton, BorderLayout.SOUTH);

//버튼---------------------------------------------------------------------------------------------------
		viewBoardButton.addActionListener(e -> showBoardView(boardTextArea));
		writeBoardButton.addActionListener(e -> showBoardWrite());
		deleteButton.addActionListener(e -> actionPerformed());

//게시글 작성 버튼 동작 > (서버에 저장)-----------------------------------------------------------------------
		saveButton.addActionListener(e -> {
			String title = titleField.getText();
			String content = contentArea.getText();
			if (!title.isEmpty() && !content.isEmpty()) {
				boardContents.put(title, content);
				titleField.setText("");
				contentArea.setText("");
				System.out.println("게시글이 저장되었습니다.");
			} else {
				System.out.println("제목과 내용을 입력하세요.");
			}
		});

		setVisible(true);
	}

// 게시판 조회 메서드------------------------------------------------------------------------------------
	private void showBoardView(JTextArea boardTextArea) {
		boardTextArea.setText(""); // 기존 텍스트 초기화
		for (Map.Entry<String, String> entry : boardContents.entrySet()) {
			boardTextArea.append("제목: " + entry.getKey() + "\n내용: " + entry.getValue() + "\n\n");
		}
		getContentPane().removeAll();
		add(mainPanel, BorderLayout.NORTH);
		add(boardViewPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

// 게시판 글쓰기 메서드----------------------------------------------------------------------------------
	private void showBoardWrite() {
		getContentPane().removeAll();
		add(mainPanel, BorderLayout.NORTH);
		add(boardWritePanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

//탈퇴메서드---------------------------------------------------------------------------------
	public void actionPerformed() {
		String userId = JOptionPane.showInputDialog("회원 ID를 입력하세요.");
		if (userId != null && !userId.isEmpty()) {
			JHJDTO dto = new JHJDTO(userId);
			JHJDAO dao = new JHJDAO();
			boolean result = dao.delete(dto);
			if (!result) {
				JOptionPane.showMessageDialog(null, "회원 탈퇴가 완료되었습니다.");
// 탈퇴 후 다른 화면으로 전환하거나 종료 처리
				dispose(); // 현재 창 닫기
			} else {
				JOptionPane.showMessageDialog(null, "탈퇴 실패. 다시 시도해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "회원 ID를 입력해야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
		}
	}

//메인---------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		new teamwork();
	}
}