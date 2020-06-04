package com.yunjaena.accident_management.ui.resgister.presenter;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.Report;
import com.yunjaena.accident_management.data.network.interactor.ReportInteractor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter {
    public static final String TAG = "RegisterPresenter";
    private CompositeDisposable disposable;
    private RegisterContract.View registerView;
    private ReportInteractor reportInteractor;

    public RegisterPresenter(RegisterContract.View registerView, ReportInteractor reportInteractor) {
        disposable = new CompositeDisposable();
        this.registerView = registerView;
        this.reportInteractor = reportInteractor;
        registerView.setPresenter(this);
    }

    public void saveReport(Report report) {
        registerView.showProgress(R.string.uploading);
        Disposable reportDisposable = reportInteractor.saveReport(report)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                            if (aBoolean) {
                                registerView.showMessage(R.string.upload_complete);
                                registerView.hideProgress();
                                registerView.showSuccessSaved();
                            } else {
                                registerView.showMessage(R.string.upload_failed);
                                registerView.hideProgress();
                            }
                        }
                        ,error -> {
                            registerView.showMessage(R.string.upload_failed);
                            registerView.hideProgress();
                        }
                );
        disposable.add(reportDisposable);
    }

    public void releaseView() {
        disposable.clear();
    }
}
