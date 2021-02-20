package emp.project.softwareengineerproject.Interface;

public interface IDatabaseCredentials {

    String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    enum DATABASE_CREDENTIALS {
        //Free Mysql Server
        DB_NAME("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12394153"),
        USER("sql12394153"),
        PASS("k7TngnBqxC");

        //Clever cloud server
        //DB_NAME("jdbc:mysql://bbqkcywrafsgjxlxecta-mysql.services.clever-cloud.com:3306/bbqkcywrafsgjxlxecta"),
        //USER("uf0mceexymqmcdl3"),
        //PASS("cx1yimaV0BM5yTw2IRgj");

        //LocalHost
        //DB_NAME("jdbc:mysql://192.168.1.3:3306/agt_db_relations"),
        //USER("admin"),
        //PASS("admin");

        private String db_cred;

        DATABASE_CREDENTIALS(String db_cred) {
            this.db_cred = db_cred;
        }

        public String getDatabaseCredentials() {
            return db_cred;
        }
    }
}
