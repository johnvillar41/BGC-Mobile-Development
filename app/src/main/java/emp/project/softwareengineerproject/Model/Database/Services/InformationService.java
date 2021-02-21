package emp.project.softwareengineerproject.Model.Database.Services;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IInformation;
import emp.project.softwareengineerproject.Model.Bean.InformationModel;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;

public class InformationService implements IInformation.IInformationService {

    private static InformationService instance = null;

    public static InformationService getInstance() {
        if (instance == null) {
            instance = new InformationService();
        }
        return instance;
    }

    private InformationService() {

    }

    @Override
    public List<InformationModel> fetchInformationDataFromDB() throws ClassNotFoundException, SQLException {
        strictMode();
        List<InformationModel> informationModelList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetInformation = "SELECT * FROM information_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetInformation);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            InventoryModel inventoryModel = null;
            String product_id = resultSet.getString("product_id");
            String product_information = resultSet.getString("product_information");
            String sqlGetProducts = "SELECT * FROM products_table WHERE product_id=?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlGetProducts);
            preparedStatement1.setString(1, product_id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                inventoryModel = new InventoryModel(
                        resultSet1.getString("product_id"),
                        resultSet1.getString("product_name"),
                        resultSet1.getString("product_description"),
                        resultSet1.getLong("product_price"),
                        (Blob) resultSet1.getBlob("product_picture"),
                        resultSet1.getInt("product_stocks"),
                        resultSet1.getString("product_category")
                );
            }
            InformationModel informationModel = new InformationModel(product_information, inventoryModel);
            informationModelList.add(informationModel);
        }
        return informationModelList;
    }

    @Override
    public void saveNewInformation(String updatedInformation, String product_id) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlUpdateInformation = "UPDATE information_table SET product_information = ? WHERE product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateInformation);
        preparedStatement.setString(1, updatedInformation);
        preparedStatement.setString(2, product_id);
        preparedStatement.execute();
    }

    public void insertNewInformation(Connection connection) throws SQLException, ClassNotFoundException {
        String insertProductIdToInformationTable = "INSERT INTO information_table(product_id,product_information) VALUES(?,?)";
        com.mysql.jdbc.PreparedStatement preparedStatement1 = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(insertProductIdToInformationTable);
        preparedStatement1.setString(1, getHighestIdFromInventoryTable());
        preparedStatement1.setString(2, "No information yet");
        preparedStatement1.execute();
        preparedStatement1.close();
    }

    private String getHighestIdFromInventoryTable() throws ClassNotFoundException, SQLException {
        strictMode();
        int highestNUm = 0;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlget = "SELECT product_id FROM products_table";
        com.mysql.jdbc.PreparedStatement preparedStatement = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(sqlget);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getInt(1) > highestNUm) {
                highestNUm = resultSet.getInt(1);
            }
        }
        connection.close();
        preparedStatement.close();
        return String.valueOf(highestNUm);
    }
}
