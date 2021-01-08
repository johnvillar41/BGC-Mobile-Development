package emp.project.softwareengineerproject.Model.Bean;

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

    public UserModel(String user_id, String user_username, String user_password, String user_full_name, InputStream updateUserImage) {
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_full_name = user_full_name;
        this.uploadUserImage = updateUserImage;
    }

    public UserModel(String user_username, String user_password) {
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

    public UserModel validateAddUsers(TextInputLayout[] arrTexts, InputStream profileImage) {
        boolean isValid = true;
        String finalPassword = null;
        for (TextInputLayout textInputLayout : arrTexts) {
            if (textInputLayout.getEditText().getText().toString().isEmpty()) {
                textInputLayout.setError("Do not leave this empty!");
                isValid = false;
            } else {
                isValid = true;
                textInputLayout.setError(null);
            }
        }

        if (!arrTexts[1].getEditText().getText().toString().equals(arrTexts[2].getEditText().getText().toString()) || arrTexts[1].getEditText().getText().toString().isEmpty()) {
            arrTexts[1].setError("Passwords do not match!");
            arrTexts[2].setError("Passwords do not match!");
            isValid = false;
        } else {
            isValid = true;
            finalPassword = arrTexts[1].getEditText().getText().toString();
            arrTexts[1].setError(null);
            arrTexts[2].setError(null);
        }

        if (profileImage == null) {
            isValid = false;
        } else {
            isValid = true;
        }

        if (isValid) {
            return new UserModel(arrTexts[0].getEditText().getText().toString(), finalPassword, arrTexts[3].getEditText().getText().toString(), profileImage);
        } else {
            return null;
        }
    }

    public VALIDITY validateCredentials(UserModel model) {
        VALIDITY validity = null;
        if (model.getUser_username().isEmpty()) {
            validity = VALIDITY.EMPTY_USERNAME;
        } else if (model.getUser_password().isEmpty()) {
            validity = VALIDITY.EMPTY_PASSWORD;
        } else if (model.getUser_username().isEmpty() && model.getUser_password().isEmpty()) {
            validity = VALIDITY.EMPTY_BOTH;
        } else {
            validity = VALIDITY.VALID_LOGIN;
        }
        return validity;
    }

    public enum VALIDITY {
        EMPTY_USERNAME,
        EMPTY_PASSWORD,
        EMPTY_BOTH,
        VALID_LOGIN;
    }
}
