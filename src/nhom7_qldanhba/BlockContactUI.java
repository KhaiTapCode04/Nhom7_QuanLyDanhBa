
package nhom7_qldanhba;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BlockContactUI extends JFrame {
    private JButton blockButton;
    private JLabel statusLabel;
     private byte[] avatarImagePath;

    public BlockContactUI() {
        setTitle("Block Contact");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đềs
        JLabel titleLabel = new JLabel("Chặn liên hệ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Nút Chặn
        blockButton = new JButton("Chặn");
        blockButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        blockButton.setBackground(new Color(255, 87, 87));
        blockButton.setForeground(Color.WHITE);
        blockButton.setFocusPainted(false);
        blockButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        blockButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Thêm chức năng cho nút Chặn
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockContact();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(blockButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Nhãn trạng thái
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(100, 100, 100));
        add(statusLabel, BorderLayout.SOUTH);
    }

    // Hàm xử lý chặn
    private void blockContact() {
        // Hiển thị trạng thái "Đã chặn"
        statusLabel.setText("Liên hệ đã bị chặn.");
        blockButton.setEnabled(false); // Vô hiệu hóa nút
        blockButton.setText("Đã chặn");
        blockButton.setBackground(new Color(200, 200, 200));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlockContactUI ui = new BlockContactUI();
            ui.setVisible(true);
        });
    }
}