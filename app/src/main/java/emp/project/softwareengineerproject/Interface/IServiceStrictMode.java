package emp.project.softwareengineerproject.Interface;

import android.os.StrictMode;

public interface IServiceStrictMode extends IDatabaseCredentials{
    /**
     * This permits the database transaction to be made
     */
    default void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }
}
