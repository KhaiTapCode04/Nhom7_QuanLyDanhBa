package nhom7_qldanhba;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.AbstractBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TrangChu extends javax.swing.JFrame {
    
    private List<User> users;

    private Map<String, Color> contactColors = new TreeMap<>();

    public TrangChu() {
        initComponents();
        
        


        // Đặt border bo tròn cho txtSearch
        txtSearch.setBorder(new RoundedBorder(20, new Color(204, 204, 204)));

        if (txtSearch.getText().isEmpty()) {
            txtSearch.setText("Tìm kiếm...");  // Đảm bảo placeholder xuất hiện ngay khi bắt đầu
        }

        // Thêm sự kiện focus cho ô tìm kiếm
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Tìm kiếm...")) {
                    txtSearch.setText("");  // Xóa placeholder khi focus
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Tìm kiếm...");  // Hiển thị lại placeholder khi mất focus
                }
            }
        });

        // Thêm danh bạ vào giao diện
        addContactListToPanel();
    }

    private void addContactListToPanel() {
        // Tạo panel chính chứa danh sách liên lạc
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 30)); // Viền cho danh sách

        // Lấy kết nối từ DatabaseConnection
        Connection connection = null;
        try {
            connection = DatabaseConnection.connect();

            if (connection != null) {
                // Lấy danh sách người dùng
                users = UserDataFetcher.fetchData(connection);
                 // Tạo danh sách liên lạc mẫu
                 String[] contacts = new String[users.size()];

                // Lấy tên người dùng và thêm vào mảng contacts
                for (int i = 0; i < users.size(); i++) {
                    contacts[i] = users.get(i).getUsername().toUpperCase();

                }
                
                // Sắp xếp danh sách liên lạc theo bảng chữ cái
                Arrays.sort(contacts, String.CASE_INSENSITIVE_ORDER);
                
                

                Map<Character, JPanel> contactGroups = new TreeMap<>();

                // Chia các liên lạc theo chữ cái đầu
                for (String contact : contacts) {
                    char firstLetter = contact.charAt(0);
                    contactGroups.putIfAbsent(firstLetter, new JPanel());

                    // Lấy nhóm liên lạc của chữ cái đầu tiên
                    JPanel contactGroup = contactGroups.get(firstLetter);

                    // Thêm mục liên lạc vào nhóm với khoảng cách
                    contactGroup.add(createContactItem(contact));
                    contactGroup.add(Box.createRigidArea(new Dimension(0, 15)));
                }

                // Duyệt qua các nhóm chữ cái và thêm vào panel chính
                for (Map.Entry<Character, JPanel> entry : contactGroups.entrySet()) {
                    JLabel groupTitle = new JLabel(String.valueOf(entry.getKey()), SwingConstants.LEFT);
                    groupTitle.setFont(new Font("Arial", Font.BOLD, 20));
                    groupTitle.setForeground(Color.BLUE);
                    groupTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

                    mainPanel.add(groupTitle);

                    JPanel contactGroup = entry.getValue();
                    contactGroup.setLayout(new BoxLayout(contactGroup, BoxLayout.Y_AXIS));
                    mainPanel.add(contactGroup);
                    mainPanel.add(Box.createRigidArea(new Dimension(0, 22)));
                }

            } else {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại.");
            }
        } finally {
            // Đảm bảo đóng kết nối
            DatabaseConnection.close(connection);
        }
        

        // Thêm Panel chính vào JScrollPane để hỗ trợ cuộn
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400)); // Đặt chiều rộng và chiều cao cho JScrollPane

        // Thêm JScrollPane vào txtHomeScroll
        txtHomeScroll.setViewportView(scrollPane);
        txtHomeScroll.setPreferredSize(new Dimension(500, 400)); // Đặt kích thước mong muốn cho JScrollPane
    }

    public Color getContactColor(String contactName) {
        return contactColors.get(contactName);
    }

    private JPanel createContactItem(String contactName) {
        // Lấy chữ cái đầu tiên của tên liên lạc
        String firstLetter = contactName.substring(0, 1).toUpperCase();

        // Tạo JLabel cho ô tròn
        JLabel circleLabel = new JLabel(firstLetter, SwingConstants.CENTER);

        circleLabel.setPreferredSize(new Dimension(100, 100));
        circleLabel.setMaximumSize(new Dimension(100, 100));
        circleLabel.setMinimumSize(new Dimension(100, 100));
        circleLabel.setOpaque(true);
        circleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        // Thiết lập màu nền ngẫu nhiên cho JLabel
        Color backgroundColor = getRandomColor();
        circleLabel.setBackground(backgroundColor);
        contactColors.put(contactName, backgroundColor);

        // Chọn màu chữ (đen hoặc trắng) dựa trên độ sáng của màu nền
        circleLabel.setForeground(getContrastingColor(backgroundColor));

        // Đảm bảo kích thước JLabel là hình vuông
        circleLabel.setMaximumSize(new Dimension(45, 45));
        circleLabel.setMinimumSize(new Dimension(45, 45));

        // Tạo JLabel cho tên liên lạc
        JLabel nameLabel = new JLabel(contactName);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 28));

        // Gộp ô tròn và tên liên lạc vào một JPanel
        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.X_AXIS));
        contactPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contactPanel.add(circleLabel);
        contactPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        contactPanel.add(nameLabel);

        contactPanel.addMouseListener(new java.awt.event.MouseAdapter() {
    private Color originalPanelBackground;
    private Font originalNameFont;

    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        // Lưu màu nền gốc và font gốc
        originalPanelBackground = contactPanel.getBackground();
        originalNameFont = nameLabel.getFont();

        contactPanel.setBackground(new Color(198, 226, 255));
        contactPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 35));

        // In đậm tên liên lạc
        nameLabel.setFont(new Font("Arial", Font.BOLD, 28));

        // Giữ nguyên màu nền của ô chữ cái
        circleLabel.setBackground(backgroundColor);
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        // Khôi phục màu nền ban đầu
        contactPanel.setBackground(originalPanelBackground);
        contactPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        // Khôi phục font ban đầu
        nameLabel.setFont(originalNameFont);

        // Khôi phục màu nền của ô chữ cái
        circleLabel.setBackground(backgroundColor);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        // Tìm thông tin của user hiện tại
        User selectedUser = users.stream()
            .filter(user -> user.getUsername().toUpperCase().equals(contactName))
            .findFirst()
            .orElse(null);

        if (selectedUser != null) {
            // Mở màn hình chi tiết liên lạc với thông tin từ user
            ChiTietLienLac chiTietLienLac = new ChiTietLienLac(
                selectedUser.getUsername(),  
                selectedUser.getPhone(),
                selectedUser.getAddress(),
                selectedUser.getEmail(),
                selectedUser.getNote(),
                selectedUser.getAvatar(),
                TrangChu.this
            );
            chiTietLienLac.setVisible(true);

            // Ẩn màn hình chính
            setVisible(false);
        } else {
            System.out.println("Không tìm thấy thông tin của user: " + contactName);
        }
    }
    }); 

        return contactPanel;
    }
    
    class CirclePanel extends JPanel {
    private Color backgroundColor;

    public CirclePanel(Color color) {
        this.backgroundColor = color;
        setPreferredSize(new Dimension(44, 44)); // Kích thước 44px
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(backgroundColor);
        g.fillOval(0, 0, 44, 44); // Vẽ hình tròn
    }
}

    // Hàm lấy màu chữ tương phản với nền (sáng hay tối)
    private Color getContrastingColor(Color backgroundColor) {
        // Tính độ sáng của màu nền (dựa trên công thức độ sáng RGB)
        int brightness = (int) (0.2126 * backgroundColor.getRed() + 0.7152 * backgroundColor.getGreen() + 0.0722 * backgroundColor.getBlue());

        // Nếu độ sáng nền lớn hơn 128, chọn màu chữ đen, nếu không chọn màu chữ trắng
        return brightness > 128 ? Color.BLACK : Color.WHITE;
    }

    // Hàm tạo màu ngẫu nhiên
    private Color getRandomColor() {
        // Tạo màu ngẫu nhiên với các giá trị RGB từ 0 đến 255
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        Header = new javax.swing.JLabel();
        txtBtnSearch = new javax.swing.JButton();
        txtHomeScroll = new javax.swing.JScrollPane();
        jSeparator1 = new javax.swing.JSeparator();
        txtYeuThich = new javax.swing.JButton();
        txtAdd = new javax.swing.JButton();
        txtDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txtSearch.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        txtSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        Header.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Header.setForeground(new java.awt.Color(255, 255, 255));
        Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Header.setText("QUẢN LÝ DANH BẠ");

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

        txtBtnSearch.setBackground(new java.awt.Color(231, 231, 231));
        txtBtnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search-icon(2).png"))); // NOI18N
        txtBtnSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtBtnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBtnSearchActionPerformed(evt);
            }
        });

        txtHomeScroll.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtHomeScrollAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        txtYeuThich.setBackground(new java.awt.Color(207, 207, 207));
        txtYeuThich.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txtYeuThich.setText("Yêu Thích");
        txtYeuThich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtYeuThichActionPerformed(evt);
            }
        });

        txtAdd.setBackground(new java.awt.Color(207, 207, 207));
        txtAdd.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txtAdd.setText("Thêm");
        txtAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddActionPerformed(evt);
            }
        });

        txtDelete.setBackground(new java.awt.Color(207, 207, 207));
        txtDelete.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txtDelete.setText("Xoá");
        txtDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHomeScroll)
                            .addComponent(jSeparator1))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBtnSearch)
                        .addGap(5, 5, 5))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(txtYeuThich, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(txtAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(txtDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch)
                    .addComponent(txtBtnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHomeScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYeuThich, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(123, 123, 123)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDeleteActionPerformed

    private void txtAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddActionPerformed
        // TODO add your handling code here:
        this.dispose(); // Hoặc bạn có thể sử dụng setVisible(false);

        // Hiển thị JFrame của TrangChu
        addContacts AddContacts = new addContacts();
        AddContacts.setVisible(true);
    }//GEN-LAST:event_txtAddActionPerformed

    private void txtYeuThichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtYeuThichActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtYeuThichActionPerformed

    private void txtHomeScrollAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtHomeScrollAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHomeScrollAncestorAdded

    private void txtBtnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBtnSearchActionPerformed
        String searchTerm = txtSearch.getText().trim().toUpperCase();
        if (searchTerm.isEmpty() || searchTerm.equals("TÌM KIẾM...")) {
            // Reset to original contact list if search is empty
            addContactListToPanel();
            return;
        }

        // Perform search
        performContactSearch(searchTerm);
    }//GEN-LAST:event_txtBtnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void performContactSearch(String searchTerm) {
    // Tạo panel chính chứa kết quả tìm kiếm
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 30));

    // Lọc danh sách liên lạc dựa trên từ khóa tìm kiếm
    List<User> searchResults = users.stream()
        .filter(user -> 
            user.getUsername().toUpperCase().contains(searchTerm) ||
            (user.getPhone() != null && user.getPhone().contains(searchTerm)) ||
            (user.getEmail() != null && user.getEmail().toUpperCase().contains(searchTerm)) ||
            (user.getAddress() != null && user.getAddress().toUpperCase().contains(searchTerm))
        )
        .collect(Collectors.toList());

    if (searchResults.isEmpty()) {
        // Hiển thị thông báo không tìm thấy kết quả
        JLabel noResultLabel = new JLabel("Không tìm thấy liên hệ phù hợp");
        noResultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        noResultLabel.setForeground(Color.GRAY);
        mainPanel.add(noResultLabel);
    } else {
        // Sắp xếp kết quả theo tên
        searchResults.sort(Comparator.comparing(user -> user.getUsername().toUpperCase()));

        for (User user : searchResults) {
            String contactName = user.getUsername().toUpperCase();
            JPanel contactPanel = createContactItem(contactName);
            mainPanel.add(contactPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
    }

    // Thêm Panel chính vào JScrollPane để hỗ trợ cuộn
    JScrollPane scrollPane = new JScrollPane(mainPanel);
    scrollPane.setPreferredSize(new Dimension(500, 400));

    // Thêm JScrollPane vào txtHomeScroll
    txtHomeScroll.setViewportView(scrollPane);
    txtHomeScroll.setPreferredSize(new Dimension(500, 400));
}    public class RoundedBorder extends AbstractBorder {

        private int radius;
        private Color borderColor;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.borderColor = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(borderColor);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(10, 10, 10, 10);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            TrangChu trangChu = new TrangChu();
            trangChu.setVisible(true);
            trangChu.setLocationRelativeTo(null); // Căn giữa cửa sổ
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Header;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton txtAdd;
    private javax.swing.JButton txtBtnSearch;
    private javax.swing.JButton txtDelete;
    private javax.swing.JScrollPane txtHomeScroll;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JButton txtYeuThich;
    // End of variables declaration//GEN-END:variables
}
