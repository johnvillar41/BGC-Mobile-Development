package emp.project.softwareengineerproject.Model;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.sql.Blob;

public class UserModel {
    String user_id;
    String user_username;
    String user_password;
    String user_full_name;
    Blob user_image;
    InputStream uploadUserImage;

    public UserModel(String user_username, String user_password, String user_full_name, InputStream uploadUserImage) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_full_name = user_full_name;
        this.uploadUserImage = uploadUserImage;
    }

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

    public InputStream getUploadUserImage() {
        return uploadUserImage;
    }

    public UserModel validateAddUsers(TextInputLayout username, TextInputLayout password1, TextInputLayout password2, TextInputLayout realName, InputStream profileImage) {
        boolean isValid = false;
        String finalPassword = null;
        if (username.getEditText().getText().toString().isEmpty()) {
            username.setError("Do not leave this empty!");
            isValid = false;
        }
        if (password1.getEditText().getText().toString().isEmpty()) {
            password1.setError("Do not leave this empty!");
            isValid = false;
        }
        if (password2.getEditText().getText().toString().isEmpty()) {
            password2.setError("Do not leave this empty!");
            isValid = false;
        }
        if (!password1.getEditText().getText().toString().equals(password2.getEditText().getText().toString())) {
            password1.setError("Passwords do not match!");
            password2.setError("Passwords do not match!");
            isValid = false;
        }
        if (password1.getEditText().getText().toString().equals(password2.getEditText().getText().toString())) {
            finalPassword = password1.getEditText().getText().toString();
        }
        if (realName.getEditText().getText().toString().isEmpty()) {
            realName.setError("Do not leave this empty!");
            isValid = false;
        } else if (username.getError() == null &&
                password1.getError() == null &&
                password2.getError() == null &&
                realName.getError() == null) {
            isValid = true;
        }

        if (isValid) {
            return new UserModel(username.getEditText().getText().toString(), finalPassword, realName.getEditText().getText().toString(), profileImage);
        } else {
            return null;
        }
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
