package com.yunjaena.accident_management.ui.retrieve;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.ReportSerial;
import com.yunjaena.accident_management.ui.resgister.ConstructType;
import com.yunjaena.accident_management.ui.resgister.DelayCause;
import com.yunjaena.accident_management.ui.retrieve.adapter.ReportImageAdapter;
import com.yunjaena.core.activity.ActivityBase;
import com.yunjaena.core.recyclerview.RecyclerViewClickListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RetrieveDetailActivity extends ActivityBase {
    private ReportSerial reportSerial;
    private TextView companyNameTextView;
    private TextView accidentDateTextView;
    private TextView realStartDayTextView;
    private TextView realEndDayTextView;
    private TextView constructionTypeTextView;
    private TextView constructionTypeSpecificTextView;
    private TextView reportDelayCauseOneTextView;
    private TextView reportDelayCauseOneSpecificTextView;
    private TextView reportDelayCauseTwoTextView;
    private TextView reportDelayCauseTwoSpecificTextView;
    private TextView savePathTextView;
    private RecyclerView cameraRecyclerView;
    private TextView designChangeAndErrorTextView;
    private TextView contractChangeAndViolationTextView;
    private TextView inevitableClauseTextView;
    private TextView concurrentOccurrenceTextView;
    private List<String> bitmapList;
    private LinearLayoutManager linearLayoutManager;
    private ReportImageAdapter reportImageAdapter;

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_detail);
        reportSerial = (ReportSerial) getIntent().getSerializableExtra(RetrieveActivity.REPORT);
        init();
    }

    public void init() {
        companyNameTextView = findViewById(R.id.report_detail_company_name_text_view);
        accidentDateTextView = findViewById(R.id.report_detail_accident_date_text_viewt);
        realStartDayTextView = findViewById(R.id.report_detail_real_start_date_text_view);
        realEndDayTextView = findViewById(R.id.report_detail_real_end_date_text_view);
        constructionTypeTextView = findViewById(R.id.report_detail_construction_type_text_view);
        constructionTypeSpecificTextView = findViewById(R.id.report_detail_construction_type_specific_text_view);
        reportDelayCauseOneTextView = findViewById(R.id.report_detail_delay_cause_one_text_view);
        reportDelayCauseOneSpecificTextView = findViewById(R.id.report_detail_delay_cause_specific_one_text_view);
        reportDelayCauseTwoTextView = findViewById(R.id.report_detail_delay_cause_two_text_view);
        reportDelayCauseTwoSpecificTextView = findViewById(R.id.report_detail_delay_cause_specific_two_text_view);
        savePathTextView = findViewById(R.id.report_detail_save_path_text_view);
        cameraRecyclerView = findViewById(R.id.report_detail_camera_recycler_view);
        designChangeAndErrorTextView = findViewById(R.id.report_detail_design_change_and_error_text_view);
        contractChangeAndViolationTextView = findViewById(R.id.report_detail_contract_change_and_violation_text_view);
        inevitableClauseTextView = findViewById(R.id.report_detail_inevitable_clause_text_view);
        concurrentOccurrenceTextView = findViewById(R.id.report_detail_concurrent_occurrence_text_view);
        bitmapList = new ArrayList<>();
        if (reportSerial.getImageFileArray() != null)
            bitmapList.addAll(reportSerial.getImageFileArray());
        updateUI();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        initRecyclerView();

    }

    public void updateUI() {
        companyNameTextView.setText(reportSerial.getCompanyName());
        accidentDateTextView.setText(reportSerial.getAccidentDate());
        realStartDayTextView.setText(reportSerial.getRealStartDate());
        realEndDayTextView.setText(reportSerial.getRealEndDate());
        constructionTypeTextView.setText(getConstructTypeText(reportSerial.getConstructType()));
        constructionTypeSpecificTextView.setText(getConstructDetailTypeText(reportSerial.getConstructType(), reportSerial.getConstructDetailType()));
        reportDelayCauseOneTextView.setText(getDelayCauseText(reportSerial.getDelayCauseOne()));
        reportDelayCauseOneSpecificTextView.setText(getDelayDetailCauseText(reportSerial.getDelayCauseOne(), reportSerial.getDelayCauseDetailOne()));
        reportDelayCauseTwoTextView.setText(getDelayCauseText(reportSerial.getDelayCauseTwo()));
        reportDelayCauseTwoSpecificTextView.setText((getDelayDetailCauseText(reportSerial.getDelayCauseTwo(), reportSerial.getDelayCauseDetailTwo())));
        savePathTextView.setText(getSaveText(reportSerial.getSavePath()));
        designChangeAndErrorTextView.setText(reportSerial.getDesignChangeAndError());
        contractChangeAndViolationTextView.setText(reportSerial.getContractChangeAndViolation());
        inevitableClauseTextView.setText(reportSerial.getInevitableClause());
        concurrentOccurrenceTextView.setText(reportSerial.getConcurrentOccurrence());

    }

    public void initRecyclerView() {
        cameraRecyclerView.setLayoutManager(linearLayoutManager);
        reportImageAdapter = new ReportImageAdapter(this, bitmapList);
        reportImageAdapter.setRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
        cameraRecyclerView.setAdapter(reportImageAdapter);
        reportImageAdapter.notifyDataSetChanged();
    }

    public String getConstructTypeText(int index) {
        if (index == -1)
            return getResources().getString(R.string.none);

        return getResources().getStringArray(R.array.construction_type)[index];
    }

    public String getConstructDetailTypeText(int index, int detailIndex) {
        if (index == -1 || detailIndex == -1)
            return getResources().getString(R.string.none);

        int arrayId = getResId(ConstructType.childArrayResourceId(index), R.array.class);
        return getResources().getStringArray(arrayId)[detailIndex];
    }

    public String getDelayCauseText(int index) {
        if (index == -1)
            return getResources().getString(R.string.none);

        return getResources().getStringArray(R.array.delay_cause)[index];
    }

    public String getDelayDetailCauseText(int index, int detailIndex) {
        if (index == -1 || detailIndex == -1)
            return getResources().getString(R.string.none);

        int arrayId = getResId(DelayCause.childArrayResourceId(index), R.array.class);
        return getResources().getStringArray(arrayId)[detailIndex];
    }

    public String getSaveText(int index) {
        if (index == -1)
            return getResources().getString(R.string.none);

        return getResources().getStringArray(R.array.save_path)[index];
    }
}
