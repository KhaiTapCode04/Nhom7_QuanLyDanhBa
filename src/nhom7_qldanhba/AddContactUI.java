package nhom7_qldanhba;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddContactUI extends JFrame {
    
    
    private JTextField nameField, lastNameField, companyField, phoneField, emailField;
    private JButton addImageButton;
    private ImageIcon contactImage;
    private final Color BACKGROUND_COLOR = new Color(18, 18, 18);
    private final Color TEXT_COLOR = new Color(255, 255, 255);
    private final Color FIELD_BACKGROUND = new Color(30, 30, 30);
    private final Color ACCENT_COLOR = new Color(215, 190, 105);
    private JPanel formPanel;
    private List<JTextField> phoneFields;
    private List<JTextField> emailFields;

    public AddContactUI() {
        setTitle("Tạo người liên hệ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 700);
        setLocationRelativeTo(null);
        
        phoneFields = new ArrayList<>();
        emailFields = new ArrayList<>();
        
        // Main panel with dark background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a scroll pane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(BACKGROUND_COLOR);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        // Header with back button and save button
        JPanel headerPanel = createHeaderPanel();
        
        // Image button panel
        JPanel imagePanel = createImageButtonPanel();
        
        // Form fields
        formPanel = createFormPanel();

        // Add all components
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(imagePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);

        scrollPane.setViewportView(mainPanel);
        getContentPane().add(scrollPane);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private JPanel createHeaderPanel() {
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(BACKGROUND_COLOR);
    headerPanel.setPreferredSize(new Dimension(420, 952)); // Đặt chiều rộng là 420 và chiều dài (chiều cao) là 952

    JButton backButton = new JButton("×");
    styleButton(backButton, BACKGROUND_COLOR, TEXT_COLOR);
    backButton.setFont(new Font("Arial", Font.PLAIN, 24));
    backButton.addActionListener(e -> dispose());
    
    JLabel titleLabel = new JLabel("Tạo người liên hệ");
    titleLabel.setForeground(TEXT_COLOR);
    titleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
    
    JButton saveButton = new JButton("Lưu");
    styleButton(saveButton, ACCENT_COLOR, Color.BLACK);
    saveButton.setFont(new Font("Arial", Font.BOLD, 14));
    saveButton.addActionListener(e -> saveContact());

    headerPanel.add(backButton, BorderLayout.WEST);
    headerPanel.add(titleLabel, BorderLayout.CENTER);
    headerPanel.add(saveButton, BorderLayout.EAST);

    return headerPanel;
}

    
    private JPanel createImageButtonPanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBackground(BACKGROUND_COLOR);
        
        addImageButton = new JButton("\u2795");
        addImageButton.setPreferredSize(new Dimension(100, 100));
        addImageButton.setMaximumSize(new Dimension(100, 100));
        addImageButton.setBackground(FIELD_BACKGROUND);
        addImageButton.setForeground(ACCENT_COLOR);
        addImageButton.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        addImageButton.setFont(new Font("Arial", Font.PLAIN, 40));
        addImageButton.addActionListener(e -> chooseImage());
        addImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel addImageLabel = new JLabel("Thêm ảnh");
        addImageLabel.setForeground(ACCENT_COLOR);
        addImageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        addImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        imagePanel.add(addImageButton);
        imagePanel.add(Box.createVerticalStrut(10));
        imagePanel.add(addImageLabel);

        return imagePanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BACKGROUND_COLOR);

        nameField = createStyledTextField("Tên");
        lastNameField = createStyledTextField("Họ");
        companyField = createStyledTextField("Công ty");
        
        // Phone field with flag
        JPanel phonePanel = new JPanel(new BorderLayout());
        phonePanel.setBackground(FIELD_BACKGROUND);
        JLabel flagLabel = new JLabel("🇻🇳");
        flagLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        phoneField = createStyledTextField("Điện thoại (Di động)");
        phoneFields.add(phoneField);
        phonePanel.add(flagLabel, BorderLayout.WEST);
        phonePanel.add(phoneField, BorderLayout.CENTER);
        
        emailField = createStyledTextField("Email (Nhà riêng)");
        emailFields.add(emailField);

        formPanel.add(createFieldContainer("Tên", nameField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createFieldContainer("Họ", lastNameField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createFieldContainer("Công ty", companyField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createFieldContainer(null, phonePanel));
        
        JButton addPhoneButton = createAddButton("Thêm số điện thoại");
        addPhoneButton.addActionListener(e -> addNewPhoneField());
        formPanel.add(addPhoneButton);
        
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createFieldContainer(null, emailField));
        
        JButton addEmailButton = createAddButton("Thêm email");
        addEmailButton.addActionListener(e -> addNewEmailField());
        formPanel.add(addEmailButton);

        return formPanel;
    }

    private void addNewPhoneField() {
        JPanel phonePanel = new JPanel(new BorderLayout());
        phonePanel.setBackground(FIELD_BACKGROUND);
        JLabel flagLabel = new JLabel("🇻🇳");
        flagLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JTextField newPhoneField = createStyledTextField("Điện thoại (Di động)");
        phoneFields.add(newPhoneField);
        
        phonePanel.add(flagLabel, BorderLayout.WEST);
        phonePanel.add(newPhoneField, BorderLayout.CENTER);
        
        int insertIndex = formPanel.getComponentCount() - 3;
        formPanel.add(Box.createVerticalStrut(10), insertIndex);
        formPanel.add(createFieldContainer(null, phonePanel), insertIndex + 1);
        
        formPanel.revalidate();
        formPanel.repaint();
    }

    private void addNewEmailField() {
        JTextField newEmailField = createStyledTextField("Email (Nhà riêng)");
        emailFields.add(newEmailField);
        
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createFieldContainer(null, newEmailField));
        
        formPanel.revalidate();
        formPanel.repaint();
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            contactImage = new ImageIcon(selectedFile.getPath());
            Image img = contactImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            addImageButton.setText("");
            addImageButton.setIcon(new ImageIcon(img));
        }
    }

    private void saveContact() {
        // Validate required fields
        if (nameField.getText().trim().isEmpty() || 
            lastNameField.getText().trim().isEmpty() || 
            phoneFields.stream().allMatch(field -> field.getText().trim().isEmpty())) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng điền đầy đủ thông tin bắt buộc (Tên, Họ và ít nhất một số điện thoại)",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Here you would typically save the contact to your database or storage
        // For now, we'll just show a success message
        JOptionPane.showMessageDialog(this,
            "Đã lưu liên hệ thành công!",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setBackground(FIELD_BACKGROUND);
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(FIELD_BACKGROUND),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    private JPanel createFieldContainer(String labelText, JComponent field) {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(BACKGROUND_COLOR);
        
        if (labelText != null) {
            JLabel label = new JLabel(labelText);
            label.setForeground(TEXT_COLOR);
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            container.add(label, BorderLayout.NORTH);
        }
        
        container.add(field, BorderLayout.CENTER);
        return container;
    }

    private JButton createAddButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(ACCENT_COLOR);
        button.setBackground(BACKGROUND_COLOR);
        button.setBorder(null);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            AddContactUI frame = new AddContactUI();
            frame.setVisible(true);
        });
    }
}