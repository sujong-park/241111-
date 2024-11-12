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

public class SignupFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField idField;
	private JPasswordField passwordField, confirmPasswordField;
	private JButton signupButton, cancelButton;

	public SignupFrame() {
		setTitle("회원가입");
		setSize(500, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		idField = new JTextField();
		passwordField = new JPasswordField();
		confirmPasswordField = new JPasswordField();
		signupButton = new JButton("가입하기");
		cancelButton = new JButton("취소");

// 각 컴포넌트의 크기를 고정
		idField.setPreferredSize(new Dimension(150, 30));
		passwordField.setPreferredSize(new Dimension(150, 30));
		confirmPasswordField.setPreferredSize(new Dimension(150, 30));
		signupButton.setPreferredSize(new Dimension(100, 30));
		cancelButton.setPreferredSize(new Dimension(100, 30));
// ----------------------------------------------------------------
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		idPanel.add(new JLabel(" 아이디:"));
		idPanel.add(idField);

		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		passwordPanel.add(new JLabel(" 패스워드:"));
		passwordPanel.add(passwordField);

		JPanel confirmPasswordPanel = new JPanel();
		confirmPasswordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		confirmPasswordPanel.add(new JLabel("패스워드 확인:"));
		confirmPasswordPanel.add(confirmPasswordField);
// ----------------------------------------------------------------
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 버튼을 중앙에 배치
		buttonPanel.add(signupButton);
		buttonPanel.add(cancelButton);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 세로로 배치
		panel.add(idPanel); // 아이디 패널 추가
		panel.add(passwordPanel); // 패스워드 패널 추가
		panel.add(confirmPasswordPanel);
		panel.add(buttonPanel); // 버튼 패널 추가

		add(panel); // 프레임에 패널 추가

		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				signup();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // 회원가입 창 닫기
			}
		});

	}

	private void signup() {
		String id = idField.getText();
		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(confirmPasswordField.getPassword());

		if (!password.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			return;
		}

// 데이터베이스에 사용자 추가
		UserDAO userDAO = new UserDAO();
		if (userDAO.addUser(id, password)) {
			JOptionPane.showMessageDialog(this, "회원가입 성공!");
			dispose(); // 회원가입 후 창 닫기
		} else {
			JOptionPane.showMessageDialog(this, "회원가입 실패. 아이디를 확인하세요.", "오류", JOptionPane.ERROR_MESSAGE);
		}
	}
}