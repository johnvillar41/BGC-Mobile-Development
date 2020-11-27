package emp.project.softwareengineerproject.Model;

import java.sql.Blob;

public class UserModel {
    String user_id;
    String user_username;
    String user_password;
    String user_full_name ;
    Blob user_image;

    public UserModel(String user_id, String user_username, String user_password, String user_full_name, Blob user_image) {
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_full_name = user_full_name;
        this.user_image = user_image;
    }


    public UserModel(String user_password, String user_username) {
        this.user_password = user_password;
        this.user_username = user_username;
    }

    public UserModel() {
    }

    public String getUser_username() {
        return user_username;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_id() {
        return user_id;
    }

    public Blob getUser_image() {
        return user_image;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public String validateCredentials(UserModel model) {
        String message = null;
        if (model.getUser_username().isEmpty()) {
            message = "Fill up username field!";
        }
        if (model.getUser_password().isEmpty()) {
            message = "Fill up password field!";
        }
        if (model.getUser_username().isEmpty() && model.getUser_password().isEmpty()) {
            message = "Fill up both fields!";
        }
        return message;
    }
}
