package emp.project.softwareengineerproject;

import emp.project.softwareengineerproject.View.NotificationView.NotificationRecyclerView;

public class Constants {
    public enum Position {ADMINISTRATOR, EMPLOYEE}

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

        public void setNotificationStatusToEmptyString() {
            this.notificationContent = "";
        }

        public String getNotificationTitle() {
            return notificationTitle;
        }
    }

    public static class LoginConstants {
        public static final String EMPTY_USERNAME = "Empty Username!";
        public static final String EMPTY_PASSWORD = "Empty Password!";
        public static final String EMPTY_BOTH = "Empty Both fields!";
        public static final String VALID_LOGIN = "Valid Login";
        public static final String SUCCESS_MESSAGE = "Login Successfull!";
        public static final String USER_NOT_FOUND = "User not found!";
    }

    public static class AddUserConstants {
        public static final String EMPTY_USERNAME_FIELD = "Empty Username Field!";
        public static final String EMPTY_PASSWORD_FIELD = "Empty Password Field!";
        public static final String EMPTY_PASSWORD_2_FIELD = "Empty Password 2 Field!";
        public static final String EMPTY_REAL_NAME_FIELD = "Empty Name Field!";
        public static final String EMPTY_PROFILE_PIC_FIELD = "Empty Picture!";
        private static final String PASSWORD_NOT_EQUAL = "Password field not equal!";
    }

}
