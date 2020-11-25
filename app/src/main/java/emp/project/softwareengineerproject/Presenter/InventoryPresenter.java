package emp.project.softwareengineerproject.Presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.mysql.jdbc.Blob;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IInvetory;
import emp.project.softwareengineerproject.Model.ProductModel;

public class InventoryPresenter extends Activity implements IInvetory.IinventoryPresenter {
    private IInvetory.IinventoryView view;
    private ProductModel model;
    private IInvetory.DBhelper dBhelper;

    public InventoryPresenter(IInvetory.IinventoryView view) {
        this.view = view;
        this.model = new ProductModel();
        this.dBhelper = new DBhelper();
    }

    @Override
    public void getGreenHouseFromDB() throws SQLException, ClassNotFoundException {
        view.displayRecyclerViewGreenHouse(dBhelper.getGreenHouseDB());
    }

    private class DBhelper implements IInvetory.DBhelper {

        private String DB_NAME = "jdbc:mysql://192.168.1.152:3306/agt_db";
        private String USER = "admin";
        private String PASS = "admin";

        @Override
        public void strictMode() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public List<ProductModel> getGreenHouseDB() throws ClassNotFoundException, SQLException {
            strictMode();
            List<ProductModel> list = new ArrayList<>();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlcmd = "SELECT * FROM greenhouse_products";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlcmd);
            while (resultSet.next()) {
                model = new ProductModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4),
                        (Blob) resultSet.getBlob(5), resultSet.getInt(6));
                list.add(model);
            }
            statement.close();
            resultSet.close();
            connection.close();
            return list;
        }
    }
}
