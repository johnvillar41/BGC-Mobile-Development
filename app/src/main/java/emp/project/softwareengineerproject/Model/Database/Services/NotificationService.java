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
import emp.project.softwareengineerproject.View.NotificationView.NotificationRecyclerView;

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
        connection.close();
    }

    public enum NotificationStatus {
        DELETED_USER(NotificationRecyclerView.PRODUCT_STATUS.DELETED_USER.getProduct_status(), "Deleted User: "),
        UPDATE_USER(NotificationRecyclerView.PRODUCT_STATUS.UPDATED_USER.getProduct_status(), "Updated User: "),
        DELETED_PRODUCT(NotificationRecyclerView.PRODUCT_STATUS.DELETED_PRODUCT.getProduct_status(), "Deleted Product: "),
        UPDATED_PRODUCT(NotificationRecyclerView.PRODUCT_STATUS.UPDATED_PRODUCT.getProduct_status(), "Updated Product: "),
        ADDED_PRODUCT(NotificationRecyclerView.PRODUCT_STATUS.ADDED_PRODUCT.getProduct_status(), "Added Product: "),
        ADDED_SALES(NotificationRecyclerView.PRODUCT_STATUS.ADDED_SALES.getProduct_status(), "Added Sales: "),
        ADDED_NEW_USER(NotificationRecyclerView.PRODUCT_STATUS.ADDED_NEW_USER.getProduct_status(), "Added new User: "),
        ORDER_PENDING(NotificationRecyclerView.PRODUCT_STATUS.ORDER_PENDING.getProduct_status()),
        ORDER_FINISHED(NotificationRecyclerView.PRODUCT_STATUS.ORDER_FINISHED.getProduct_status()),
        ORDER_CANCEL(NotificationRecyclerView.PRODUCT_STATUS.ORDER_CANCEL.getProduct_status());

        private String notificationContent;
        private String notificationTitle;

        NotificationStatus(String notificationTitle, String notificationContent) {
            this.notificationContent = notificationContent;
            this.notificationTitle = notificationTitle;
        }

        NotificationStatus(String notificationTitle) {
            this.notificationTitle = notificationTitle;
        }

        public String getNotificationContent() {
            return notificationContent;
        }

        public String getNotificationTitle() {
            return notificationTitle;
        }
    }

    public NotificationModel notificationFactory(String name, NotificationStatus notificationStatus) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        NotificationModel notificationModel = new NotificationModel(
                notificationStatus.getNotificationTitle(),
                notificationStatus.getNotificationContent() + name, String.valueOf(dtf.format(now)),
                MainMenuActivityView.GET_PREFERENCES_REALNAME);
        return notificationModel;
    }
}
