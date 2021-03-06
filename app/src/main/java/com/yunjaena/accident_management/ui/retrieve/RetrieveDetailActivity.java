package com.yunjaena.accident_management.ui.retrieve;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.ReportSerial;
import com.yunjaena.accident_management.data.network.interactor.ReportFirebaseInteractor;
import com.yunjaena.accident_management.ui.modify.ModifyActivity;
import com.yunjaena.accident_management.ui.resgister.ConstructType;
import com.yunjaena.accident_management.ui.resgister.DelayCause;
import com.yunjaena.accident_management.ui.retrieve.adapter.ReportImageAdapter;
import com.yunjaena.accident_management.ui.retrieve.presenter.RetrieveDetailContract;
import com.yunjaena.accident_management.ui.retrieve.presenter.RetrieveDetailPresenter;
import com.yunjaena.accident_management.util.DateUtil;
import com.yunjaena.accident_management.util.FileUtil;
import com.yunjaena.core.activity.ActivityBase;
import com.yunjaena.core.recyclerview.RecyclerViewClickListener;
import com.yunjaena.core.toast.ToastUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RetrieveDetailActivity extends ActivityBase implements RetrieveDetailContract.View {
    public static final int EXCEL_CREATE = 99;
    public static final int REPORT_DETAIL = 100;
    public static final int FILE_EXPORT_REQUEST_CODE = 101;
    private Workbook workbook;
    private ReportSerial reportSerial;
    private TextView companyNameTextView;
    private TextView accidentDateTextView;
    private TextView expectStartDayTextView;
    private TextView expectEndDayTextView;
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
    private TextView pictureDescribeTextView;
    private FrameLayout cameraFrameLayout;
    private List<String> bitmapList;
    private LinearLayoutManager linearLayoutManager;
    private ReportImageAdapter reportImageAdapter;
    private RetrieveDetailPresenter retrieveDetailPresenter;

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
        retrieveDetailPresenter = new RetrieveDetailPresenter(this, new ReportFirebaseInteractor());
        companyNameTextView = findViewById(R.id.report_detail_company_name_text_view);
        accidentDateTextView = findViewById(R.id.report_detail_accident_date_text_viewt);
        expectStartDayTextView = findViewById(R.id.report_detail_expect_start_date_text_view);
        expectEndDayTextView = findViewById(R.id.report_detail_expect_end_date_text_view);
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
        pictureDescribeTextView = findViewById(R.id.report_detail_picture_describe_text_view);
        cameraFrameLayout = findViewById(R.id.report_detail_camera_frame_layout);
        bitmapList = new ArrayList<>();
        if (reportSerial.getImageFileArray() != null && !reportSerial.getImageFileArray().isEmpty()) {
            bitmapList.addAll(reportSerial.getImageFileArray());
        } else {
            cameraFrameLayout.setVisibility(View.GONE);
        }

        updateUI();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        initRecyclerView();

    }

    public void updateUI() {
        companyNameTextView.setText(reportSerial.getCompanyName());
        accidentDateTextView.setText(reportSerial.getAccidentDate());
        expectStartDayTextView.setText(reportSerial.getExpectStartDate());
        expectEndDayTextView.setText(reportSerial.getExpectEndDate());
        realStartDayTextView.setText(reportSerial.getRealStartDate());
        realEndDayTextView.setText(reportSerial.getRealEndDate());
        constructionTypeTextView.setText(getConstructTypeText(reportSerial.getConstructType()));
        constructionTypeSpecificTextView.setText(getConstructDetailTypeText(reportSerial.getConstructType(), reportSerial.getConstructDetailType()));
        reportDelayCauseOneTextView.setText(getDelayCauseText(reportSerial.getDelayCauseOne()));
        reportDelayCauseOneSpecificTextView.setText(getDelayDetailCauseText(reportSerial.getDelayCauseOne(), reportSerial.getDelayCauseDetailOne()));
        reportDelayCauseTwoTextView.setText(getDelayCauseText(reportSerial.getDelayCauseTwo()));
        reportDelayCauseTwoSpecificTextView.setText((getDelayDetailCauseText(reportSerial.getDelayCauseTwo(), reportSerial.getDelayCauseDetailTwo())));
        savePathTextView.setText(getSaveText(reportSerial.getSavePath()));
        pictureDescribeTextView.setText(reportSerial.getPictureDescribe());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_modify:
                goToModify();
                break;
            case R.id.action_delete:
                showDeleteAlertDialog();
                break;
            case R.id.action_change:
                showSaveExcelFileDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToModify() {
        Intent intent = new Intent(this, ModifyActivity.class);
        intent.putExtra(RetrieveActivity.REPORT, reportSerial);
        startActivity(intent);
        Intent flagIntent = new Intent();
        flagIntent.putExtra("IS_DELETE", true);
        setResult(RESULT_OK, flagIntent);
        finish();
    }

    public void showDeleteAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete).setMessage(R.string.delete_confirm);
        builder.setPositiveButton(R.string.okay, (dialog, id) -> {
            retrieveDetailPresenter.deleteReport(reportSerial.getId());
        });
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        retrieveDetailPresenter.releaseView();
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
    public void showSuccessDelete() {
        Intent intent = new Intent();
        intent.putExtra("IS_DELETE", true);
        setResult(RESULT_OK, intent);
        ToastUtil.getInstance().makeShort(R.string.delete_success);
        finish();
    }

    @Override
    public void setPresenter(RetrieveDetailPresenter presenter) {
        this.retrieveDetailPresenter = presenter;
    }


    public void showSaveExcelFileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select).setMessage(R.string.save_file_excel);
        builder.setPositiveButton(R.string.okay, (dialog, id) -> {
            saveAtExcelFile();
        });

        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void saveAtExcelFile() {
        if (!isWritePermissionGranted()) {
            showPermissionDialog(EXCEL_CREATE);
            return;
        }
        saveExcelFileExecuteWithThread();
    }

    public void saveExcelFileExecuteWithThread() {
        showProgress(R.string.loading);
        new Thread(() -> {
            workbook = saveExcelFileExecute();
            runOnUiThread(() -> {
                        ToastUtil.getInstance().makeShort(R.string.save);
                        hideProgress();
                        saveWorkBookFile();
                    }

            );
        }).start();
    }

    public void saveWorkBookFile() {
        String fileName = "k-cm" + DateUtil.getCurrentDateWithDot() + ".xls";
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P)
            saveFile(fileName);
        else
            saveFileVersionQ(fileName);
    }


    public void saveFile(String fileName) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File xls = new File(dir, fileName);
        try {
            FileOutputStream os = new FileOutputStream(xls);
            workbook.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showEmailSendDialog(fileName);
    }

    public void saveFileVersionQ(String fileName) {
        Intent exportIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        exportIntent.addCategory(Intent.CATEGORY_OPENABLE);
        exportIntent.setType("application/excel");
        exportIntent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(exportIntent, FILE_EXPORT_REQUEST_CODE);
    }

    public void saveFileQ(Uri uri) {
        try {
            OutputStream os = getContentResolver().openOutputStream(uri);
            if (os != null) {
                workbook.write(os);
                os.close();
                showEmailSendDialogVersionQ(uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEmailSendDialog(String fileName) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select).setMessage(R.string.send_at_email);
        builder.setPositiveButton(R.string.okay, (dialog, id) -> {
            Uri uri = Uri.parse("mailto:");
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            it.putExtra(Intent.EXTRA_SUBJECT, "excel file 전송");
            it.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + dir + "/" + fileName));
            startActivity(it);
        });

        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showEmailSendDialogVersionQ(Uri fileUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select).setMessage(R.string.send_at_email);
        builder.setPositiveButton(R.string.okay, (dialog, id) -> {
            try {
                Uri uri = Uri.parse("mailto:");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra(Intent.EXTRA_SUBJECT, "excel file 전송");
                it.putExtra(Intent.EXTRA_STREAM, FileUtil.getFilePathFromUri(fileUri, getApplicationContext()));
                startActivity(it);
            } catch (IOException e) {
                ToastUtil.getInstance().makeShort(R.string.upload_failed);
            }
        });

        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public Workbook saveExcelFileExecute() {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("sheet1");
        Row row = sheet1.createRow(0);
        //Header
        Cell cell = row.createCell(0);
        cell.setCellValue(getResources().getString(R.string.excel_company_name));
        cell = row.createCell(1);
        cell.setCellValue(getResources().getString(R.string.excel_accident_date));
        cell = row.createCell(2);
        cell.setCellValue(getResources().getString(R.string.excel_construct_type));
        cell = row.createCell(3);
        cell.setCellValue(getResources().getString(R.string.excel_delay_cause_one));
        cell = row.createCell(4);
        cell.setCellValue(getResources().getString(R.string.excel_delay_cause_two));
        cell = row.createCell(5);
        cell.setCellValue(getResources().getString(R.string.excel_expect_start_date));
        cell = row.createCell(6);
        cell.setCellValue(getResources().getString(R.string.excel_expect_end_date));
        cell = row.createCell(7);
        cell.setCellValue(getResources().getString(R.string.excel_real_start_date));
        cell = row.createCell(8);
        cell.setCellValue(getResources().getString(R.string.excel_real_end_date));
        int rowIndex = 1;
        if (!reportSerial.isDelete()) {
            row = sheet1.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(reportSerial.getCompanyName());
            cell = row.createCell(1);
            cell.setCellValue(reportSerial.getAccidentDate());
            cell = row.createCell(2);
            cell.setCellValue(getConstructTypeText(reportSerial.getConstructType()));
            cell = row.createCell(3);
            cell.setCellValue(getDelayDetailCauseText(reportSerial.getDelayCauseOne(), reportSerial.getDelayCauseDetailOne()));
            cell = row.createCell(4);
            cell.setCellValue(getDelayDetailCauseText(reportSerial.getDelayCauseTwo(), reportSerial.getDelayCauseDetailTwo()));
            cell = row.createCell(5);
            cell.setCellValue(reportSerial.getRealStartDate());
            cell = row.createCell(6);
            cell.setCellValue(reportSerial.getRealEndDate());
            cell = row.createCell(7);
            cell.setCellValue(reportSerial.getRealStartDate());
            cell = row.createCell(8);
            cell.setCellValue(reportSerial.getRealEndDate());

        }
        return wb;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FILE_EXPORT_REQUEST_CODE && data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    saveFileQ(uri);
                }
            }
        }
    }

    public boolean isWritePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return permissionCheck != PackageManager.PERMISSION_DENIED;
    }

    public void showPermissionDialog(int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.need_write_permission_title).setMessage(R.string.need_write_permission);

        builder.setPositiveButton(R.string.okay, (dialog, id) -> {
            grantExternalStoragePermission(permission);
        });

        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
            ToastUtil.getInstance().makeShort(R.string.need_write_permission);
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean grantExternalStoragePermission(int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                switch (requestCode) {
                    case EXCEL_CREATE:
                        saveExcelFileExecuteWithThread();
                        break;
                }
            } else {
                ToastUtil.getInstance().makeShort(R.string.need_write_permission);
            }
        }
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
