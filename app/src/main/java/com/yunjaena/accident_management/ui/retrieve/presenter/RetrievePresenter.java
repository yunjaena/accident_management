package com.yunjaena.accident_management.ui.retrieve.presenter;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.interactor.ReportInteractor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RetrievePresenter {
    public static final String TAG = "RetrievePresenter";
    private RetrieveContract.View retrieveView;
    private ReportInteractor reportInteractor;
    private CompositeDisposable disposable;

    public RetrievePresenter(RetrieveContract.View retrieveView, ReportInteractor reportInteractor) {
        this.retrieveView = retrieveView;
        this.reportInteractor = reportInteractor;
        disposable = new CompositeDisposable();
        retrieveView.setPresenter(this);
    }

    public void getReport() {
        retrieveView.showProgress(R.string.uploading);
        Disposable reportDisposable = reportInteractor.loadAllReport().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reportList -> {
                            retrieveView.hideProgress();
                            retrieveView.showReport(reportList);
                        }
                        , error -> {
                            retrieveView.showMessage(R.string.load_failed);
                            retrieveView.hideProgress();
                        });


        disposable.add(reportDisposable);
    }

    public void releaseView() {
        disposable.clear();
    }
}
