package nhom7_qldanhba;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends javax.swing.JFrame {

    private javax.swing.JTextField txtUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRegister;

    public LoginForm() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng Nhập");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Tên đăng nhập:"));
        txtUsername = new javax.swing.JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Mật khẩu:"));
        txtPassword = new javax.swing.JPasswordField();
        panel.add(txtPassword);

        btnLogin = new JButton("Đăng Nhập");
        btnRegister = new JButton("Đăng Ký");

        btnLogin.addActionListener(e -> loginUser());
        btnRegister.addActionListener(e -> openRegisterForm());

        panel.add(btnLogin);
        panel.add(btnRegister);

        add(panel);
    }

    private void loginUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.connect();
            String sql = "SELECT * FROM login WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                TrangChu trangChu = new TrangChu();
                trangChu.setLocationRelativeTo(null);
                trangChu.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    DatabaseConnection.close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void openRegisterForm() {
        RegisterForm registerForm = new RegisterForm(this);
        registerForm.setVisible(true);
        this.setVisible(false);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
