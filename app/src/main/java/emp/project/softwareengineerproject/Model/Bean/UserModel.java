package emp.project.softwareengineerproject.Model.Bean;

import java.sql.Blob;

import emp.project.softwareengineerproject.Constants;

public class UserModel {
    private int userID;
    private String username;
    private String password;
    private String fullName;
    private Blob userPicture;
    private Constants.Position position;

    public UserModel(int userID, String username, String password, String fullName, Blob userPicture, Constants.Position position) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.userPicture = userPicture;
        this.position = position;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public Blob getUserPicture() {
        return userPicture;
    }

    public Constants.Position getPosition() {
        return position;
    }
}
