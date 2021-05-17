package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.Bean.InformationModel;

public interface IInformation {
    interface IInformationView extends IBaseView {
        void displayRecyclerViewData(List<InformationModel> informationModelList);

        void displayMessage(String message);
    }

    interface IInformationPresenter extends IBasePresenter{
        void loadData();

        void onFloatingActionButtonClickedPopup(String updatedInformation, int product_id);
    }

    interface IInformationService extends IServiceStrictMode {
        List<InformationModel> fetchInformationDataFromDB() throws ClassNotFoundException, SQLException;

        void saveNewInformation(String updatedInformation, int product_id) throws ClassNotFoundException, SQLException;
    }
}
