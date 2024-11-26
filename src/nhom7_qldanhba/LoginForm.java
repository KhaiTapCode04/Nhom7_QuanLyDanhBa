package nhom7_qldanhba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel lblForgotPassword;
    private JLabel lblSignupPrompt;
    private JLabel lblSignupLink;
    private final Color PRIMARY_COLOR = new Color(45, 166, 153);
    private final Color HOVER_COLOR = new Color(38, 141, 130);
    private final Color TEXT_GRAY = new Color(128, 128, 128);
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private ImageIcon userIcon;
    private ImageIcon lockIcon;

    public LoginForm() {
        initializeIcons();
        initComponents();

        setupButtonHoverEffects();
    }

    private void initializeIcons() {
        try {
            userIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/user.png"))
                    .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            lockIcon = new ImageIcon(new ImageIcon(getClass().getResource("/icons/lock.png"))
                    .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.err.println("Error loading icons: " + e.getMessage());
        }
    }

    private void initComponents() {
        setTitle("Đăng Nhập");
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

        JLabel titleLabel = new JLabel("Login Form");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 70, 40));

        txtUsername = createStyledTextField("Email or Phone", userIcon);
        txtPassword = createStyledPasswordField();

        btnLogin = createStyledButton("Login", PRIMARY_COLOR);
        btnRegister = createStyledButton("Register", new Color(70, 130, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createInputPanel("Username", txtUsername, userIcon), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createInputPanel("Password", txtPassword, lockIcon), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(btnLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(btnRegister, gbc);

        mainPanel.add(headerPanel);
        mainPanel.add(formPanel);

        add(mainPanel);
    }

    private JPanel createInputPanel(String labelText, JComponent inputField, ImageIcon icon) {
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

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));

        inputPanel.add(iconLabel);
        inputPanel.add(inputField);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(inputPanel);

        return panel;
    }

    private JTextField createStyledTextField(String placeholder, ImageIcon icon) {
        JTextField field = new JTextField(placeholder);
        field.setFont(MAIN_FONT);
        field.setForeground(TEXT_GRAY);
        field.setBorder(null);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.addFocusListener(new FocusListener() {
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

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField("Password");
        field.setFont(MAIN_FONT);
        field.setForeground(TEXT_GRAY);
        field.setBorder(null);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(field.getPassword()).equals("Password")) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(field.getPassword()).isEmpty()) {
                    field.setText("Password");
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
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 40));

        if (text.equals("Login")) {
            button.addActionListener(e -> loginUser());
        } else {
            button.addActionListener(e -> openRegisterForm());
        }
        return button;
    }

    private void styleLinkLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(PRIMARY_COLOR);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupButtonHoverEffects() {
        setupButtonHover(btnLogin);
        setupButtonHover(btnRegister);
    }

    private void setupButtonHover(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    private void loginUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()
                || username.equals("Email or Phone")
                || password.equals("Password")) {
            showError("Vui lòng nhập đầy đủ thông tin đăng nhập");
            return;
        }

        try (Connection conn = DatabaseConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM login WHERE username = ? AND password = ?")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TrangChu trangChu = new TrangChu();
                    trangChu.setLocationRelativeTo(null);
                    trangChu.setVisible(true);
                    this.dispose();
                } else {
                    showError("Tên đăng nhập hoặc mật khẩu không đúng");
                }
            }
        } catch (SQLException ex) {
            showError("Lỗi kết nối: " + ex.getMessage());
        }
    }

    private void openRegisterForm() {
        RegisterForm registerForm = new RegisterForm(this);
        registerForm.setVisible(true);
        this.setVisible(false);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}

// RoundedBorder class for input fields
class RoundedBorder implements javax.swing.border.Border {

    private final Color color;
    private final int thickness;
    private final int radius;

    public RoundedBorder(Color color, int thickness, int radius) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness + 8, thickness, thickness + 8);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
