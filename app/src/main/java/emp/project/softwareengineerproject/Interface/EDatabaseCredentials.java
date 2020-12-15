package emp.project.softwareengineerproject.Interface;

public enum EDatabaseCredentials {

    DB_NAME("jdbc:mysql://192.168.1.152:3306/agt_db"),
    USER("admin"),
    PASS("admin");


    /*
    DB_NAME("jdbc:mysql://192.168.254.123:3306/agt_db"),
    USER("admin"),
    PASS("admin");

     */


    /*
        DB_NAME("jdbc:mysql://sstqpje3.epizy.com/epiz_27400849_agt_db"),
        USER("epiz_27400849"),
        PASS("uS5nIEIAEHuR");
     */


    private String db_cred;

    EDatabaseCredentials(String db_cred) {
        this.db_cred = db_cred;
    }

    public String getDatabaseCredentials() {
        return db_cred;
    }
}
