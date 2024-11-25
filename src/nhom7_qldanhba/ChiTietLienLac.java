package nhom7_qldanhba;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChiTietLienLac extends javax.swing.JFrame {

    private TrangChu trangChu;
    private int idUser;
    private String contactName;
    private String email;
    private String phone;
    private String address;
    private String note;
    private byte[] avatarImagePath;
    private boolean isBlock;

    private JButton btnBack, btnEdit, btnDelete;
    private JPanel actionButtonsPanel;
    private boolean isStarred = false;
    private JPanel jPanel1, jPanel2;
    private JLabel Header;

    public ChiTietLienLac(int idUser, String contactName, String phone, String email, String address, String note, byte[] avatarImagePath, boolean isBlock, TrangChu trangChu) {
        this.idUser = idUser;
        this.contactName = contactName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.trangChu = trangChu;
        this.avatarImagePath = avatarImagePath;
        this.isBlock = isBlock;
        initComponents();
    }

    private void initComponents() {
        // Khởi tạo các components chính
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Header = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        // Thiết lập kích thước cố định cho form 
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

        // Thêm các components
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
                                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        // Đặt kích thước cho navPanel 
        navPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 50));
        navPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

        // Nút quay lại (btnBack)
        btnBack = createIconButton("←", 50);
        btnBack.addActionListener(e -> {
            if (trangChu != null) {
                trangChu.setVisible(true);
            }
            dispose();
        });

        // Thêm nút quay lại vào navPanel
        navPanel.add(btnBack, BorderLayout.WEST);
        btnBack.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, -10));

        // Các nút bên phải
        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, -15, 0));
        rightButtons.setBackground(Color.WHITE);
        rightButtons.setBorder(BorderFactory.createEmptyBorder(-12, 0, 0, 0));

        btnEdit = createIconButton("✎", 45);
        btnDelete = createIconButton("Xoá", 30);
        btnDelete.addActionListener(e -> deleteContact());

        //Hiệu ứng đổi màu cho các nút
        addHoverEffect(btnEdit, new Color(0, 122, 204), Color.DARK_GRAY);

        addHoverEffect(btnDelete, new Color(231, 76, 60), Color.DARK_GRAY);
        rightButtons.add(btnEdit);

        rightButtons.add(btnDelete);

        // Thêm các nút vào phía Đông (sát phải)
        navPanel.add(rightButtons, BorderLayout.CENTER);

        // Thêm navPanel vào mainPanel
        mainPanel.add(navPanel, BorderLayout.NORTH);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        // Thêm ActionListener cho btnEdit để hiển thị trang EditContacts
        btnEdit.addActionListener(e -> {
            editContacts EditContactsPage = new editContacts(idUser, contactName, phone, email, address, note, avatarImagePath);
            EditContactsPage.setVisible(true);  // Mở trang chỉnh sửaeditComtac
            EditContactsPage.setLocationRelativeTo(null);
            dispose();  // Đóng cửa sổ hiện tại nếu cần
        });
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
        // Lấy dữ liệu từ đối tượng User
        byte[] avatarData = avatarImagePath;

        // Gọi phương thức createAvatarPanel với tham số
        JPanel avatarPanel = createAvatarPanel(avatarData, contactName);
        mainPanel.add(avatarPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tạo label cho tên liên hệ
        JLabel nameLabel = new JLabel(contactName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(nameLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    }

    private JPanel blockButtonPanel;

    private void createActionButtonsPanel(JPanel mainPanel) {
        actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        actionButtonsPanel.setBackground(Color.WHITE);
        actionButtonsPanel.setBorder(BorderFactory.createEmptyBorder(-20, 15, -55, 0));

        // Các nút khác
        addActionButton(actionButtonsPanel, "📞", "Gọi", () -> System.out.println("Gọi!"));
        addActionButton(actionButtonsPanel, "💬", "Nhắn tin", () -> System.out.println("Nhắn tin!"));
        addActionButton(actionButtonsPanel, "📹", "Video", () -> System.out.println("Video!"));

        // Nút "Chặn"
        blockButtonPanel = new JPanel(); // Tạo tham chiếu
        addActionButton(
                blockButtonPanel,
                "🚫",
                isBlock ? "Đã chặn" : "Chặn",
                () -> toggleBlockStatus() // Gọi hàm đổi trạng thái khi nhấp
        );
        actionButtonsPanel.add(blockButtonPanel);
        mainPanel.add(actionButtonsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
    }

    private void updateDatabase(boolean isBlock, int idUser) {
        Connection connection = DatabaseConnection.connect();
        if (connection != null) {
            String sql = "UPDATE user SET isBlock = ? WHERE idUser = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, isBlock);
                preparedStatement.setInt(2, idUser);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    String message = isBlock ? "Đã chặn thành công!" : "Đã bỏ chặn!";
                    JOptionPane.showMessageDialog(this, message);
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thất bại!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật trạng thái: " + e.getMessage());
            } finally {
                DatabaseConnection.close(connection);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!");
        }
    }

    private void toggleBlockStatus() {
        isBlock = !isBlock;
        updateDatabase(isBlock, idUser);
        updateBlockButton();
    }

    private void updateBlockButton() {
        if (blockButtonPanel != null) {
            // Lấy các thành phần trong blockButtonPanel
            JPanel innerPanel = (JPanel) blockButtonPanel.getComponent(0);
            JLabel iconLabel = (JLabel) innerPanel.getComponent(0);
            JLabel textLabel = (JLabel) innerPanel.getComponent(2);

            // Cập nhật nội dung hiển thị
            textLabel.setText(isBlock ? "Đã chặn" : "Chặn");
            textLabel.setForeground(isBlock ? Color.RED : Color.DARK_GRAY);
            iconLabel.setText("🚫");

            // Làm mới nút "Chặn"
            blockButtonPanel.revalidate();
            blockButtonPanel.repaint();
        }
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

        // Sử dụng JLabel để hiển thị số điện thoại
        JTextField phoneNumberField = new JTextField(phone);
        phoneNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        phoneNumberField.setBorder(BorderFactory.createEmptyBorder());
        phoneNumberField.setBackground(new Color(255, 245, 238));
        phoneNumberField.setEditable(true);
        phoneNumberField.setCaretColor(Color.BLACK);

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
        return button;
    }

    private void deleteContact() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa liên hệ này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            Connection connection = DatabaseConnection.connect();
            if (connection != null) {
                String sql = "DELETE FROM user WHERE phone = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, phone);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Đã xóa liên hệ thành công.");
                        dispose();

                        // Gọi phương thức làm mới danh sách liên hệ trong TrangChu
                        if (trangChu != null) {
                            trangChu.addContactListToPanel(); // Cập nhật danh sách liên hệ
                            trangChu.setVisible(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy liên hệ để xóa.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa liên hệ: " + e.getMessage());
                } finally {
                    DatabaseConnection.close(connection);
                }
            }
        }
    }

    private void addActionButton(JPanel panel, String icon, String text, Runnable onClickAction) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.setPreferredSize(new Dimension(100, 100));
        buttonPanel.setOpaque(false);

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
                iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
                textLabel.setForeground(new Color(0, 122, 204));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
                textLabel.setForeground(Color.DARK_GRAY);
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (onClickAction != null) {
                    onClickAction.run();
                }
            }
        });
    }

    private JPanel createAvatarPanel(byte[] avatarData, String contactName) {
        // Tạo CircularLabel cho avatar
        CircularLabel avatarLabel = new CircularLabel("");
        avatarLabel.setPreferredSize(new Dimension(190, 190));

        if (avatarData != null && avatarData.length > 0) {
            try {
                // Chuyển đổi byte[] thành ảnh
                ByteArrayInputStream bis = new ByteArrayInputStream(avatarData);
                BufferedImage avatarImage = ImageIO.read(bis);

                if (avatarImage != null) {
                    // Đặt ảnh vào CircularLabel
                    Image scaledAvatarImage = avatarImage.getScaledInstance(190, 190, Image.SCALE_SMOOTH); // Scale ảnh
                    avatarLabel.setIcon(new ImageIcon(scaledAvatarImage));
                    System.out.println("Đã tải avatar từ dữ liệu BLOB.");
                } else {
                    System.out.println("Dữ liệu avatar không phải là ảnh hợp lệ.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Lỗi khi đọc dữ liệu avatar.");
            }
        } else {
            String firstLetter = contactName.substring(0, 1).toUpperCase();
            avatarLabel.setText(firstLetter);
            System.out.println("Không có dữ liệu avatar, hiển thị chữ cái đầu.");
        }

        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        avatarPanel.add(avatarLabel);
        avatarPanel.setOpaque(false);

        return avatarPanel;
    }

    private Color getContrastingColor(Color backgroundColor) {
        if (backgroundColor == null) {

            return Color.BLACK;
        }

        int brightness = (int) (0.2126 * backgroundColor.getRed() + 0.7152 * backgroundColor.getGreen() + 0.0722 * backgroundColor.getBlue());
        return brightness > 128 ? Color.BLACK : Color.RED;
    }

    public static void main(String[] args) {
    }

}
