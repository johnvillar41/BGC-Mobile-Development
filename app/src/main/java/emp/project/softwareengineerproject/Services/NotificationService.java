package emp.project.softwareengineerproject.Services;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.NotificationModel;

public class NotificationService implements INotification.INotificationService {

    private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
    private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
    private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();

    private NotificationModel model;

    public NotificationService(NotificationModel model) {
        this.model = model;
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
        return list;
    }
}
