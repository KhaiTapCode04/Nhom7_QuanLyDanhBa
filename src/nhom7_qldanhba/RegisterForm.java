package nhom7_qldanhba;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterForm extends javax.swing.JFrame {
    private javax.swing.JTextField txtUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtEmail;
    private LoginForm loginForm;

    public RegisterForm(LoginForm loginForm) {
        this.loginForm = loginForm;
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng Ký");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Tên đăng nhập:"));
        txtUsername = new javax.swing.JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Mật khẩu:"));
        txtPassword = new javax.swing.JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("Xác nhận mật khẩu:"));
        txtConfirmPassword = new javax.swing.JPasswordField();
        panel.add(txtConfirmPassword);

        panel.add(new JLabel("Số điện thoại:"));
        txtPhone = new javax.swing.JTextField();
        panel.add(txtPhone);

        panel.add(new JLabel("Email:"));
        txtEmail = new javax.swing.JTextField();
        panel.add(txtEmail);

        JButton btnRegister = new JButton("Đăng Ký");
        JButton btnCancel = new JButton("Hủy");

        btnRegister.addActionListener(e -> registerUser());
        btnCancel.addActionListener(e -> returnToLogin());

        panel.add(btnRegister);
        panel.add(btnCancel);

        add(panel);
    }

    private void registerUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        String phone = txtPhone.getText();
        String email = txtEmail.getText();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không khớp");
            return;
        }

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.connect();
            
            // Kiểm tra tên đăng nhập đã tồn tại
           String checkSql = "SELECT * FROM login WHERE username = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại");
                return;
            }

            // Thêm người dùng mới
          String insertSql = "INSERT INTO login (username, password, phone, email) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công");
                returnToLogin();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi đăng ký: " + ex.getMessage());
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (checkStmt != null) checkStmt.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) DatabaseConnection.close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void returnToLogin() {
        loginForm.setVisible(true);
        this.dispose();
    }
}