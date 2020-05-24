package com.yunjaena.accident_management.ui.resgister.presenter;

import androidx.annotation.StringRes;

import com.yunjaena.core.contract.BaseView;

public interface RegisterContract {
    interface View extends BaseView<RegisterPresenter> {
        void showProgress(String message);

        void showProgress(@StringRes int message);

        void hideProgress();

        void showMessage(String message);

        void showMessage(@StringRes int message);
    }
}
