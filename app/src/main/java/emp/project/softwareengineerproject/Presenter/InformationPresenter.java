package emp.project.softwareengineerproject.Presenter;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IInformation;
import emp.project.softwareengineerproject.Model.Bean.InformationModel;

public class InformationPresenter implements IInformation.IInformationPresenter {

    IInformation.IInformationView view;
    IInformation.IInformationService service;

    public InformationPresenter(IInformation.IInformationView view, IInformation.IInformationService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void loadData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    view.displayProgressBar();
                    List<InformationModel> informationModelList = service.fetchInformationDataFromDB();
                    view.displayRecyclerViewData(informationModelList);
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                view.hideProgressBar();
            }
        });thread.start();
    }

    @Override
    public void onFloatingActionButtonClickedPopup(String updatedInformation, String product_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayMessage("Saving...");
                try {
                    service.saveNewInformation(updatedInformation,product_id );
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                view.displayMessage("Saved!");
            }
        });thread.start();
    }
}
