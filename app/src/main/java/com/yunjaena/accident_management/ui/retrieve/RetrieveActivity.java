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
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.Report;
import com.yunjaena.accident_management.data.network.entity.ReportSerial;
import com.yunjaena.accident_management.data.network.interactor.ReportFirebaseInteractor;
import com.yunjaena.accident_management.ui.resgister.ConstructType;
import com.yunjaena.accident_management.ui.resgister.DelayCause;
import com.yunjaena.accident_management.ui.retrieve.adapter.ReportAdapter;
import com.yunjaena.accident_management.ui.retrieve.presenter.RetrieveContract;
import com.yunjaena.accident_management.ui.retrieve.presenter.RetrievePresenter;
import com.yunjaena.accident_management.util.DateUtil;
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

public class RetrieveActivity extends ActivityBase implements RetrieveContract.View {
    public static final String TAG = "RetrieveActivity";
    public static final String REPORT = "REPORT";
    public static final int EXCEL_CREATE = 99;
    public static final int REPORT_DETAIL = 100;
    public static final int FILE_EXPORT_REQUEST_CODE = 101;
    private RetrievePresenter retrievePresenter;
    private Spinner savePathSpinner;
    private RecyclerView retrieveRecyclerView;
    private int selectPosition;
    private List<Report> allReportItem;
    private List<Report> selectReportItem;
    private ReportAdapter reportAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Workbook workbook;

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
                selectPosition = position;
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
                if (data.getBooleanExtra("IS_DELETE", false)) {
                    retrievePresenter.getReport();
                }
            } else if (requestCode == FILE_EXPORT_REQUEST_CODE && data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    saveFileQ(uri);
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
        for (Report report : allReportItem) {
            if (report.getSavePath() == savePathPosition && !report.isDelete()) {
                selectReportItem.add(report);
            }
        }
        reportAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change:
                showSaveExcelFileDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P)
            saveFile();
        else
            saveFileVersionQ();
    }


    public void saveFile() {
        String fileName = "k-cm" + DateUtil.getCurrentDateWithOutTimeWithOutDot() + ".xls";
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

    public void saveFileVersionQ() {
        String fileName = "k-cm" + DateUtil.getCurrentDateWithOutTimeWithOutDot() + ".xls";
        Intent exportIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        exportIntent.addCategory(Intent.CATEGORY_OPENABLE);
        exportIntent.setType("text/csv");
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
            Uri uri = Uri.parse("mailto:");
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            it.putExtra(Intent.EXTRA_SUBJECT, "excel file 전송");
            it.putExtra(Intent.EXTRA_STREAM, fileUri);
            startActivity(it);
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
        cell.setCellValue(getResources().getString(R.string.excel_real_start_date));
        cell = row.createCell(6);
        cell.setCellValue(getResources().getString(R.string.excel_real_end_date));
        int rowIndex = 1;
        for (Report report : allReportItem) {
            if (!report.isDelete()) {
                row = sheet1.createRow(rowIndex++);
                cell = row.createCell(0);
                cell.setCellValue(report.getCompanyName());
                cell = row.createCell(1);
                cell.setCellValue(report.getAccidentDate());
                cell = row.createCell(2);
                cell.setCellValue(getConstructTypeText(report.getConstructType()));
                cell = row.createCell(3);
                cell.setCellValue(getDelayDetailCauseText(report.getDelayCauseOne(), report.getDelayCauseDetailOne()));
                cell = row.createCell(4);
                cell.setCellValue(getDelayDetailCauseText(report.getDelayCauseTwo(), report.getDelayCauseDetailTwo()));
                cell = row.createCell(5);
                cell.setCellValue(report.getRealStartDate());
                cell = row.createCell(6);
                cell.setCellValue(report.getRealEndDate());
            }
        }
        return wb;
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
