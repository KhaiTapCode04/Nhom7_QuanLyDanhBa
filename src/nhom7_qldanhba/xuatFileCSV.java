package nhom7_qldanhba;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class xuatFileCSV {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Xuất file CSV");
        JButton exportButton = new JButton("Xuất danh bạ");

        exportButton.addActionListener(e -> xuatFileCSV());
        frame.add(exportButton);
        frame.setSize(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void xuatFileCSV() {
        List<User> users;

        // Kết nối cơ sở dữ liệu và lấy dữ liệu
        try (Connection connection = DatabaseConnection.connect()) {
            if (connection != null) {
                // Lấy danh sách người dùng từ cơ sở dữ liệu
                users = UserDataFetcher.fetchData(connection);

                // Tạo file CSV
                try (FileWriter fileWriter = new FileWriter("DanhBa.csv")) {
                    // Ghi dòng tiêu đề
                    fileWriter.append("ID,Tên,Số điện thoại,Email\n");

                    // Ghi dữ liệu từng người dùng vào file
                    for (User user : users) {
                        fileWriter.append(user.getIdUser() + ",");
                        fileWriter.append(user.getUsername() + ",");
                        fileWriter.append(user.getPhone() + ",");
                        fileWriter.append(user.getEmail() + "\n");
                    }

                    JOptionPane.showMessageDialog(null, "Xuất file thành công!");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi ghi file: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Kết nối cơ sở dữ liệu thất bại!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
        }
    }
}
