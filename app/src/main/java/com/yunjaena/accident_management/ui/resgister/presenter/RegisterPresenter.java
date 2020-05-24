package com.yunjaena.accident_management.ui.resgister.presenter;

import io.reactivex.disposables.CompositeDisposable;

public class RegisterPresenter {
    private CompositeDisposable disposable;
    private RegisterContract.View registerView;

    public RegisterPresenter(RegisterContract.View registerView) {
        disposable = new CompositeDisposable();
        this.registerView = registerView;
        registerView.setPresenter(this);
    }

    public void saveDate() {

    }

    public void releaseView() {
        disposable.clear();
    }
}
