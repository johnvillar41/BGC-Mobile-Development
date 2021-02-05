package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.Bean.InformationModel;

public interface IInformation {
    interface IInformationView {
        void displayProgressBar();

        void hideProgressBar();

        void displayRecyclerViewData(List<InformationModel> informationModelList);

        void displayMessage(String message);
    }

    interface IInformationPresenter {
        void loadData();

        void onFloatingActionButtonClickedPopup(String updatedInformation,String product_id);
    }

    interface IInformationService extends IServiceStrictMode{
        List<InformationModel> fetchInformationDataFromDB() throws ClassNotFoundException, SQLException;

        void saveNewInformation(String updatedInformation,String product_id) throws ClassNotFoundException, SQLException;
    }
}
