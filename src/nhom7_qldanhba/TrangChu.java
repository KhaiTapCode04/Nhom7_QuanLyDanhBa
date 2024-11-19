package nhom7_qldanhba;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.AbstractBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TrangChu extends javax.swing.JFrame {

    public TrangChu() {
        initComponents();
        // Đặt border bo tròn cho txtSearch
        txtSearch.setBorder(new RoundedBorder(20, new Color(204, 204, 204)));  // Màu xám nhạt giống ô tìm kiếm thông thường

        // Thêm một biểu tượng kính lúp và gợi ý placeholder
        addSearchIcon();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Header = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(226, 248, 226));

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
                .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtSearch.setText("jTextField1");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(630, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed
    // Phương thức thêm ô tìm kiếm
                                    
    private void addSearchIcon() {
    // Đặt placeholder khi focus vào ô tìm kiếm
    txtSearch.setText("Tìm kiếm...");

    // Thêm icon vào bên trái của ô tìm kiếm
    Icon searchIcon = new ImageIcon(getClass().getResource("/icons/search-icon.png"));

    // Lấy chiều cao của ô tìm kiếm và điều chỉnh kích thước icon
    int iconWidth = txtSearch.getHeight() - 10;  // Giảm chiều rộng icon một chút để không chiếm hết không gian
    int iconHeight = txtSearch.getHeight() - 10; // Giảm chiều cao icon một chút

    // Thay đổi kích thước icon cho vừa với ô tìm kiếm
    Image image = ((ImageIcon) searchIcon).getImage();
    Image scaledImage = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
    searchIcon = new ImageIcon(scaledImage);

    // Căn chỉnh icon vào bên trái của ô tìm kiếm
    txtSearch.setLayout(new BorderLayout());
    JLabel iconLabel = new JLabel(searchIcon);
    txtSearch.add(iconLabel, BorderLayout.WEST);
    txtSearch.setHorizontalAlignment(JTextField.LEFT);  // Đảm bảo text căn trái

    // Thêm một sự kiện khi người dùng focus vào ô tìm kiếm (xóa placeholder)
    txtSearch.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (txtSearch.getText().equals("Tìm kiếm...")) {
                txtSearch.setText(""); // Xóa placeholder khi focus vào
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (txtSearch.getText().isEmpty()) {
                txtSearch.setText("Tìm kiếm..."); // Đặt lại placeholder khi mất focus
            }
        }
    });
}
      // Lớp RoundedBorder để bo tròn góc và thay đổi màu sắc viền
    public class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color borderColor;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.borderColor = color;
        }

        @Override
        public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(borderColor);
            g2d.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(java.awt.Component c) {
            return new Insets(5, 5, 5, 5);  // Cách viền
        }
    }
    
    public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            TrangChu trangChu = new TrangChu();
            trangChu.setVisible(true);
            // Căn giữa cửa sổ
            trangChu.setLocationRelativeTo(null);
        }
    });
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Header;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
