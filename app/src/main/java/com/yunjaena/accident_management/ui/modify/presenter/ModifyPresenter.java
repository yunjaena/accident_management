package com.yunjaena.accident_management.ui.modify.presenter;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.Report;
import com.yunjaena.accident_management.data.network.interactor.ReportInteractor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ModifyPresenter {
    public static final String TAG = "ModifyPresenter";
    private CompositeDisposable disposable;
    private ModifyContract.View registerView;
    private ReportInteractor reportInteractor;

    public ModifyPresenter(ModifyContract.View registerView, ReportInteractor reportInteractor) {
        disposable = new CompositeDisposable();
        this.registerView = registerView;
        this.reportInteractor = reportInteractor;
        registerView.setPresenter(this);
    }

    public void updateReport(Report report) {
        registerView.showProgress(R.string.modifying);
        Disposable reportDisposable = reportInteractor.updateReport(report)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                            if (aBoolean) {
                                registerView.showMessage(R.string.modify_complete);
                                registerView.hideProgress();
                                registerView.showSuccessSaved();
                            } else {
                                registerView.showMessage(R.string.modify_fail);
                                registerView.hideProgress();
                            }
                        }
                        ,error -> {
                            registerView.showMessage(R.string.modify_fail);
                            registerView.hideProgress();
                        }
                );
        disposable.add(reportDisposable);
    }

    public void releaseView() {
        disposable.clear();
    }
}
