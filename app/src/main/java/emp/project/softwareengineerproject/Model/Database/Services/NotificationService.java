package emp.project.softwareengineerproject.Model.Database.Services;

import android.app.Notification;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class NotificationService implements INotification.INotificationService {
    private static NotificationService SINGLE_INSTANCE = null;

    private NotificationService() {

    }

    public static NotificationService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new NotificationService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
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
            NotificationModel model = new NotificationModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)
                    , resultSet.getString(4),
                    resultSet.getString(5));
            list.add(model);
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }

    @Override
    public void insertNewNotifications(NotificationModel notificationModel) throws SQLException, ClassNotFoundException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sqlNotification);
        preparedStatement.setString(1, notificationModel.getNotif_title());
        preparedStatement.setString(2, notificationModel.getNotif_content());
        preparedStatement.setString(3, notificationModel.getNotif_date());
        preparedStatement.setString(4, notificationModel.getUser_name());
        preparedStatement.execute();
        preparedStatement.close();
        preparedStatement.close();
    }
}
