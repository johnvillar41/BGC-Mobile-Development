package emp.project.softwareengineerproject;

public class Constants {
    public enum Position {ADMINISTRATOR, EMPLOYEE}

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
