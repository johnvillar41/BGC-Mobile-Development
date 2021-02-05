package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.Bean.InformationModel;

public interface IInformation {
    interface IInformationView {
        void displayProgressBar();

        void hideProgressBar();

        void displayRecyclerViewData(List<InformationModel> informationModelList);
    }

    interface IInformationPresenter {
        void loadData();
    }

    interface IInformationService extends IServiceStrictMode{
        List<InformationModel> fetchInformationDataFromDB() throws ClassNotFoundException, SQLException;
    }
}
