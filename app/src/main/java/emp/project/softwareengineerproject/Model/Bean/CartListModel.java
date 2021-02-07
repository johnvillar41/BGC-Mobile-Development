package emp.project.softwareengineerproject.Model.Bean;

import java.util.ArrayList;
import java.util.List;

public class CartListModel {
    private static CartListModel instance = null;

    public List<InventoryModel> cartList;

    public static CartListModel getInstance() {
        if (instance == null) {
            instance = new CartListModel();
        }
        return instance;
    }

    private CartListModel() {
        cartList = new ArrayList<>();
    }


}
