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
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.text.Collator;
import java.util.Locale;
import java.sql.PreparedStatement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrangChu extends javax.swing.JFrame {

    private List<User> users;
    private final Collator vietnameseCollator;
    private Map<String, Color> contactColors = new TreeMap<>();

    private Map<String, JCheckBox> contactCheckboxes = new HashMap<>();
    private JButton confirmDeleteBtn;
    private boolean isSelectionMode = false;

    public TrangChu() {
        initComponents();
        vietnameseCollator = Collator.getInstance(new Locale("vi", "VN"));
        vietnameseCollator.setStrength(Collator.TERTIARY);
        // Thêm DocumentListener cho real-time search
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterContacts();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterContacts();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterContacts();
            }
        });

        setupSearchPlaceholder();
        addContactListToPanel();
    }

    private void setupSearchPlaceholder() {
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setText("Tìm kiếm...");

        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Tìm kiếm...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setForeground(Color.GRAY);
                    txtSearch.setText("Tìm kiếm...");
                }
            }
        });
    }
    // Hàm lọc danh bạ theo từ khóa tìm kiếm
    private void filterContacts() {
        String searchText = txtSearch.getText();
        if (searchText.equals("Tìm kiếm...")) {
            searchText = "";
        }

        // Nếu ô tìm kiếm trống, hiển thị lại toàn bộ danh sách
        if (searchText.isEmpty()) {
            addContactListToPanel();
            return;
        }

        // Thực hiện tìm kiếm realtime
        performContactSearch(searchText);
    }

    private static final String VIETNAMESE_ALPHABET = "aăâbcdđefghijklmnopqrstuvwxyz";

    private int compareVietnamese(String s1, String s2) {
        int minLength = Math.min(s1.length(), s2.length());
        for (int i = 0; i < minLength; i++) {
            int index1 = VIETNAMESE_ALPHABET.indexOf(s1.charAt(i));
            int index2 = VIETNAMESE_ALPHABET.indexOf(s2.charAt(i));
            if (index1 != index2) {
                return Integer.compare(index1, index2);
            }
        }
        return Integer.compare(s1.length(), s2.length()); // If equal up to the length of the shorter string
    }

    public void addContactListToPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 30));

        Connection connection = null;
        try {
            connection = DatabaseConnection.connect();
            if (connection != null) {
                users = UserDataFetcher.fetchData(connection);

                // Nhóm users theo chữ cái đầu tiên với custom comparator cho tiếng Việt
                Map<Character, List<User>> groupedUsers = new TreeMap<>(new Comparator<Character>() {
                    @Override
                    public int compare(Character c1, Character c2) {
                        return compareVietnamese(c1.toString(), c2.toString());
                    }
                });

                // Phân nhóm users
                for (User user : users) {
                    char firstLetter = user.getUsername().toUpperCase().charAt(0);
                    groupedUsers.computeIfAbsent(firstLetter, k -> new ArrayList<>()).add(user);
                }

                // Duyệt qua từng nhóm
                for (Map.Entry<Character, List<User>> entry : groupedUsers.entrySet()) {
                    // Tạo panel riêng cho mỗi nhóm chữ cái
                    JPanel groupPanel = new JPanel();
                    groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
                    groupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    // Tạo header panel chứa chữ cái và line
                    JPanel headerPanel = new JPanel();
                    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
                    headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    // Sắp xếp users trong nhóm
                    List<User> sortedUsers = entry.getValue();
                    sortedUsers.sort((u1, u2) -> compareVietnamese(
                            u1.getUsername().toLowerCase(),
                            u2.getUsername().toLowerCase()
                    ));

                    // Thêm contacts vào group
                    for (User user : sortedUsers) {
                        JPanel contactPanel = createContactItem(user.getIdUser());
                        groupPanel.add(contactPanel);
                        groupPanel.add(Box.createRigidArea(new Dimension(0, 12)));
                    }

                    // Thêm group panel vào main panel
                    mainPanel.add(groupPanel);

                    // Thêm khoảng trống giữa các nhóm
                    mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));

                }
            } else {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại.");
            }
        } finally {
            DatabaseConnection.close(connection);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        txtHomeScroll.setViewportView(scrollPane);
        txtHomeScroll.setPreferredSize(new Dimension(500, 400));
    }

    private JPanel createContactItem(int userID) {

        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.X_AXIS));
        contactPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Lấy chữ cái đầu tiên của tên liên lạc
            User selectedUser = users.stream()
                .filter(user -> user.getIdUser() == userID)
                .findFirst()
                .orElse(null);
            if (selectedUser != null) {
                String contactName = selectedUser.getUsername().toUpperCase();
                String firstLetter = contactName.substring(0, 1).toUpperCase();
                // Tạo JLabel cho ô tròn
                JLabel circleLabel = new JLabel(firstLetter, SwingConstants.CENTER);

                circleLabel.setPreferredSize(new Dimension(100, 100));
                circleLabel.setMaximumSize(new Dimension(100, 100));
                circleLabel.setMinimumSize(new Dimension(100, 100));
                circleLabel.setOpaque(true);
                circleLabel.setFont(new Font("Arial", Font.BOLD, 30));

                // Create checkbox
                JCheckBox checkbox = new JCheckBox();
                checkbox.setVisible(isSelectionMode);
                contactCheckboxes.put(contactName, checkbox);
                // Add checkbox to panel
                contactPanel.add(checkbox);
                contactPanel.add(Box.createRigidArea(new Dimension(10, 0)));

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
                                .filter(user -> user.getIdUser() == userID)
                                .findFirst()
                                .orElse(null);

                        if (selectedUser != null) {
                            // Mở màn hình chi tiết liên lạc với thông tin từ user
                            ChiTietLienLac chiTietLienLac = new ChiTietLienLac(
                                    selectedUser.getIdUser(),
                                    selectedUser.getUsername(),
                                    selectedUser.getPhone(),
                                    selectedUser.getEmail(),
                                    selectedUser.getAddress(),
                                    selectedUser.getNote(),
                                    selectedUser.getAvatar(),
                                    selectedUser.getIsBlock(),
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
            }
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
        ExportBtn = new javax.swing.JButton();
        AddDanhba = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

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

        ExportBtn.setBackground(new java.awt.Color(207, 207, 207));
        ExportBtn.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        ExportBtn.setText("Xuất File");
        ExportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportBtnActionPerformed(evt);
            }
        });

        AddDanhba.setBackground(new java.awt.Color(207, 207, 207));
        AddDanhba.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        AddDanhba.setText("Thêm");
        AddDanhba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddDanhbaActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(207, 207, 207));
        btnDelete.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnDelete.setText("Xoá Tất Cả");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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
                .addComponent(ExportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AddDanhba, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(btnDelete)
                .addGap(59, 59, 59))
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
                .addComponent(txtHomeScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddDanhba, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ExportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

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
    private void performContactSearch(String searchTerm) {
        // Chuẩn hóa searchTerm
        String normalizedSearchTerm = removeAccents(searchTerm.toLowerCase().trim());

        // Tạo panel chính chứa kết quả tìm kiếm
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 30));

        // Lọc và nhóm users theo kết quả tìm kiếm
        Map<Character, List<User>> filteredUsers = users.stream()
                .filter(user -> {
                    // Chuẩn hóa các trường thông tin của user để tìm kiếm
                    String normalizedUser = removeAccents(user.getUsername().toLowerCase());
                    String normalizedPhone = user.getPhone() != null ? user.getPhone().toLowerCase() : "";
                    String normalizedEmail = user.getEmail() != null ? removeAccents(user.getEmail().toLowerCase()) : "";
                    String normalizedAddress = user.getAddress() != null ? removeAccents(user.getAddress().toLowerCase()) : "";

                    // Tách các từ trong searchTerm
                    String[] searchWords = normalizedSearchTerm.split("\\s+");
                    boolean matchesAnyWord = false; // Chỉ cần 1 từ khớp là đủ

                    // Kiểm tra từng từ trong searchWords
                    for (String word : searchWords) {
                        if (normalizedUser.contains(word)
                                || normalizedPhone.contains(word)
                                || normalizedEmail.contains(word)
                                || normalizedAddress.contains(word)) {
                            matchesAnyWord = true; // Có từ khớp
                            break; // Dừng kiểm tra khi tìm thấy từ khớp
                        }
                    }

                    return matchesAnyWord; // Trả về true nếu có ít nhất 1 từ khớp
                })
                .collect(Collectors.groupingBy(
                        user -> Character.toUpperCase(user.getUsername().charAt(0)), // Nhóm theo chữ cái đầu tiên của tên người dùng
                        TreeMap::new,
                        Collectors.toList()
                ));

        // Kiểm tra xem có người dùng nào khớp không
        if (filteredUsers.isEmpty()) {
            // Hiển thị thông báo không tìm thấy kết quả
            JLabel noResultLabel = new JLabel("Không tìm thấy liên hệ phù hợp");
            noResultLabel.setFont(new Font("Arial", Font.BOLD, 20));
            noResultLabel.setForeground(Color.GRAY);
            noResultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(noResultLabel);
        } else {
            // Sắp xếp và hiển thị kết quả
            for (Map.Entry<Character, List<User>> entry : filteredUsers.entrySet()) {
                // Thêm tiêu đề nhóm
                JLabel groupTitle = new JLabel(String.valueOf(entry.getKey()));
                groupTitle.setFont(new Font("Arial", Font.BOLD, 20));
                groupTitle.setForeground(Color.BLUE);
                groupTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
                mainPanel.add(groupTitle);
                mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                // Sắp xếp users trong nhóm theo tiếng Việt
                List<User> sortedUsers = new ArrayList<>(entry.getValue());
                sortedUsers.sort((u1, u2) -> vietnameseCollator.compare(u1.getUsername(), u2.getUsername()));

                // Hiển thị từng contact trong nhóm
                for (User user : sortedUsers) {
                    JPanel contactPanel = createContactItem(user.getIdUser());
                    contactPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    mainPanel.add(contactPanel);
                    mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                }

                mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }

        // Cập nhật giao diện
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        txtHomeScroll.setViewportView(scrollPane);

        // Cuộn lên đầu trang
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMinimum());
        });
    }
// Hàm hỗ trợ chuẩn hóa chuỗi tiếng Việt

    private static String removeAccents(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        // Chuẩn hóa Unicode
        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);

        // Loại bỏ dấu
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");

        // Xử lý các ký tự đặc biệt
        return temp.replaceAll("đ", "d")
                .replaceAll("Đ", "D")
                .replaceAll("[^\\p{ASCII}]", ""); // Loại bỏ các ký tự không phải ASCII
    }

    private void deleteAllContacts() {
        Connection connection = null;
        try {
            connection = DatabaseConnection.connect();
            if (connection != null) {
                String sql = "DELETE FROM users"; // Thay đổi 'users' thành tên bảng của bạn
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Đã xóa " + rowsAffected + " liên lạc.");
                }
            } else {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(connection);
        }
    }
    private void txtHomeScrollAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtHomeScrollAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHomeScrollAncestorAdded

    private void ExportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportBtnActionPerformed
        // Gọi phương thức xuatFileCSV từ class xuatFileCSV
        try {
            xuatFileCSV.xuatFileCSV();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + ex.getMessage());
        }
    }//GEN-LAST:event_ExportBtnActionPerformed

    private void AddDanhbaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddDanhbaActionPerformed
   addContacts addForm = new addContacts();
    addForm.setVisible(true);
    this.dispose(); // hoặc this.setVisible(false) nếu bạn muốn giữ form TrangChu
    }//GEN-LAST:event_AddDanhbaActionPerformed
 private void deleteSelectedContacts(List<String> selectedContacts) {
    // Kiểm tra nếu danh sách liên hệ được chọn trống hoặc null
    if (selectedContacts == null || selectedContacts.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Không có liên hệ nào được chọn để xóa",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        // Kết nối đến cơ sở dữ liệu
        connection = DatabaseConnection.connect();
        if (connection != null) {
            // Tạo câu lệnh SQL để xóa liên hệ (cột 'username' là ví dụ, thay bằng cột phù hợp của bạn)
            String sql = "DELETE FROM user WHERE username = ?";
            
            // Đếm số lượng liên hệ đã xóa
            int totalDeleted = 0;

            // Lặp qua từng liên hệ và thực hiện xóa
            for (String contactId : selectedContacts) {
                // Tạo PreparedStatement mới cho mỗi lần lặp để tránh lỗi trong việc tái sử dụng
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, contactId); // Gán giá trị liên hệ vào câu lệnh SQL
                int rowsAffected = preparedStatement.executeUpdate();
                totalDeleted += rowsAffected; // Cộng dồn số dòng bị ảnh hưởng
            }

            // Hiển thị thông báo kết quả xóa
            if (totalDeleted > 0) {
                JOptionPane.showMessageDialog(this,
                        "Đã xóa thành công " + totalDeleted + " liên hệ",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không có liên hệ nào được xóa",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Không thể kết nối đến cơ sở dữ liệu",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        // Xử lý lỗi SQL
        e.printStackTrace();
        String errorMessage = "Lỗi khi xóa liên hệ: ";
        errorMessage += e.getMessage() != null ? e.getMessage() : "Không xác định được nguyên nhân";
        JOptionPane.showMessageDialog(this,
                errorMessage,
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    } finally {
        // Đóng tài nguyên trong block finally để tránh rò rỉ tài nguyên
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
  if (!isSelectionMode) {
    // Bắt đầu chế độ chọn
    isSelectionMode = true;
    btnDelete.setText("Xác nhận xóa");

    // Hiển thị checkbox và tùy chỉnh giao diện
    contactCheckboxes.values().forEach(checkbox -> {
        checkbox.setVisible(true);
        checkbox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        checkbox.setFont(new Font("Arial", Font.PLAIN, 14));
        checkbox.setBackground(new Color(240, 240, 240));
        checkbox.setOpaque(true);
        checkbox.setFocusable(false); // Tắt viền khi checkbox được chọn
    });

    // Thêm nút Hủy nếu chưa có
    if (confirmDeleteBtn == null) {
        confirmDeleteBtn = new JButton("Hủy");
        confirmDeleteBtn.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        confirmDeleteBtn.setBackground(new Color(207, 207, 207));
        confirmDeleteBtn.setFocusPainted(false); // Tắt viền khi nút được chọn

        // Thêm nút Hủy vào cùng hàng với nút Xóa
        Container parent = btnDelete.getParent();
        parent.add(confirmDeleteBtn);
        parent.revalidate();
        parent.repaint();

        // Xử lý sự kiện cho nút Hủy
        confirmDeleteBtn.addActionListener(e -> {
            // Thoát chế độ chọn
            isSelectionMode = false;
            btnDelete.setText("Xóa Tất Cả");

            // Ẩn tất cả checkbox
            contactCheckboxes.values().forEach(checkbox -> checkbox.setVisible(false));

            // Bỏ chọn tất cả checkbox
            contactCheckboxes.values().forEach(checkbox -> checkbox.setSelected(false));

            // Xóa nút Hủy
            parent.remove(confirmDeleteBtn);
            parent.revalidate();
            parent.repaint();
            confirmDeleteBtn = null; // Đảm bảo không thêm trùng nút
        });
    }
} else {
    // Kiểm tra xem có contact nào được chọn không
    List<String> selectedContacts = contactCheckboxes.entrySet().stream()
            .filter(entry -> entry.getValue().isSelected())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

    if (selectedContacts.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Vui lòng chọn ít nhất một liên hệ để xóa",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

 // Hiển thị dialog xác nhận với số lượng liên hệ đã chọn
int confirm = JOptionPane.showConfirmDialog(this,
        "Bạn có chắc chắn muốn xóa " + selectedContacts.size() + " liên hệ đã chọn?",
        "Xác nhận xóa",
        JOptionPane.YES_NO_OPTION);


    if (confirm == JOptionPane.YES_OPTION) {
        // Thực hiện xóa các contact đã chọn
        deleteSelectedContacts(selectedContacts);

        // Reset UI
        isSelectionMode = false;
        btnDelete.setText("Xóa Tất Cả");

        // Ẩn tất cả checkbox
        contactCheckboxes.values().forEach(checkbox -> checkbox.setVisible(false));

        // Xóa nút Hủy
        Container parent = btnDelete.getParent();
        parent.remove(confirmDeleteBtn);
        parent.revalidate();
        parent.repaint();
        confirmDeleteBtn = null;

        // Cập nhật lại danh sách liên hệ
        addContactListToPanel();
    }
}
    }//GEN-LAST:event_btnDeleteActionPerformed
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
    private javax.swing.JButton AddDanhba;
    private javax.swing.JButton ExportBtn;
    private javax.swing.JLabel Header;
    private javax.swing.JButton btnDelete;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton txtBtnSearch;
    private javax.swing.JScrollPane txtHomeScroll;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
