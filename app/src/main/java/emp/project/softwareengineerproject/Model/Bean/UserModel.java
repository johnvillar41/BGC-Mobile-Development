package emp.project.softwareengineerproject.Model.Bean;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

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

    public List<VALIDITY> validateAddUsers(String[] arrTexts, InputStream profileImage) {
        boolean isValid = false;
        boolean isImageValid = false;

        boolean isFieldsValid_username = false;
        boolean isFieldsValid_password = false;
        boolean isFieldsValid_password_2 = false;
        boolean isFieldsValid_realName = false;

        List<VALIDITY> validity = new ArrayList<>();
        for (int i = 0; i < arrTexts.length; i++) {
            if (arrTexts[i].isEmpty()) {
                switch (i) {
                    case 0:
                        validity.add(VALIDITY.EMPTY_USERNAME);
                        break;
                    case 1:
                        validity.add(VALIDITY.EMPTY_PASSWORD);
                        break;
                    case 2:
                        validity.add(VALIDITY.EMPTY_PASSWORD_2);
                        break;
                    case 3:
                        validity.add(VALIDITY.EMPTY_REAL_NAME);
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        validity.add(VALIDITY.VALID_USERNAME);
                        isFieldsValid_username = true;
                        break;
                    case 1:
                        validity.add(VALIDITY.VALID_PASSWORD);
                        isFieldsValid_password = true;
                        break;
                    case 2:
                        validity.add(VALIDITY.VALID_PASSWORD_2);
                        isFieldsValid_password_2 = true;
                        break;
                    case 3:
                        validity.add(VALIDITY.VALID_REAL_NAME);
                        isFieldsValid_realName = true;
                        break;
                }
            }
        }

        if (arrTexts[1].equals(arrTexts[2]) && !arrTexts[1].isEmpty() && !arrTexts[2].isEmpty()) {
            validity.add(VALIDITY.EQUAL_PASSWORD);
            isValid = true;
        } else {
            validity.add(VALIDITY.PASSWORD_NOT_EQUAL);
            isValid = false;
        }

        if (profileImage == null) {
            validity.add(VALIDITY.EMPTY_PROFILE_PICTURE);
        } else {
            isImageValid = true;
        }

        if (isValid && isImageValid && isFieldsValid_username && isFieldsValid_password && isFieldsValid_password_2 && isFieldsValid_realName) {
            validity.add(VALIDITY.VALID_REGISTER);
        }

        return validity;
    }

    public VALIDITY validateLoginCredentials(UserModel model) {
        VALIDITY validity = null;
        if (model.getUser_username().isEmpty()) {
            validity = VALIDITY.EMPTY_USERNAME;
        }
        if (model.getUser_password().isEmpty()) {
            validity = VALIDITY.EMPTY_PASSWORD;
        }
        if (model.getUser_username().isEmpty() && model.getUser_password().isEmpty()) {
            validity = VALIDITY.EMPTY_BOTH;
        }
        if (!model.getUser_username().isEmpty() && !model.getUser_password().isEmpty()) {
            validity = VALIDITY.VALID_LOGIN;
        }
        return validity;
    }

    public List<VALIDITY> validateEditCredentials(UserModel model) {
        List<VALIDITY> validity = new ArrayList<>();
        if (model.getUser_username().isEmpty()) {
            validity.add(VALIDITY.EMPTY_USERNAME);
        }
        if (model.getUser_password().isEmpty()) {
            validity.add(VALIDITY.EMPTY_PASSWORD);
        }
        if (model.getUser_full_name().isEmpty()) {
            validity.add(VALIDITY.EMPTY_REAL_NAME);
        }
        if (!model.getUser_username().isEmpty() && !model.getUser_password().isEmpty() && !model.getUser_full_name().isEmpty()) {
            validity.add(VALIDITY.VALID_EDIT);
        }
        return validity;
    }


    public enum VALIDITY {
        EMPTY_USERNAME,
        EMPTY_PASSWORD,
        EMPTY_PASSWORD_2,
        EMPTY_BOTH,
        EMPTY_REAL_NAME,
        EMPTY_PROFILE_PICTURE,

        EQUAL_PASSWORD,
        PASSWORD_NOT_EQUAL,

        VALID_USERNAME,
        VALID_PASSWORD,
        VALID_PASSWORD_2,
        VALID_REAL_NAME,
        VALID_LOGIN,
        VALID_EDIT,
        VALID_REGISTER;
    }
}
