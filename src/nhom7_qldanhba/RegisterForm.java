package nhom7_qldanhba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private LoginForm loginForm;
    private final Color PRIMARY_COLOR = new Color(45, 166, 153);
    private final Color HOVER_COLOR = new Color(38, 141, 130);
    private final Color TEXT_GRAY = new Color(128, 128, 128);
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public RegisterForm(LoginForm loginForm) {
        this.loginForm = loginForm;
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng Ký");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, HOVER_COLOR);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(400, 50));
        headerPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Register Form");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 70, 40));

        txtUsername = createStyledTextField("Tên đăng nhập");
        txtPassword = createStyledPasswordField("Mật khẩu");
        txtConfirmPassword = createStyledPasswordField("Xác nhận mật khẩu");
        txtPhone = createStyledTextField("Số điện thoại");
        txtEmail = createStyledTextField("Email");

        JButton btnRegister = createStyledButton("Đăng Ký", PRIMARY_COLOR);
        JButton btnCancel = createStyledButton("Hủy", new Color(70, 130, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // Vertical spacing
        gbc.weightx = 1.0; // Allow components to stretch horizontally

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createInputPanel("Tên đăng nhập", txtUsername), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createInputPanel("Mật khẩu", txtPassword), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(createInputPanel("Xác nhận mật khẩu", txtConfirmPassword), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(createInputPanel("Số điện thoại", txtPhone), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(createInputPanel("Email", txtEmail), gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(btnRegister, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(btnCancel, gbc);

        mainPanel.add(headerPanel);
        mainPanel.add(formPanel);

        add(mainPanel);

        btnRegister.addActionListener(e -> registerUser());
        btnCancel.addActionListener(e -> returnToLogin());
    }

    private JPanel createInputPanel(String labelText, JComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(TEXT_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(Color.LIGHT_GRAY, 1, 10),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        inputPanel.add(inputField);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(inputPanel);

        return panel;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(MAIN_FONT);
        field.setForeground(TEXT_GRAY);
        field.setBorder(null);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_GRAY);
                }
            }
        });
        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);
        field.setFont(MAIN_FONT);
        field.setForeground(TEXT_GRAY);
        field.setBorder(null);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(field.getPassword()).isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_GRAY);
                }
            }
        });
        return field;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(backgroundColor.darker());
                } else if (getModel().isRollover()) {
                    g.setColor(backgroundColor.brighter());
                } else {
                    g.setColor(backgroundColor);
                }
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                FontMetrics metrics = g2.getFontMetrics(getFont());
                int width = metrics.stringWidth(getText());
                int height = metrics.getHeight();
                g2.setColor(Color.WHITE);
                g2.drawString(getText(), (getWidth() - width) / 2,
                        (getHeight() - height) / 2 + metrics.getAscent());
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(100, 45));
        return button;
    }

    private void registerUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        String phone = txtPhone.getText();
        String email = txtEmail.getText();

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

            String checkSql = "SELECT * FROM login WHERE username = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại");
                return;
            }

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
                if (rs != null) {
                    rs.close();
                }
                if (checkStmt != null) {
                    checkStmt.close();
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

    private void returnToLogin() {
        loginForm.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        new RegisterForm(new LoginForm()).setVisible(true);
    }
}
