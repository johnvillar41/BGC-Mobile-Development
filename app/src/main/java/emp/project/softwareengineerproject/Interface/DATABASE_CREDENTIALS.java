package emp.project.softwareengineerproject.Interface;

public enum DATABASE_CREDENTIALS {

    DB_NAME("jdbc:mysql://192.168.1.152:3306/agt_db"),
    USER("admin"),
    PASS("admin");

    private String db_cred;

    DATABASE_CREDENTIALS(String db_cred) {
        this.db_cred = db_cred;
    }

    public String getDatabaseCredentials() {
        return db_cred;
    }
}
