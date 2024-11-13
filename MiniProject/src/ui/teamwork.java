package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;  // 추가: 테이블 모델

import dao.JHJDAO;
import dao.PSJDAO;
import dto.JHJDTO;
import dto.PSJDTO;

public class teamwork extends JFrame {

    private JPanel mainPanel;
    private JPanel boardViewPanel;
    private JPanel boardWritePanel;
    private JPanel boardRowNumPanel;
    private JButton deleteButton;

    private Map<String, String> boardContents;
    private DefaultListModel<String> listModel;
    private PSJDAO dao;
    private JHJDAO dao2;

    public teamwork() {
        setTitle("게시판 프로그램");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardContents = new HashMap<>();
        setLayout(new BorderLayout());

        dao = new PSJDAO();
        dao2 = new JHJDAO();

        // 메인화면구성하기----------------------------------------------------------------------------------------
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JButton viewBoardButton = new JButton("게시판 조회");
        JButton writeBoardButton = new JButton("게시판 글쓰기");
        JButton viewMembersButton = new JButton("회원 조회");  // 회원조회 버튼
        JButton rowNumBoardButton = new JButton("직원 월급 탑 10");

        mainPanel.add(viewBoardButton);
        mainPanel.add(writeBoardButton);
        mainPanel.add(viewMembersButton);  // 회원조회 버튼을 메인 패널에 추가

        add(mainPanel, BorderLayout.NORTH);

        // 게시판 조회 화면 (JTable을 사용하여 테이블 형식으로 표시)---------------------------------------------
        boardViewPanel = new JPanel();
        boardViewPanel.setLayout(new BorderLayout());
        
        // 테이블 모델 생성 (열 이름 설정)
        String[] columnNames = {"제목", "내용"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        
        // JTable 생성
        JTable boardTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(boardTable);  // JScrollPane에 JTable 추가
        
        boardViewPanel.add(new JLabel("게시판 조회 화면"), BorderLayout.NORTH);
        boardViewPanel.add(scrollPane, BorderLayout.CENTER);  // 테이블을 JScrollPane에 추가하여 화면에 표시

        // 급여 탑 10 패널 생성---------------------------------------------------------------------------------------------------
        boardRowNumPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();  // listModel 초기화
        JList<String> rowNumList = new JList<>(listModel);  // 초기화된 listModel로 JList 생성
        boardRowNumPanel.add(new JLabel("ALLEN보다 급여가 높은 사원 리스트"), BorderLayout.NORTH);
        boardRowNumPanel.add(new JScrollPane(rowNumList), BorderLayout.CENTER);

        // 버튼---------------------------------------------------------------------------------------------------
        viewBoardButton.addActionListener(e -> showBoardView(tableModel));  // 테이블 모델을 업데이트하는 메서드로 수정
        writeBoardButton.addActionListener(e -> showBoardWriteDialog());  // 팝업창 띄우는 버튼 이벤트
        viewMembersButton.addActionListener(e -> showMemberViewDialog());  // 회원조회 버튼 클릭 시

        setVisible(true);
    }

    // 게시판 조회 메서드 (테이블에 데이터를 추가하는 부분)-----------------------------------------------
    private void showBoardView(DefaultTableModel tableModel) {
        // 기존 테이블 내용 초기화
        tableModel.setRowCount(0);  // 기존 데이터를 지우고, 새로운 데이터를 추가
        
        // 테이블에 데이터를 추가
        for (Map.Entry<String, String> entry : boardContents.entrySet()) {
            String title = entry.getKey();
            String content = entry.getValue();
            tableModel.addRow(new Object[]{title, content});  // 테이블에 새로운 행을 추가
        }

        getContentPane().removeAll();
        add(mainPanel, BorderLayout.NORTH);
        add(boardViewPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // 게시판 글쓰기 팝업창 메서드-----------------------------------------------------------------------------------
    private void showBoardWriteDialog() {
        // JDialog로 글쓰기 팝업창 띄우기
        JDialog writeDialog = new JDialog(this, "게시글 작성", true);
        writeDialog.setSize(400, 300);
        writeDialog.setLocationRelativeTo(this);

        JPanel writePanel = new JPanel();
        writePanel.setLayout(new BorderLayout());

        // 제목 입력란
        JPanel titlePanel = new JPanel();
        JTextField titleField = new JTextField(30);
        titlePanel.add(new JLabel("제목: "));
        titlePanel.add(titleField);

        // 내용 입력란
        JPanel contentPanel = new JPanel();
        JTextArea contentArea = new JTextArea(10, 30);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentPanel.add(new JLabel("내용: "));
        contentPanel.add(contentScrollPane);

        // 글쓰기 버튼
        JButton saveButton = new JButton("글 저장");
        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            String content = contentArea.getText();
            if (!title.isEmpty() && !content.isEmpty()) {
                boardContents.put(title, content); // 제목과 내용을 저장
                JOptionPane.showMessageDialog(writeDialog, "게시글이 저장되었습니다.");
                writeDialog.dispose();  // 팝업창 닫기
            } else {
                JOptionPane.showMessageDialog(writeDialog, "제목과 내용을 입력해주세요.");
            }
        });

        // 팝업 창에 컴포넌트 추가
        writePanel.add(titlePanel, BorderLayout.NORTH);
        writePanel.add(contentPanel, BorderLayout.CENTER);
        writePanel.add(saveButton, BorderLayout.SOUTH);

        writeDialog.add(writePanel);
        writeDialog.setVisible(true);
    }

    // 회원조회 팝업창 메서드 -----------------------------------------------------------------------------------
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
        ArrayList<JHJDTO> memberListFromDB = dao.getAllMembers();  // DB에서 모든 회원 조회

        for (JHJDTO member : memberListFromDB) {
            listModel.addElement(member.getId());  // 각 회원의 ID를 리스트에 추가
        }

        // 회원 탈퇴 버튼
        JButton deleteMemberButton = new JButton("회원 탈퇴");
        deleteMemberButton.addActionListener(e -> {
            String selectedUserId = memberList.getSelectedValue();  // 선택된 회원 ID
            if (selectedUserId != null) {
                int response = JOptionPane.showConfirmDialog(
                    memberDialog,
                    "정말 " + selectedUserId + "님을 탈퇴하시겠습니까?",
                    "회원 탈퇴 확인",
                    JOptionPane.YES_NO_OPTION
                );
                if (response == JOptionPane.YES_OPTION) {
                    JHJDTO memberToDelete = new JHJDTO(selectedUserId);
                    System.out.println(selectedUserId);
                    boolean result = dao2.delete(memberToDelete);  // DB에서 회원 탈퇴 처리
                    if (result) {
                        JOptionPane.showMessageDialog(memberDialog, "회원 탈퇴가 완료되었습니다.");
                        listModel.removeElement(selectedUserId);  // 리스트에서 해당 회원 삭제
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
