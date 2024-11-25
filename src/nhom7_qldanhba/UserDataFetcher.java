package nhom7_qldanhba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataFetcher {
    public static List<User> fetchData(Connection connection) {
        String query = "SELECT * FROM user";
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idUser = resultSet.getInt("idUser");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String note = resultSet.getString("note");
                String createdAt = resultSet.getString("created_at");
                byte[] avatar = resultSet.getBytes("avatar");
                boolean isBlock = resultSet.getBoolean("isBlock");

                // Thêm đối tượng User vào danh sách
                users.add(new User(idUser, username,address, email, phone, note, createdAt, avatar, isBlock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}