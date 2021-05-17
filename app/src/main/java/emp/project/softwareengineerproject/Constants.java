package emp.project.softwareengineerproject;
public class Constants {
    public enum Position {ADMINISTRATOR, EMPLOYEE}

    public static class Status {
        public enum NotificationStatus {
            DELETED_USER(ProductStatus.DELETED_USER.getProduct_status(), "Deleted User: "),
            UPDATE_USER(ProductStatus.UPDATED_USER.getProduct_status(), "Updated User: "),
            DELETED_PRODUCT(ProductStatus.DELETED_PRODUCT.getProduct_status(), "Deleted Product: "),
            UPDATED_PRODUCT(ProductStatus.UPDATED_PRODUCT.getProduct_status(), "Updated Product: "),
            ADDED_PRODUCT(ProductStatus.ADDED_PRODUCT.getProduct_status(), "Added Product: "),
            ADDED_SALES(ProductStatus.ADDED_SALES.getProduct_status(), "Added Sales: "),
            ADDED_NEW_USER(ProductStatus.ADDED_NEW_USER.getProduct_status(), "Added new User: "),
            ORDER_PENDING(ProductStatus.ORDER_PENDING.getProduct_status()),
            ORDER_FINISHED(ProductStatus.ORDER_FINISHED.getProduct_status()),
            ORDER_CANCEL(ProductStatus.ORDER_CANCEL.getProduct_status());

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

        public enum ProductStatus {
            DELETED_PRODUCT("Deleted product"),
            UPDATED_PRODUCT("Updated product"),
            ADDED_PRODUCT("Added product"),
            ADDED_SALES("Sold Item"),
            ADDED_NEW_USER("Added new User"),
            ORDER_PENDING("Order moved to pending"),
            ORDER_FINISHED("Finished Order"),
            ORDER_CANCEL("Order cancelled"),
            DELETED_USER("Deleted User"),
            UPDATED_USER("Updated User");

            private String product_status;

            ProductStatus(String product_status) {
                this.product_status = product_status;
            }

            public String getProduct_status() {
                return product_status;
            }
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
