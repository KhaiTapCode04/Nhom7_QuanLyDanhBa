package nhom7_qldanhba;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChiTietLienLac extends javax.swing.JFrame {

    private TrangChu trangChu;
    private String contactName;
    private String email;
    private String phone;
    private String address;
    private String note;

    private Color avatarColor;
    private JButton btnBack, btnEdit, btnStar, btnDelete;
    private JPanel actionButtonsPanel;
    private boolean isStarred = false;
    private JPanel jPanel1, jPanel2;
    private JLabel Header;
    

    public ChiTietLienLac(String contactName,String phone, String email, String address, String note, TrangChu trangChu) {
        this.contactName = contactName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.trangChu = trangChu;
        this.avatarColor = trangChu.getContactColor(contactName);
        initComponents();
    }


    private void initComponents() {
        // Khởi tạo các components chính
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Header = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        // Thiết lập kích thước cố định cho form giống với TrangChu
        setPreferredSize(new Dimension(564, 952));

        // Thiết lập Panel chính
        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        // Thiết lập Panel Header
        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        Header.setFont(new java.awt.Font("Segoe UI", 1, 36));
        Header.setForeground(new java.awt.Color(255, 255, 255));
        Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Header.setText("CHI TIẾT LIÊN LẠC");

        // Layout cho Panel Header
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // Panel chính cho nội dung
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // Thêm các components từ code cũ
        createNavigationPanel(mainContentPanel);
        createAvatarAndNamePanel(mainContentPanel);
        createActionButtonsPanel(mainContentPanel);
        createContactInfoPanel(mainContentPanel);

        // Bọc mainContentPanel trong JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Không cho phép cuộn ngang
        scrollPane.setBorder(null); // Xóa viền của JScrollPane

        // Layout cho Panel chính
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, javax.swing.GroupLayout .DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane)
                                .addContainerGap())
        );

        // Layout chính cho Frame
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null); // Căn giữa màn hình
    }

    private void createNavigationPanel(JPanel mainPanel) {
    JPanel navPanel = new JPanel(new BorderLayout());
    navPanel.setBackground(Color.WHITE);

    // Đặt kích thước cho navPanel sao cho không gian được phân phối hợp lý
    navPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 50));
    navPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

    // Nút quay lại (btnBack) nằm sát trái
    btnBack = createIconButton("←", 50);
    btnBack.addActionListener(e -> {
        if (trangChu != null) {
            trangChu.setVisible(true);
        }
        dispose();
    });

    // Thêm nút quay lại vào navPanel ở phía Tây (sát trái)
    navPanel.add(btnBack, BorderLayout.WEST);
    btnBack.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, -10)); // Loại bỏ lề xung quanh btnBack

    // Các nút bên phải (edit, star, delete)
    JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, -15, 0));
    rightButtons.setBackground(Color.WHITE);
    rightButtons.setBorder(BorderFactory.createEmptyBorder(-12, 0, 0, 0));

    btnEdit = createIconButton("✎", 45);  // Nút chỉnh sửa
    btnStar = createIconButton("☆", 40);   // Nút đánh dấu
    btnDelete = createIconButton("Xoá", 30); // Nút thùng rác (biểu tượng Unicode)
 
    // Thêm hiệu ứng đổi màu cho các nút
    addHoverEffect(btnEdit, new Color(0, 122, 204), Color.DARK_GRAY);
    addHoverEffect(btnStar, new Color(0, 122, 204), Color.DARK_GRAY);
    addHoverEffect(btnDelete, new Color(231, 76, 60), Color.DARK_GRAY); // Màu cho thùng rác

    rightButtons.add(btnEdit);
    rightButtons.add(btnStar);
    rightButtons.add(btnDelete);

    // Thêm các nút vào phía Đông (sát phải)
    navPanel.add(rightButtons, BorderLayout.CENTER);

    // Thêm navPanel vào mainPanel
    mainPanel.add(navPanel, BorderLayout.NORTH);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
}

// Phương thức thêm hiệu ứng đổi màu cho nút
private void addHoverEffect(JButton button, Color hoverColor, Color originalColor) {
    button.setForeground(originalColor); // Đặt màu chữ ban đầu

    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setForeground(hoverColor); // Đổi màu khi di chuột vào
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setForeground(originalColor); // Trở lại màu ban đầu khi rời chuột
        }
    });
}



    private void createAvatarAndNamePanel(JPanel mainPanel) {
        JPanel avatarPanel = createAvatarPanel();
        mainPanel.add(avatarPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel nameLabel = new JLabel(contactName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(nameLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    }

   private void createActionButtonsPanel(JPanel mainPanel) {
    actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
    actionButtonsPanel.setBackground(Color.WHITE); // Nền của panel chính vẫn có thể giữ màu trắng

  
    
    actionButtonsPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, -55, 0));

    addActionButton(actionButtonsPanel, "📞", "Gọi");
    addActionButton(actionButtonsPanel, "💬", "Nhắn tin");
    addActionButton(actionButtonsPanel, "📹", "Video");

    mainPanel.add(actionButtonsPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
}

    private void createContactInfoPanel(JPanel mainPanel) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel titleLabel = new JLabel("Thông tin liên hệ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        infoPanel.add(titleLabel);

        JPanel phonePanel = createPhoneInfoPanel();
        infoPanel.add(phonePanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel emailPanel = createInfoPanel("📧", email, "Công việc");
        infoPanel.add(emailPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel addressPanel = createInfoPanel("🏠", address, "Địa chỉ nhà");
        infoPanel.add(addressPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel notePanel = createInfoPanel("📝", note, "Ghi chú");
        infoPanel.add(notePanel);

        mainPanel.add(infoPanel);
    }


    private JPanel createPhoneInfoPanel() {
    JPanel phonePanel = new JPanel();
    phonePanel.setLayout(new BorderLayout(10, 0));
    phonePanel.setBackground(new Color(255, 245, 238));
    phonePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
    ));
    phonePanel.setMaximumSize(new Dimension(400, 80));

    JLabel phoneIcon = new JLabel("📞");
    phoneIcon.setFont(new Font("Segoe UI", Font.PLAIN, 20));

    JPanel phoneInfo = new JPanel();
    phoneInfo.setLayout(new BoxLayout(phoneInfo, BoxLayout.Y_AXIS));
    phoneInfo.setBackground(new Color(255, 245, 238));

    // Sử dụng JTextField thay vì JLabel để hiển thị số điện thoại
    JTextField phoneNumberField = new JTextField(phone);
    phoneNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    phoneNumberField.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền mặc định
    phoneNumberField.setBackground(new Color(255, 245, 238)); // Màu nền trùng với panel
    phoneNumberField.setEditable(true); // Cho phép nhập liệu
    phoneNumberField.setCaretColor(Color.BLACK); // Màu con trỏ nhập liệu

    JLabel phoneType = new JLabel("Di động");
    phoneType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    phoneType.setForeground(Color.GRAY);

    phoneInfo.add(phoneNumberField);
    phoneInfo.add(Box.createRigidArea(new Dimension(0, 3)));
    phoneInfo.add(phoneType);

    JPanel rightIcons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
    rightIcons.setBackground(new Color(255, 245, 238));

    JLabel videoIcon = new JLabel("📹");
    videoIcon.setFont(new Font("Segoe UI", Font.PLAIN, 20));
    JLabel messageIcon = new JLabel("💬");
    messageIcon.setFont(new Font("Segoe UI", Font.PLAIN, 20));

    rightIcons.add(videoIcon);
    rightIcons.add(messageIcon);

    phonePanel.add(phoneIcon, BorderLayout.WEST);
    phonePanel.add(phoneInfo, BorderLayout.CENTER);
    phonePanel.add(rightIcons, BorderLayout.EAST);

    // Lưu thay đổi khi người dùng chỉnh sửa số điện thoại
    phoneNumberField.addActionListener(e -> phone = phoneNumberField.getText());

    return phonePanel;
}


    private JPanel createInfoPanel(String icon, String info, String subInfo) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout(10, 0));
        infoPanel.setBackground(new Color(255, 245, 238));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        infoPanel.setMaximumSize(new Dimension(400, 80));

        JLabel leftIcon = new JLabel(icon);
        leftIcon.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        JPanel textInfo = new JPanel();
        textInfo.setLayout(new BoxLayout(textInfo, BoxLayout.Y_AXIS));
        textInfo.setBackground(new Color(255, 245, 238));

        JLabel mainInfo = new JLabel(info);
        mainInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel secondaryInfo = new JLabel(subInfo);
        secondaryInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        secondaryInfo.setForeground(Color.GRAY);

        textInfo.add(mainInfo);
        textInfo.add(Box.createRigidArea(new Dimension(0, 3)));
        textInfo.add(secondaryInfo);

        infoPanel.add(leftIcon, BorderLayout.WEST);
        infoPanel.add(textInfo, BorderLayout.CENTER);

        return infoPanel;
    }

    private JButton createIconButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
 button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    // Xóa ô vuông bên cạnh biểu tượng
    button.setFocusPainted(false);
        return button;
    }

   private void addActionButton(JPanel panel, String icon, String text) {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setBackground(new Color(0, 0, 0, 0)); // Đặt nền trong suốt
    buttonPanel.setPreferredSize(new Dimension(100, 100));
    buttonPanel.setOpaque(false); // Đảm bảo panel trong suốt

    JLabel iconLabel = new JLabel(icon);
    iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel textLabel = new JLabel(text);
    textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    textLabel.setForeground(Color.DARK_GRAY);

    buttonPanel.add(iconLabel);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    buttonPanel.add(textLabel);

    panel.add(buttonPanel);

    // Thêm hiệu ứng hover cho buttonPanel
    buttonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 35)); // Tăng kích thước icon
            textLabel.setForeground(new Color(0, 122, 204)); // Thay đổi màu chữ
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30)); // Trở lại kích thước gốc
            textLabel.setForeground(Color.DARK_GRAY); // Trở lại màu chữ gốc
        }
    });
}

    private JPanel createAvatarPanel() {
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                String firstLetter = contactName.substring(0, 1).toUpperCase();

                g2d.setColor(avatarColor);
                g2d.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

                Color textColor = getContrastingColor(avatarColor);
                g2d.setColor(textColor);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 120));
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(firstLetter)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(firstLetter, x, y);

                g2d.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(190, 190);
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };

        avatarPanel.setMaximumSize(new Dimension(190, 190));
        avatarPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        avatarPanel.setOpaque(false);

        return avatarPanel;
    }

    private Color getContrastingColor(Color backgroundColor) {
        int brightness = (int) (0.2126 * backgroundColor.getRed() + 0.7152 * backgroundColor.getGreen() + 0.0722 * backgroundColor.getBlue());
        return brightness > 128 ? Color.BLACK : Color.WHITE;
    }

    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
////        ChiTietLienLac chiTietLienLac = new ChiTietLienLac(
////            "Anh Của Trường",
////             "0978697129",
////            "danghuynhk@gmail.com",
////            "123 Đường ABC, Quận X, Thành phố Y",
////            "Liên hệ trong giờ hành chính",
////            null
////        );
//        chiTietLienLac.setVisible(true);
    });
}

} 