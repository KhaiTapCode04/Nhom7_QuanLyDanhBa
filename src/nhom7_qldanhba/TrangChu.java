package nhom7_qldanhba;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.AbstractBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TrangChu extends javax.swing.JFrame {

    private Map<String, Color> contactColors = new HashMap<>();

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
        
        // Thêm sự kiện xử lý tìm kiếm cho txtBtnSearch và txtSearch
        txtSearch.addActionListener(evt -> handleSearch()); // Tìm kiếm khi nhấn Enter trong txtSearch
        txtBtnSearch.addActionListener(evt -> handleSearch()); // Tìm kiếm khi nhấn nút tìm kiếm

        // Thêm sự kiện khi xóa tên tìm kiếm
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                handleSearch();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                // Nếu ô tìm kiếm trống, quay lại trang chủ
                if (txtSearch.getText().trim().isEmpty() || txtSearch.getText().equals("Tìm kiếm...")) {
                    addContactListToPanel();
                }
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                // Không cần xử lý thêm, nhưng vẫn phải implement để có thể chạy được
            }
        });

        // Thêm danh bạ vào giao diện
        addContactListToPanel();
    }
    
        private void handleSearch() {
        String searchQuery = txtSearch.getText().trim().toLowerCase();

        if (searchQuery.isEmpty() || searchQuery.equals("Tìm kiếm...")) {
            addContactListToPanel(); // Hiển thị lại toàn bộ danh bạ nếu tìm kiếm rỗng
        } else {
            filterContacts(searchQuery); // Lọc danh sách theo từ khóa tìm kiếm
        }
    }

    private void filterContacts(String searchQuery) {
        // Tạo panel chính chứa danh sách liên lạc
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 30)); // Viền cho danh sách

        // Tạo danh sách liên lạc mẫu
        String[] contacts = {
            "Nguyen Van A", "Tran Thi B", "Anh ba", "Le Van C", "Aham Van D",
            "Doan Thi E", "Hoang Van F", "Bui Thi G", "Bguyen Van H"
        };

        // Sắp xếp danh sách liên lạc theo bảng chữ cái
        Arrays.sort(contacts);

        // Duyệt qua danh bạ và lọc theo từ khóa tìm kiếm
        for (String contact : contacts) {
            if (contact.toLowerCase().contains(searchQuery)) {
                mainPanel.add(createContactItem(contact)); // Thêm liên lạc vào panel nếu tên chứa từ khóa
                mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Tăng khoảng cách giữa các liên lạc
            }
        }

        if (mainPanel.getComponentCount() == 0) {
            JLabel noResultsLabel = new JLabel("Không tìm thấy kết quả", SwingConstants.CENTER);
            noResultsLabel.setFont(new Font("Arial", Font.ITALIC, 18));
            mainPanel.add(noResultsLabel);
        }

        // Thêm Panel vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        // Thêm JScrollPane vào txtHomeScroll
        txtHomeScroll.setViewportView(scrollPane);
        txtHomeScroll.setPreferredSize(new Dimension(500, 400)); // Đặt kích thước mong muốn cho JScrollPane
    }

    private void addContactListToPanel() {
        // Tạo panel chính chứa danh sách liên lạc
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 30)); // Viền cho danh sách

        // Tạo danh sách liên lạc mẫu
        String[] contacts = {
            "Nguyen Van A", "Tran Thi B", "Anh ba", "Le Van C", "Aham Van D",
            "Doan Thi E", "Hoang Van F", "Bui Thi G", "Bguyen Van H"
        };

        // Sắp xếp danh sách liên lạc theo bảng chữ cái
        Arrays.sort(contacts);

        // Dùng HashMap để nhóm theo chữ cái đầu
        Map<Character, JPanel> contactGroups = new HashMap<>();

        // Chia các liên lạc theo chữ cái đầu
        for (String contact : contacts) {
            char firstLetter = contact.charAt(0);
            contactGroups.putIfAbsent(firstLetter, new JPanel());

            // Lấy nhóm liên lạc của chữ cái đầu tiên
            JPanel contactGroup = contactGroups.get(firstLetter);

            // Thêm mục liên lạc vào nhóm với khoảng cách
            contactGroup.add(createContactItem(contact));

            // Tạo một khoảng cách giữa các liên lạc trong cùng nhóm
            contactGroup.add(Box.createRigidArea(new Dimension(0, 30)));
        }

        // Duyệt qua các nhóm chữ cái và thêm vào panel chính
        for (Map.Entry<Character, JPanel> entry : contactGroups.entrySet()) {
            // Tạo một tiêu đề cho mỗi nhóm chữ cái
            JLabel groupTitle = new JLabel(String.valueOf(entry.getKey()), SwingConstants.LEFT);
            groupTitle.setFont(new Font("Arial", Font.BOLD, 20));
            groupTitle.setForeground(Color.BLUE);
            groupTitle.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái
            mainPanel.add(groupTitle);

            // Thêm các liên lạc vào nhóm
            JPanel contactGroup = entry.getValue();
            contactGroup.setLayout(new BoxLayout(contactGroup, BoxLayout.Y_AXIS));
            mainPanel.add(contactGroup);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 22))); // Khoảng cách giữa các nhóm
        }

        // Thêm Panel chính vào JScrollPane để hỗ trợ cuộn
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400)); // Đặt chiều rộng và chiều cao cho JScrollPane

        // Thêm JScrollPane vào txtHomeScroll
        txtHomeScroll.setViewportView(scrollPane);
        txtHomeScroll.setPreferredSize(new Dimension(500, 400)); // Đặt kích thước mong muốn cho JScrollPane
    }

    private JPanel createContactItem(String contactName) {
    // Lấy chữ cái đầu tiên của tên liên lạc
    String firstLetter = contactName.substring(0, 1).toUpperCase();

    // Tạo JLabel cho ô tròn
    JLabel circleLabel = new JLabel(firstLetter, SwingConstants.CENTER);
    circleLabel.setPreferredSize(new Dimension(100, 100));
    circleLabel.setOpaque(true);
    circleLabel.setFont(new Font("Arial", Font.BOLD, 30));

    // Thiết lập màu nền ngẫu nhiên cho JLabel
    Color backgroundColor = getRandomColor();
    circleLabel.setBackground(backgroundColor);
    contactColors.put(contactName, backgroundColor); // Thêm dòng này

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
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            // Mở màn hình chi tiết liên lạc khi click
            ChiTietLienLac chiTietLienLac = new ChiTietLienLac(contactName, TrangChu.this);
            chiTietLienLac.setVisible(true);

            // Ẩn màn hình chính (Trang chủ)
            setVisible(false);
        }
    });



    return contactPanel;
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
    public Color getContactColor(String contactName) {
    // Logic để lấy màu sắc của liên lạc dựa trên tên
    // Ví dụ: trả về một màu ngẫu nhiên hoặc theo một quy tắc nào đó.
    // Dưới đây, tôi sử dụng mã màu ngẫu nhiên cho mỗi liên lạc
    return contactColors.getOrDefault(contactName, getRandomColor());  // Trả về màu đã lưu trong contactColors hoặc màu ngẫu nhiên
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBtnSearch)
                .addGap(11, 11, 11))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtHomeScroll)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch)
                    .addComponent(txtBtnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(txtHomeScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtBtnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBtnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBtnSearchActionPerformed

    private void txtHomeScrollAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtHomeScrollAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHomeScrollAncestorAdded
    public class RoundedBorder extends AbstractBorder {

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
    private javax.swing.JButton txtBtnSearch;
    private javax.swing.JScrollPane txtHomeScroll;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
