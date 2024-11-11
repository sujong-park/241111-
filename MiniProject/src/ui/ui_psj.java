package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.DAO_Psj;
import dto.DTO_Psj;

public class ui_psj extends JFrame {

    private JList<String> listbox;
    private DefaultListModel<String> listModel;

    DAO_Psj dao = new DAO_Psj();

    public ui_psj() {
        setTitle("회원 정보");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JButton btnPsj = new JButton("박수종");
        inputPanel.add(btnPsj);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        listbox = new JList<>(listModel);

        listPanel.add(new JScrollPane(listbox), BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);

        btnPsj.addActionListener(e -> btnFunc());

        setVisible(true);
    }

    public void btnFunc() {
        listModel.clear();
        ArrayList<DTO_Psj> deptList = dao.getDeptList();
        for (DTO_Psj dto : deptList) {
            listModel.addElement(dto.getDeptno() + " - " + dto.getDname() + " - " + dto.getLoc());
        }
    }

    public static void main(String[] args) {
        new ui_psj();
    }
}
