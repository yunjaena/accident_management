package com.yunjaena.accident_management.ui.resgister.presenter;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.repository.entity.Report;
import com.yunjaena.accident_management.repository.source.ReportSource;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter {
    private CompositeDisposable disposable;
    private RegisterContract.View registerView;
    private ReportSource reportSource;

    public RegisterPresenter(RegisterContract.View registerView, ReportSource reportSource) {
        disposable = new CompositeDisposable();
        this.registerView = registerView;
        this.reportSource = reportSource;
        registerView.setPresenter(this);
    }

    public void saveReport(Report report) {
        registerView.showProgress(R.string.saving);
        disposable.add(
                Observable.just(reportSource.saveReport(report)).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(isSaved -> {
                                    if (isSaved)
                                        registerView.showSuccessSaved();
                                    else
                                        registerView.showMessage(R.string.saved_failed);
                                    registerView.hideProgress();
                                },
                                error -> {
                                    registerView.showMessage(R.string.saved_failed);
                                    registerView.hideProgress();
                                }
                        )

        );
    }

    public void releaseView() {
        disposable.clear();
    }
}
