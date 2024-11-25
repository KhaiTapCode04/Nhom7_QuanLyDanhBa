/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package nhom7_qldanhba;

/**
 *
 * @author My PC
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.awt.geom.Ellipse2D;
import java.sql.Connection; // Import the Connection class
import java.sql.PreparedStatement; // Import the PreparedStatement class
import java.sql.SQLException; // Import the SQLException class
import java.sql.Timestamp; // Import the Timestamp class
import javax.swing.Timer; // Thêm import Timer
import java.io.FileInputStream;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;
import javax.swing.border.LineBorder; // Để sử dụng LineBorder
import java.awt.Color; // Để sử dụng Color

public class addContacts extends javax.swing.JFrame {
    private File selectedImageFile; // Class-level variable to hold the selected image file
  
    private JLabel imageLabel; // Label to display the image
    
    public addContacts() {
        initComponents();
        setupImageLabel();
        setPlaceholder(addName1, "Tên");
        setPlaceholder(addName2, "Họ");
        setPlaceholder(addAddress, "Địa chỉ");
        setPlaceholder(addPhone, "Số điện thoại");
        setPlaceholder(addEmail, "Email");
        addBTN.setBorder(null); // Xóa border
        backBTN.setBorder(null); // Xóa border
        setLocationRelativeTo(null); // Căn giữa form trên màn hình
        
        // Thêm DocumentListener cho số điện thoại
        addPhone.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validatePhone(addPhone.getText().trim());
            }

            public void removeUpdate(DocumentEvent e) {
                validatePhone(addPhone.getText().trim());
            }

            public void changedUpdate(DocumentEvent e) {
                validatePhone(addPhone.getText().trim());
            }
        });
        
        addName1.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateName(addName1.getText().trim());
            }

            public void removeUpdate(DocumentEvent e) {
                validateName(addName1.getText().trim());
            }

            public void changedUpdate(DocumentEvent e) {
                validateName(addName1.getText().trim());
            }
        });

       // Thêm FocusListener cho email
        addEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Chỉ kiểm tra nếu email không rỗng
                if (!addEmail.getText().trim().isEmpty()) {
                    validateEmail(addEmail.getText().trim());
                } else {
                    // Nếu email rỗng, đặt viền về màu xám
                    jPanel8.setBorder(new LineBorder(Color.GRAY, 1));
                }
            }
        });
    }

    private void setupImageLabel() {
        imageLabel = new CircularLabel("Click to add image");
        imageLabel.setPreferredSize(new Dimension(140, 140));
        imageLabel.setMinimumSize(new Dimension(140, 140));
        imageLabel.setMaximumSize(new Dimension(140, 140));
        imageLabel.setBackground(new Color(173, 216, 230));
        imageLabel.setForeground(Color.DARK_GRAY);
        imageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectImage();
            }
        });
        

        // Use GridBagLayout for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Allow the component to grow horizontally
        gbc.weighty = 1.0; // Allow the component to grow vertically
        gbc.anchor = GridBagConstraints.CENTER; // Center the component
        jPanel3.setLayout(new GridBagLayout());
        jPanel3.add(imageLabel, gbc);
    }
    
   private void selectImage() {
    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(this);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
        selectedImageFile = fileChooser.getSelectedFile(); // Set the selected file
        try {
            BufferedImage img = ImageIO.read(selectedImageFile);
            // Set the circular image as the icon
            imageLabel.setIcon(new ImageIcon(createCircularImage(img)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

    private BufferedImage createCircularImage(Image image) {
        int diameter = 153;
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = circularImage.createGraphics();

        // Tạo vùng clip hình tròn
        g2d.setClip(new Ellipse2D.Double(0, 0, diameter, diameter));

        // Tính toán kích thước ảnh được scale
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double scale = Math.min((double) diameter / imageWidth, (double) diameter / imageHeight);
        int scaledWidth = (int) (imageWidth * scale);
        int scaledHeight = (int) (imageHeight * scale);

        // Căn giữa hình ảnh
        int x = (diameter + 12 - scaledWidth) / 2;
        int y = (diameter - scaledHeight) / 2;

        // Vẽ ảnh
        g2d.drawImage(image, x, y, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return circularImage;
    }   
    
    private void setPanelError(JPanel panel, boolean isError) {
        if (isError) {
            panel.setBorder(new LineBorder(Color.RED, 1)); // Đặt viền màu đỏ
        } else {
            panel.setBorder(new LineBorder(Color.BLACK, 1)); // Đặt viền về màu xám (hoặc màu mặc định)
        }
    }

    private boolean validatePhone(String phone) {
        boolean isValid = Pattern.matches("\\d+", phone);
        setPanelError(jPanel7, !isValid); // Cập nhật màu viền cho jPanel7 nếu có lỗi
        return isValid;
    }
    
    private boolean validateName(String username) {
        boolean isValid = !username.trim().isEmpty(); // Kiểm tra xem tên không rỗng
        setPanelError(jPanel4, !isValid); // Cập nhật màu viền cho jPanel4 nếu có lỗi
        return isValid;
    }

    private boolean validateEmail(String email) {
        boolean isValid = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);

        JPanel emailPanel = jPanel8; // assuming jPanel8 is where the email JTextField is placed
        if (!isValid && !email.isEmpty()) {
            emailPanel.setBorder(new LineBorder(Color.RED, 1)); // Highlight in red
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng email");
        } else {
            emailPanel.setBorder(new LineBorder(Color.GRAY, 1)); // Revert to default on valid or empty
        }

        return isValid;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        addNewPhone = new javax.swing.JLabel();
        addNewPhone1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        addName1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        addName2 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        addAddress = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        addPhone = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        addEmail = new javax.swing.JTextField();
        addBTN = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        addNote = new javax.swing.JTextArea();
        backBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Người liên hệ mới");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(140, 140));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thêm ảnh");

        addNewPhone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addNewPhone.setForeground(new java.awt.Color(0, 153, 153));
        addNewPhone.setText("Thêm số điện thoại");

        addNewPhone1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addNewPhone1.setForeground(new java.awt.Color(0, 153, 153));
        addNewPhone1.setText("Thêm email");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("+84");
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        addName1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addName1.setActionCommand("<Not Set>");
        addName1.setBorder(null);
        addName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addName1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(addName1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addName1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        addName2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addName2.setActionCommand("<Not Set>");
        addName2.setBorder(null);
        addName2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addName2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addName2, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addName2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        addAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addAddress.setActionCommand("<Not Set>");
        addAddress.setBorder(null);
        addAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAddressActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(addAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        addPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addPhone.setActionCommand("<Not Set>");
        addPhone.setBorder(null);
        addPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPhoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(addPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        addEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addEmail.setActionCommand("<Not Set>");
        addEmail.setBorder(null);
        addEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(addEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        addBTN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addBTN.setText("Thêm");
        addBTN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBTNActionPerformed(evt);
            }
        });

        addNote.setColumns(20);
        addNote.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addNote.setRows(5);
        addNote.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(addNote);

        backBTN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        backBTN.setText("Back");
        backBTN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        backBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(204, 204, 204))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(addNewPhone, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(addNewPhone1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(33, 33, 33)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(addNewPhone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewPhone1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addName1ActionPerformed

    private void addBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBTNActionPerformed
    // Lấy họ và tên từ các trường nhập liệu và loại bỏ khoảng trắng thừa
    String lastName = addName2.getText().trim(); // Họ
    String firstName = addName1.getText().trim(); // Tên
    
    // Xử lý tên theo chuẩn Việt Nam
    String fullName = "";
    
    // Kiểm tra nếu cả họ và tên không phải là placeholder
    if (!lastName.equals("Họ") && !firstName.equals("Tên")) {
        // Tách họ thành các phần (trong trường hợp có nhiều họ)
        String[] lastNames = lastName.split("\\s+");
        
        // Chuyển đổi chữ cái đầu tiên của mỗi phần thành chữ hoa, các chữ còn lại viết thường
        for (int i = 0; i < lastNames.length; i++) {
            if (!lastNames[i].isEmpty()) {
                // Xử lý Unicode cho tiếng Việt
                String firstChar = lastNames[i].substring(0, 1).toUpperCase();
                String restChars = lastNames[i].substring(1).toLowerCase();
                lastNames[i] = firstChar + restChars;
            }
        }
        
        // Xử lý tên: chữ cái đầu viết hoa, các chữ còn lại viết thường
        String firstChar = firstName.substring(0, 1).toUpperCase();
        String restChars = firstName.substring(1).toLowerCase();
        firstName = firstChar + restChars;
        
        // Ghép họ và tên lại
        fullName = String.join(" ", lastNames) + " " + firstName;
    } else {
        // Nếu một trong hai trường là placeholder, báo lỗi
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ họ và tên");
        return;
    }
    
    // Kiểm tra tên có hợp lệ không
    boolean isNameValid = !fullName.trim().isEmpty();
    if (!isNameValid) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên");
        return;
    }
    
    String email = addEmail.getText().trim();
    String phone = addPhone.getText().trim();
    String address = addAddress.getText().trim();
    String note = addNote.getText().trim();
    java.sql.Timestamp createdAt = new java.sql.Timestamp(System.currentTimeMillis());

    // Kiểm tra tính hợp lệ của số điện thoại
    boolean isPhoneValid = validatePhone(phone);
    if (!isPhoneValid) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số điện thoại");
        return;
    }

    // Database connection
    Connection connection = DatabaseConnection.connect();
    if (connection != null) {
        String sql = "INSERT INTO user (username, email, phone, note, created_at, avatar, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, note); 
            preparedStatement.setTimestamp(5, createdAt);
           
            // Check if an image file was selected
            if (selectedImageFile != null) {
                // Convert the selected image to a byte array
                byte[] imageBytes = new byte[(int) selectedImageFile.length()];
                try (FileInputStream fis = new FileInputStream(selectedImageFile)) {
                    fis.read(imageBytes);
                }
                preparedStatement.setBytes(6, imageBytes);
            } else {
                preparedStatement.setNull(6, java.sql.Types.BLOB);
            }
            preparedStatement.setString(7, address);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Thêm liên hệ thành công!");
                
                // Sau khi hiển thị thông báo thành công, đóng form hiện tại
                this.dispose();
                
                // Mở form TrangChu và đặt nó ở giữa màn hình
                TrangChu trangChu = new TrangChu();
                trangChu.setLocationRelativeTo(null);
                trangChu.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm liên hệ thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm liên hệ: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc ảnh: " + e.getMessage());
        } finally {
            DatabaseConnection.close(connection);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!");
    }

    }//GEN-LAST:event_addBTNActionPerformed

    private void addName2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addName2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addName2ActionPerformed

    private void addAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addAddressActionPerformed

    private void addPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addPhoneActionPerformed

    private void addEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addEmailActionPerformed

    private void backBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTNActionPerformed
       // Đóng JFrame hiện tại (addContacts)
    this.dispose();

    // Hiển thị JFrame của TrangChu và đặt nó ở giữa màn hình
    TrangChu trangChu = new TrangChu();
    trangChu.setLocationRelativeTo(null); // Thêm dòng này để form xuất hiện giữa màn hình
    trangChu.setVisible(true);

    }//GEN-LAST:event_backBTNActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.LIGHT_GRAY); // Màu chữ cho placeholder
        textField.setText(placeholder);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Màu chữ khi có nội dung
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY); // Màu chữ cho placeholder
                    textField.setText(placeholder);
                }
            }
        });
    }
    
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(addContacts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addContacts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addContacts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addContacts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
   
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addContacts().setVisible(true);
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addAddress;
    private javax.swing.JButton addBTN;
    private javax.swing.JTextField addEmail;
    private javax.swing.JTextField addName1;
    private javax.swing.JTextField addName2;
    private javax.swing.JLabel addNewPhone;
    private javax.swing.JLabel addNewPhone1;
    private javax.swing.JTextArea addNote;
    private javax.swing.JTextField addPhone;
    private javax.swing.JButton backBTN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
