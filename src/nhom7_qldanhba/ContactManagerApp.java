package nhom7_qldanhba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ContactManagerApp extends JFrame {
    private JPanel contactListPanel;
    private JButton btnAddContact;
    private JTextField txtSearchContact;

    public ContactManagerApp() {
        initComponents();
        setupLayout();
        setupEventListeners();
        loadContacts();
    }

    private void initComponents() {
        setTitle("Quản Lý Liên Lạc");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);

        contactListPanel = new JPanel();
        contactListPanel.setLayout(new BoxLayout(contactListPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(contactListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        txtSearchContact = new JTextField("Tìm kiếm liên lạc...");
        txtSearchContact.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtSearchContact.setFont(new Font("Arial", Font.PLAIN, 16));

        btnAddContact = new JButton("Thêm Liên Lạc Mới");
        btnAddContact.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnAddContact.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(txtSearchContact);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(btnAddContact);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(contactListPanel), BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        // Sự kiện thêm liên lạc mới
        btnAddContact.addActionListener(e -> showAddContactDialog());

        // Sự kiện tìm kiếm liên lạc
        txtSearchContact.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearchContact.getText().equals("Tìm kiếm liên lạc...")) {
                    txtSearchContact.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearchContact.getText().isEmpty()) {
                    txtSearchContact.setText("Tìm kiếm liên lạc...");
                }
            }
        });

        txtSearchContact.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchTerm = txtSearchContact.getText().toLowerCase();
                if (!searchTerm.equals("tìm kiếm liên lạc...")) {
                    filterContacts(searchTerm);
                }
            }
        });
    }

    private void showAddContactDialog() {
        JDialog addContactDialog = new JDialog(this, "Thêm Liên Lạc Mới", true);
        addContactDialog.setSize(400, 500);
        addContactDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField txtName = new JTextField();
        JTextField txtPhone = new JTextField();
        JTextField txtEmail = new JTextField();

        panel.add(new JLabel("Tên Liên Lạc:"));
        panel.add(txtName);
        panel.add(new JLabel("Số Điện Thoại:"));
        panel.add(txtPhone);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);

        JButton btnSave = new JButton("Lưu");
        btnSave.addActionListener(e -> {
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();

            if (!name.isEmpty()) {
                addContact(name, phone, email);
                addContactDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(addContactDialog, "Vui lòng nhập tên liên lạc");
            }
        });

        panel.add(btnSave);
        addContactDialog.add(panel);
        addContactDialog.setVisible(true);
    }

    private JPanel createContactItem(String name, String phone, String email) {
        JPanel contactPanel = new JPanel(new BorderLayout());
        contactPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Color backgroundColor = getRandomColor();
        Color textColor = getContrastingColor(backgroundColor);

        // Panel màu để hiển thị tên viết tắt
        JPanel colorPanel = new JPanel(new BorderLayout());
        colorPanel.setBackground(backgroundColor);
        JLabel initialLabel = new JLabel(name.substring(0, 1).toUpperCase());
        initialLabel.setFont(new Font("Arial", Font.BOLD, 24));
        initialLabel.setForeground(textColor);
        initialLabel.setHorizontalAlignment(SwingConstants.CENTER);
        colorPanel.add(initialLabel, BorderLayout.CENTER);
        colorPanel.setPreferredSize(new Dimension(60, 60));

        // Panel thông tin liên lạc
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel phoneLabel = new JLabel(phone);
        JLabel emailLabel = new JLabel(email);

        infoPanel.add(nameLabel);
        infoPanel.add(phoneLabel);
        infoPanel.add(emailLabel);

        contactPanel.add(colorPanel, BorderLayout.WEST);
        contactPanel.add(infoPanel, BorderLayout.CENTER);

        return contactPanel;
    }

    private void addContact(String name, String phone, String email) {
        JPanel contactPanel = createContactItem(name, phone, email);
        contactListPanel.add(contactPanel);
        contactListPanel.revalidate();
        contactListPanel.repaint();
    }

    private void filterContacts(String searchTerm) {
        for (Component comp : contactListPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel contactPanel = (JPanel) comp;
                JLabel nameLabel = findNameLabel(contactPanel);
                
                if (nameLabel != null) {
                    boolean visible = nameLabel.getText().toLowerCase().contains(searchTerm);
                    contactPanel.setVisible(visible);
                }
            }
        }
        contactListPanel.revalidate();
        contactListPanel.repaint();
    }

    private JLabel findNameLabel(JPanel contactPanel) {
        for (Component comp : ((Container)contactPanel.getComponent(1)).getComponents()) {
            if (comp instanceof JLabel) {
                return (JLabel) comp;
            }
        }
        return null;
    }

    private void loadContacts() {
        String[][] initialContacts = {
            {"John Doe", "0123456789", "john@example.com"},
            {"Jane Smith", "0987654321", "jane@example.com"},
            {"Alice Johnson", "0567891234", "alice@example.com"},
            {"Bob Williams", "0345678901", "bob@example.com"}
        };
        
        for (String[] contact : initialContacts) {
            addContact(contact[0], contact[1], contact[2]);
        }
    }

    private Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private Color getContrastingColor(Color backgroundColor) {
        int brightness = (backgroundColor.getRed() * 299 + 
                          backgroundColor.getGreen() * 587 + 
                          backgroundColor.getBlue() * 114) / 1000;
        return (brightness > 125) ? Color.BLACK : Color.WHITE;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ContactManagerApp().setVisible(true);
        });
    }
}