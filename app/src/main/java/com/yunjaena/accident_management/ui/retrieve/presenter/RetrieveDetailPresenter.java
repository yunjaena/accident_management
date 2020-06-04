package com.yunjaena.accident_management.ui.retrieve.presenter;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.interactor.ReportInteractor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RetrieveDetailPresenter {
    public static final String TAG = "RetrievePresenter";
    private RetrieveDetailContract.View retrieveDetailView;
    private ReportInteractor reportInteractor;
    private CompositeDisposable disposable;

    public RetrieveDetailPresenter(RetrieveDetailContract.View retrieveView, ReportInteractor reportInteractor) {
        this.retrieveDetailView = retrieveView;
        this.reportInteractor = reportInteractor;
        disposable = new CompositeDisposable();
        retrieveView.setPresenter(this);
    }


    public void deleteReport(String id){
        retrieveDetailView.showProgress(R.string.deleting);
        Disposable reportDisposable = reportInteractor.deleteReport(id).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reportList -> {
                            retrieveDetailView.hideProgress();
                            retrieveDetailView.showSuccessDelete();
                        }
                        , error -> {
                            retrieveDetailView.showMessage(R.string.deleting_fail);
                            retrieveDetailView.hideProgress();
                        });


        disposable.add(reportDisposable);
    }

    public void releaseView() {
        disposable.clear();
    }
}
