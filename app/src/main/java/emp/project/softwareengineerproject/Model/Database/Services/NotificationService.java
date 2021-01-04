package emp.project.softwareengineerproject.Model.Database.Services;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;

public class NotificationService implements INotification.INotificationService {

    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    private NotificationModel model;
    private static NotificationService SINGLE_INSTANCE = null;

    private NotificationService(NotificationModel model) {
        this.model = model;
    }

    public static NotificationService getInstance(NotificationModel model) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new NotificationService(model);
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
    }

    @Override
    public void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    public List<NotificationModel> fetchNotifsFromDB(String date_today) throws SQLException, ClassNotFoundException {
        strictMode();
        List<NotificationModel> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM notifications_table WHERE notif_date LIKE " + "'" + date_today + "%'";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            model = new NotificationModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)
                    , resultSet.getString(4),
                    resultSet.getString(5));
            list.add(model);
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }
}
