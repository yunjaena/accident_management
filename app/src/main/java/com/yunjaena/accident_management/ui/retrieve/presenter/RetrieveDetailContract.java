package com.yunjaena.accident_management.ui.retrieve.presenter;

import androidx.annotation.StringRes;

import com.yunjaena.accident_management.data.network.entity.Report;
import com.yunjaena.core.contract.BaseView;

import java.util.List;

public interface RetrieveDetailContract {
    interface View extends BaseView<RetrieveDetailPresenter> {
        void showProgress(String message);

        void showProgress(@StringRes int message);

        void hideProgress();

        void showMessage(String message);

        void showMessage(@StringRes int message);

        void showSuccessDelete();
    }
}
