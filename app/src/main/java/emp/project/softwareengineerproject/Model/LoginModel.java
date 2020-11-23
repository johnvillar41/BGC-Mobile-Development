package emp.project.softwareengineerproject.Model;

public class LoginModel {
    String user_id, user_username, user_password;

    public LoginModel(String user_username, String user_password) {
        this.user_username = user_username;
        this.user_password = user_password;
    }

    public LoginModel() {
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_username() {
        return user_username;
    }

    public String getUser_password() {
        return user_password;
    }

    public String validateCredentials(LoginModel model) {
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
