package nhom7_qldanhba;

public class User {
    private int idUser;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String note;
    private String createdAt;
    private String avatar;

    public User(int idUser, String username, String email, String address, String phone, String note, String createdAt, String avatar) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
        this.phone = phone;        
        this.address = address;
        this.note = note;
        this.createdAt = createdAt;
        this.avatar = avatar;
    }

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getNote() { return note; }
    public String getAvatar() { return avatar; }
}
