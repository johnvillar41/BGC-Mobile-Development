package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.Build;
import android.os.StrictMode;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class SalesAddPresenter implements ISalesAdd.ISalesAddPresenter {

    ISalesAdd.ISalesAddView view;
    ISalesAdd.ISalesAddService service;
    SalesModel model;

    public SalesAddPresenter(ISalesAdd.ISalesAddView view) {
        this.view = view;
        this.model = new SalesModel();
        this.service = new SalesAddService();
    }

    @Override
    public void onCartButtonClicked(List<InventoryModel> cartList) {
        view.displayCart(cartList);
    }

    @Override
    public void directProductList() throws SQLException, ClassNotFoundException {
        view.displayProductRecyclerView(service.getProductListFromDB());
    }

    @Override
    public void onConfirmButtonClicked(View v) {
        for (int i = 0; i < SalesModel.cartList.size(); i++) {
            model = new SalesModel(SalesModel.cartList.get(i).getProduct_picture(), SalesModel.cartList.get(i).getProduct_name(),
                    SalesModel.cartList.get(i).getNewPrice(), SalesModel.cartList.get(i).getProduct_id(), SalesModel.cartList.get(i).getTotal_number_of_products());
            try {
                if(service.insertOrderToDB(model)){
                    view.displaySuccessfullPrompt();
                } else {
                    view.displayOnErrorMessage("Number of products not enough",v);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private class SalesAddService implements ISalesAdd.ISalesAddService {

        private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
        private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
        private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();

        @Override
        public void strictMode() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean insertOrderToDB(SalesModel model) throws SQLException, ClassNotFoundException {
            strictMode();
            if(checkIfProductIsEnough(model.getProduct_id(),model.getTotal_number_of_products())){
                String sql = "INSERT INTO sales_table(sales_title,sales_image,sales_transaction_value,product_id,total_number_of_products) VALUES(?,?,?,?,?)";
                Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, model.getSales_title());
                preparedStatement.setBlob(2, model.getSales_image());
                preparedStatement.setLong(3, model.getProduct_total());
                preparedStatement.setString(4, model.getProduct_id());
                preparedStatement.setString(5, model.getTotal_number_of_products());
                preparedStatement.execute();

                //Update Products
                String sqlMinusStocks = "UPDATE products_table SET product_stocks=product_stocks-" + "'" + model.getTotal_number_of_products() + "' WHERE product_id=" + "'" +
                        model.getProduct_id() + "'";
                Statement statement = connection.createStatement();
                statement.execute(sqlMinusStocks);


                //Update Notifications
                String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                NotificationModel notificationModel;
                notificationModel = new NotificationModel("Added sales", "Added sales " + model.getSales_title(), String.valueOf(dtf.format(now)),
                        MainMenuActivityView.GET_PREFERENCES_REALNAME);
                com.mysql.jdbc.PreparedStatement preparedStatement2 = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(sqlNotification);
                preparedStatement2.setString(1, notificationModel.getNotif_title());
                preparedStatement2.setString(2, notificationModel.getNotif_content());
                preparedStatement2.setString(3, notificationModel.getNotif_date());
                preparedStatement2.setString(4, notificationModel.getUser_name());
                preparedStatement2.execute();
                preparedStatement2.close();
                return true;
            } else {
                return false;
            }


        }

        @Override
        public List<InventoryModel> getProductListFromDB() throws ClassNotFoundException, SQLException {
            strictMode();
            InventoryModel model;
            List<InventoryModel> list = new ArrayList<>();
            String sql = "SELECT * FROM products_table";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                model = new InventoryModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4),
                        (Blob) resultSet.getBlob(5),
                        resultSet.getInt(6), resultSet.getString(7));
                list.add(model);
            }
            return list;
        }

        @Override
        public boolean checkIfProductIsEnough(String product_id, String total_orders) throws ClassNotFoundException, SQLException {
            strictMode();
            boolean isValid = false;
            String sqlSelect = "SELECT product_stocks FROM products_table WHERE product_id=" + "'" + product_id + "'";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                if (Integer.parseInt(resultSet.getString(1)) < Integer.parseInt(total_orders)) {
                    isValid = false;
                } else {
                    isValid = true;
                }
            }
            return isValid;
        }


    }
}
