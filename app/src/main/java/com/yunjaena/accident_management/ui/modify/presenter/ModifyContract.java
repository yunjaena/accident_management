package com.yunjaena.accident_management.ui.modify.presenter;

import androidx.annotation.StringRes;

import com.yunjaena.core.contract.BaseView;

public interface ModifyContract {
    interface View extends BaseView<ModifyPresenter> {
        void showProgress(String message);

        void showProgress(@StringRes int message);

        void hideProgress();

        void showMessage(String message);

        void showMessage(@StringRes int message);

        void showSuccessSaved();
    }
}
