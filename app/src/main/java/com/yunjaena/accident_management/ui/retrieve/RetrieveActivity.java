package com.yunjaena.accident_management.ui.retrieve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.Report;
import com.yunjaena.accident_management.data.network.entity.ReportSerial;
import com.yunjaena.accident_management.data.network.interactor.ReportFirebaseInteractor;
import com.yunjaena.accident_management.ui.retrieve.adapter.ReportAdapter;
import com.yunjaena.accident_management.ui.retrieve.presenter.RetrieveContract;
import com.yunjaena.accident_management.ui.retrieve.presenter.RetrievePresenter;
import com.yunjaena.core.activity.ActivityBase;
import com.yunjaena.core.recyclerview.RecyclerViewClickListener;
import com.yunjaena.core.toast.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class RetrieveActivity extends ActivityBase implements RetrieveContract.View {
    public static final String TAG = "RetrieveActivity";
    public static final String REPORT = "REPORT";
    public static final int REPORT_DETAIL = 100;
    private RetrievePresenter retrievePresenter;
    private Spinner savePathSpinner;
    private RecyclerView retrieveRecyclerView;
    private int selectPosition;
    private List<Report> allReportItem;
    private List<Report> selectReportItem;
    private ReportAdapter reportAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        init();
        retrievePresenter.getReport();
    }

    public void init() {
        selectPosition = 0;
        retrievePresenter = new RetrievePresenter(this, new ReportFirebaseInteractor());
        allReportItem = new ArrayList<>();
        selectReportItem = new ArrayList<>();
        retrieveRecyclerView = findViewById(R.id.retrieve_recycler_view);
        savePathSpinner = findViewById(R.id.retrieve_save_path_spinner);
        linearLayoutManager = new LinearLayoutManager(this);
        retrieveRecyclerView.setLayoutManager(linearLayoutManager);
        reportAdapter = new ReportAdapter(this, selectReportItem);
        reportAdapter.setRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                selectPosition = position;
                itemSelect(selectPosition);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
        retrieveRecyclerView.setAdapter(reportAdapter);

        savePathSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateRecyclerView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void itemSelect(int position) {
        Intent intent = new Intent(this, RetrieveDetailActivity.class);
        intent.putExtra(REPORT, new ReportSerial(selectReportItem.get(position)));
        startActivityForResult(intent, REPORT_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REPORT_DETAIL && data != null) {
                    if(data.getBooleanExtra("IS_DELETE",false)){
                        retrievePresenter.getReport();
                    }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        retrievePresenter.releaseView();
    }

    @Override
    public void showProgress(String message) {
        showProgressDialog(message);
    }

    @Override
    public void showProgress(int message) {
        showProgressDialog(message);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.getInstance().makeShort(message);
    }

    @Override
    public void showMessage(int message) {
        ToastUtil.getInstance().makeShort(message);
    }

    @Override
    public void showReport(List<Report> reportList) {
        allReportItem.clear();
        if (reportList != null) {
            allReportItem.addAll(reportList);
            updateRecyclerView(selectPosition);
        }
    }

    @Override
    public void setPresenter(RetrievePresenter presenter) {
        this.retrievePresenter = presenter;
    }

    public void updateRecyclerView(int savePathPosition) {
        selectReportItem.clear();
        showProgress(R.string.loading);
        new Thread(() -> {
            for (Report report : allReportItem) {
                if (report.getSavePath() == savePathPosition && !report.isDelete()) {
                    selectReportItem.add(report);
                }
            }
            runOnUiThread(() -> {
                        reportAdapter.notifyDataSetChanged();
                        hideProgress();
                    }

            );
        }).start();
    }
}
