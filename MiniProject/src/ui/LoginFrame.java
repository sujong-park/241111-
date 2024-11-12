package ui;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.UserDAO;

public class LoginFrame extends JFrame {
private JTextField idField;
private JPasswordField passwordField;
private JButton loginButton, signupButton;

public LoginFrame() {
setTitle("로그인");
setSize(500, 300);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 센터 정렬, 가로/세로 간격 10px

idField = new JTextField();
passwordField = new JPasswordField();
loginButton = new JButton("로그인");
signupButton = new JButton("회원가입");

// 각 컴포넌트의 크기를 고정
idField.setPreferredSize(new Dimension(150, 30));
passwordField.setPreferredSize(new Dimension(150, 30));
loginButton.setPreferredSize(new Dimension(100, 30));
signupButton.setPreferredSize(new Dimension(100, 30));

// 아이디 레이블과 입력 필드를 가로로 배치할 패널
JPanel idPanel = new JPanel();
idPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
idPanel.add(new JLabel(" 아이디:"));
idPanel.add(idField);

// 패스워드 레이블과 입력 필드를 가로로 배치할 패널
JPanel passwordPanel = new JPanel();
passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
passwordPanel.add(new JLabel("패스워드:"));
passwordPanel.add(passwordField);

// 로그인 버튼과 회원가입 버튼을 가로로 배치할 패널
JPanel buttonPanel = new JPanel();
buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 버튼을 중앙에 배치
buttonPanel.add(loginButton);
buttonPanel.add(signupButton);

// 전체 패널 생성 및 세로로 배치
JPanel panel = new JPanel();
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 세로로 배치
panel.add(idPanel); // 아이디 패널 추가
panel.add(passwordPanel); // 패스워드 패널 추가
panel.add(buttonPanel); // 버튼 패널 추가

add(panel); // 프레임에 패널 추가

loginButton.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
login();
}
});

signupButton.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
new SignupFrame().setVisible(true);
}
});
}

private void login() {
String id = idField.getText();
String password = new String(passwordField.getPassword());

// 데이터베이스에서 로그인 검증
UserDAO userDAO = new UserDAO();
if (userDAO.validateUser(id, password)) {
JOptionPane.showMessageDialog(this, "로그인 성공!");
dispose(); // 로그인 창 닫기
new teamwork().setVisible(true); // 영어 단어장 창 열기
} else {
JOptionPane.showMessageDialog(this, "존재하지 않는 계정입니다.", "오류", JOptionPane.ERROR_MESSAGE);
}
}

public static void main(String[] args) {
new LoginFrame().setVisible(true);
}
}