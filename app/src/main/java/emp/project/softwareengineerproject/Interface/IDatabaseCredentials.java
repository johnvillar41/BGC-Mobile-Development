package emp.project.softwareengineerproject.Interface;

public interface IDatabaseCredentials {

    String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    enum DATABASE_CREDENTIALS {

        DB_NAME("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12384495"),
        USER("sql12384495"),
        PASS("2i1bIA2Vs8");

        private String db_cred;

        DATABASE_CREDENTIALS(String db_cred) {
            this.db_cred = db_cred;
        }

        public String getDatabaseCredentials() {
            return db_cred;
        }
    }
}