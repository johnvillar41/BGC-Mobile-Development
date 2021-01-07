package emp.project.softwareengineerproject.Interface;

public interface IServiceStrictMode {

    String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    /**
     * This permits the database transaction to be made
     * */
    void strictMode() throws ClassNotFoundException;
}
