package com.yunjaena.accident_management.ui.resgister;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.Report;
import com.yunjaena.accident_management.data.network.entity.interactor.ReportSaveFirebaseInteractor;
import com.yunjaena.accident_management.ui.resgister.adapter.RegisterImageAdapter;
import com.yunjaena.accident_management.ui.resgister.presenter.RegisterContract;
import com.yunjaena.accident_management.ui.resgister.presenter.RegisterPresenter;
import com.yunjaena.accident_management.util.DateUtil;
import com.yunjaena.core.activity.ActivityBase;
import com.yunjaena.core.recyclerview.RecyclerViewClickListener;
import com.yunjaena.core.toast.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends ActivityBase implements RegisterContract.View, View.OnClickListener {
    public static final String TAG = "RegisterActivity";
    public static final int MAX_IMAGE_SELECT = 10;
    public static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_IMAGE_REQUEST = 2;
    private Context context;
    private String currentPhotoPath;
    private RegisterPresenter registerPresenter;
    private LinearLayout registerConstructionTypeLinearLayout;
    private LinearLayout registerConstructionTypeSpecificLinearLayout;
    private LinearLayout delayCauseOneSpecificLinearLayout;
    private LinearLayout delayCauseTwoSpecificLinearLayout;
    private EditText companyNameEditText;
    private TextView accidentDateTextView;
    private Spinner constructionTypeSpinner;
    private Spinner constructionTypeSpecificSpinner;
    private Spinner registerDelayCauseOneSpinner;
    private Spinner registerDelayCauseOneSpecificSpinner;
    private Spinner registerDelayCauseTwoSpinner;
    private Spinner registerDelayCauseTwoSpecificSpinner;
    private TextView savePathTextView;
    private RecyclerView cameraRecyclerView;
    private LinearLayout cameraLinearLayout;
    private ImageView cameraImageView;
    private File currentPhotoFile;
    private EditText designChangeAndErrorEditText;
    private EditText contractChangeAndViolationEditText;
    private EditText inevitableClauseEditText;
    private EditText concurrentOccurrenceEditText;
    private List<Bitmap> bitmapList;
    private RegisterImageAdapter registerImageAdapter;
    private LinearLayoutManager linearLayoutManager;

    private String selectDate;
    private int constructionType;
    private int constructionTypeSpecific;
    private int delayCause1;
    private int delayCauseSpecific1;
    private int delayCause2;
    private int delayCauseSpecific2;
    private int savePath;

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
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        context = this;
        selectDate = DateUtil.getCurrentDateWithOutTime();
        constructionType = 0;
        constructionTypeSpecific = -1;
        delayCause1 = 0;
        delayCauseSpecific1 = 0;
        delayCause2 = -1;
        delayCauseSpecific2 = -1;
        savePath = -1;

        registerPresenter = new RegisterPresenter(this, new ReportSaveFirebaseInteractor());
        initView();
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.register);


        updateUI();

    }

    public void initView() {
        registerConstructionTypeSpecificLinearLayout = findViewById(R.id.register_construction_type_specific_linear_layout);
        registerConstructionTypeLinearLayout = findViewById(R.id.register_construction_type_linear_layout);
        constructionTypeSpecificSpinner = findViewById(R.id.register_construction_type_specific_spinner);
        companyNameEditText = findViewById(R.id.register_company_name_edit_text);
        accidentDateTextView = findViewById(R.id.register_accident_date_text_view);
        constructionTypeSpinner = findViewById(R.id.register_construction_type_spinner);
        registerDelayCauseOneSpinner = findViewById(R.id.register_delay_cause_one_spinner);
        registerDelayCauseOneSpecificSpinner = findViewById(R.id.register_delay_cause_specific_one_spinner);
        registerDelayCauseTwoSpinner = findViewById(R.id.register_delay_cause_two_spinner);
        registerDelayCauseTwoSpecificSpinner = findViewById(R.id.register_delay_cause_specific_two_spinner);
        savePathTextView = findViewById(R.id.register_save_path_text_view);
        designChangeAndErrorEditText = findViewById(R.id.register_design_change_and_error_edit_text);
        contractChangeAndViolationEditText = findViewById(R.id.register_contract_change_and_violation_edit_text);
        inevitableClauseEditText = findViewById(R.id.register_inevitable_clause_edit_text);
        concurrentOccurrenceEditText = findViewById(R.id.register_concurrent_occurrence_edit_text);
        cameraRecyclerView = findViewById(R.id.register_camera_recycler_view);
        cameraLinearLayout = findViewById(R.id.register_camera_linear_layout);
        cameraImageView = findViewById(R.id.register_camera_image_view);
        delayCauseOneSpecificLinearLayout = findViewById(R.id.register_delay_cause_specific_one_linear_layout);
        delayCauseTwoSpecificLinearLayout = findViewById(R.id.register_delay_cause_specific_two_linear_layout);
        setEditTextGravityStartWhenLengthOverZero(designChangeAndErrorEditText);
        setEditTextGravityStartWhenLengthOverZero(contractChangeAndViolationEditText);
        setEditTextGravityStartWhenLengthOverZero(inevitableClauseEditText);
        setEditTextGravityStartWhenLengthOverZero(concurrentOccurrenceEditText);
        bitmapList = new ArrayList<>();
        cameraLinearLayout.setOnClickListener(this);
        accidentDateTextView.setOnClickListener(this);
        setCameraRecyclerView();

        /*Set Spinner*/
        setConstructionTypeSpinner();
        setRegisterDelayCauseOneSpinner();
        setRegisterDelayCauseTwoSpinner();
    }

    public void setCameraRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        registerImageAdapter = new RegisterImageAdapter(this, bitmapList);
        registerImageAdapter.setRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position >= bitmapList.size()) {
                    showCameraOrGallerySelectDialog();
                    return;
                }
                showImageDeleteDialog(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
        cameraRecyclerView.setLayoutManager(linearLayoutManager);
        cameraRecyclerView.setAdapter(registerImageAdapter);
        registerImageAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void save() {
        String companyName = companyNameEditText.getText().toString().trim();
        String accidentDate = accidentDateTextView.getText().toString().trim();
        int constructType = this.constructionType;
        int constructDetailType = this.constructionTypeSpecific;
        int delayCauseOne = this.delayCause1;
        int delayCauseDetailOne = this.delayCauseSpecific1;
        int delayCauseTwo = this.delayCause2;
        int delayCauseDetailTwo = this.delayCauseSpecific2;
        int savePath = this.savePath;
        String designChangeAndError = designChangeAndErrorEditText.getText().toString().trim();
        String contractChangeAndViolation = contractChangeAndViolationEditText.getText().toString().trim();
        String inevitableClause = inevitableClauseEditText.getText().toString().trim();
        String concurrentOccurrence = concurrentOccurrenceEditText.getText().toString().trim();

        if (companyName.isEmpty() || accidentDate.isEmpty() || savePath == -1 || designChangeAndError.isEmpty() || contractChangeAndViolation.isEmpty() ||
                inevitableClause.isEmpty() || concurrentOccurrence.isEmpty()
        ) {
            ToastUtil.getInstance().makeShort(R.string.input_all_waring);
            return;
        }

        if (bitmapList.size() < 1) {
            ToastUtil.getInstance().makeShort(R.string.input_picture_waring);
            return;
        }

        Report.Builder builder = new Report.Builder();
        builder.companyName(companyName);
        builder.accidentDate(accidentDate);
        builder.constructType(constructType);
        builder.constructDetailType(constructDetailType);
        builder.delayCauseOne(delayCauseOne);
        builder.delayCauseDetailOne(delayCauseDetailOne);
        builder.delayCauseTwo(delayCauseTwo);
        builder.delayCauseDetailTwo(delayCauseDetailTwo);
        builder.savePath(savePath);
        builder.designChangeAndError(designChangeAndError);
        builder.contractChangeAndViolation(contractChangeAndViolation);
        builder.inevitableClause(inevitableClause);
        builder.concurrentOccurrence(concurrentOccurrence);
        builder.isDelete(false);
        builder.imageBitmap(bitmapList);
        registerPresenter.saveReport(builder.build());

    }

    public void setEditTextGravityStartWhenLengthOverZero(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    editText.setGravity(Gravity.START | Gravity.TOP);
                } else {
                    editText.setGravity(Gravity.CENTER);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.releaseView();
    }

    @Override
    public void showProgress(String message) {
        showProgressDialog(message);
    }

    @Override
    public void showProgress(@StringRes int message) {
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
    public void setPresenter(RegisterPresenter presenter) {
        this.registerPresenter = presenter;
    }

    @Override
    public void showSuccessSaved() {

    }

    public void showCameraOrGallerySelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select).setMessage(R.string.select_gallery_or_photo);
        builder.setPositiveButton(R.string.camera, (dialog, id) -> {
            getImageFromCamera();
        });

        builder.setNegativeButton(R.string.gallery, (dialog, id) -> {
            getImageFromGallery();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    public void showImageDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_image).setMessage(R.string.ask_delete_image);
        builder.setPositiveButton(R.string.okay, (dialog, id) -> {
            bitmapList.remove(position);
            registerImageAdapter.notifyDataSetChanged();
            updateUI();
        });
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void getImageFromGallery() {
        if (!isWritePermissionGranted()) {
            showPermissionDialog(GALLERY_IMAGE_REQUEST);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(Intent.createChooser(intent, "다중 선택은 '포토'를 선택하세요."), GALLERY_IMAGE_REQUEST);
    }

    /**
     * 카메라에서 이미지 가져오기
     */
    public void getImageFromCamera() {
        if (!isWritePermissionGranted()) {
            showPermissionDialog(CAMERA_IMAGE_REQUEST);
            return;
        }

        dispatchTakePictureIntent();
    }

    private File createImageFile() throws IOException {
        String timeStamp = DateUtil.getCurrentDate();
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public boolean isWritePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return permissionCheck != PackageManager.PERMISSION_DENIED;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ToastUtil.getInstance().makeShort(R.string.image_fail);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.yunjaena.accident_management.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_IMAGE_REQUEST);
            }
        }
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
                    case GALLERY_IMAGE_REQUEST:
                        getImageFromGallery();
                        break;
                    case CAMERA_IMAGE_REQUEST:
                        getImageFromCamera();
                        break;
                }
            } else {
                ToastUtil.getInstance().makeShort(R.string.need_write_permission);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_IMAGE_REQUEST:
                    if (data != null)
                        addImageFromGallery(data);
                    break;
                case CAMERA_IMAGE_REQUEST:
                    galleryAddPic();
                    addImageFromCamera();
                    break;
            }
        } else {
            ToastUtil.getInstance().makeShort(R.string.image_cancel);
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        currentPhotoFile = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(currentPhotoFile);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void addImageFromGallery(Intent data) {
        try {
            if (data != null) {
                if (data.getClipData() == null) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                    bitmapList.add(bitmap);
                } else {
                    ClipData clipData = data.getClipData();
                    if (clipData.getItemCount() > 10) {
                        ToastUtil.getInstance().makeShort(MAX_IMAGE_SELECT + getResources().getString(R.string.image_over_select_warning));
                        return;
                    } else if (clipData.getItemCount() == 1) {
                        Uri uri = clipData.getItemAt(0).getUri();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                        bitmapList.add(bitmap);
                    } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 10) {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                            bitmapList.add(bitmap);
                        }
                    }
                }
                updateUI();
            }
        } catch (Exception e) {
            ToastUtil.getInstance().makeShort(R.string.image_fail);
        }
    }

    public void addImageFromCamera() {
        try {
            String filePath = currentPhotoFile.getAbsolutePath();
            if (filePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                bitmapList.add(bitmap);
                updateUI();
            }
        } catch (Exception e) {
            ToastUtil.getInstance().makeShort(R.string.image_fail);
        }
    }

    public void updateUI() {
        accidentDateTextView.setText(selectDate);

        if (bitmapList.size() < 1) {
            cameraLinearLayout.setVisibility(View.VISIBLE);
        } else {
            cameraLinearLayout.setVisibility(View.GONE);
        }
        registerImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_camera_linear_layout:
                showCameraOrGallerySelectDialog();
                break;
            case R.id.register_accident_date_text_view:
                openCalendarView();
                break;
        }
    }

    private void openCalendarView() {
        String[] dateSplitString = selectDate.split("-");
        int year = Integer.parseInt(dateSplitString[0]);
        int month = Integer.parseInt(dateSplitString[1]);
        int day = Integer.parseInt(dateSplitString[2]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, yearSelect, monthSelect, daySelect) -> {
            selectDate = String.format(Locale.KOREA, "%04d-%02d-%02d", yearSelect, monthSelect + 1, daySelect);
            updateUI();
        }, year, month - 1, day);

        datePickerDialog.show();
    }

    public void setConstructionTypeSpinner() {
        ArrayList arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.construction_type)));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        constructionTypeSpinner.setAdapter(arrayAdapter);
        constructionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                constructionType = index;
                setConstructionTypeSpecificSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        constructionTypeSpinner.post(() -> constructionTypeSpinner.setSelection(constructionType));
    }

    public void setConstructionTypeSpecificSpinner() {
        if (ConstructType.childArrayResourceId(constructionType) == null) {
            constructionTypeSpecific = -1;
            registerConstructionTypeSpecificLinearLayout.setVisibility(View.GONE);
            return;
        }

        constructionTypeSpecific = 0;
        registerConstructionTypeSpecificLinearLayout.setVisibility(View.VISIBLE);
        int arrayId = getResId(ConstructType.childArrayResourceId(constructionType), R.array.class);
        ArrayList arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayId)));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        constructionTypeSpecificSpinner.setAdapter(arrayAdapter);
        constructionTypeSpecificSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                constructionTypeSpecific = index;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    public void setRegisterDelayCauseOneSpinner() {
        ArrayList arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.delay_cause)));
        arrayList.add(0, getResources().getString(R.string.none));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        registerDelayCauseOneSpinner.setAdapter(arrayAdapter);
        registerDelayCauseOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                delayCause1 = index - 1;
                delayCauseSpecific1 = 0;
                setRegisterDelayCauseOneSpecificSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        registerDelayCauseOneSpinner.post(() -> registerDelayCauseOneSpinner.setSelection(delayCause1 + 1));
    }

    public void setRegisterDelayCauseOneSpecificSpinner() {
        if (delayCause1 == -1) {
            delayCauseSpecific1 = -1;
            delayCauseOneSpecificLinearLayout.setVisibility(View.GONE);
            updateSavePath();
            return;
        }

        delayCauseOneSpecificLinearLayout.setVisibility(View.VISIBLE);
        int arrayId = getResId(DelayCause.childArrayResourceId(delayCause1), R.array.class);
        ArrayList arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayId)));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        registerDelayCauseOneSpecificSpinner.setAdapter(arrayAdapter);
        registerDelayCauseOneSpecificSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                delayCauseSpecific1 = index;
                updateSavePath();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        updateSavePath();
    }


    public void setRegisterDelayCauseTwoSpinner() {
        ArrayList arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.delay_cause)));
        arrayList.add(0, getResources().getString(R.string.none));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        registerDelayCauseTwoSpinner.setAdapter(arrayAdapter);
        registerDelayCauseTwoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                delayCause2 = index - 1;
                delayCauseSpecific2 = 0;
                setRegisterDelayCauseTwoSpecificSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        registerDelayCauseTwoSpinner.post(() -> registerDelayCauseTwoSpinner.setSelection(delayCause2 + 1));
    }

    public void setRegisterDelayCauseTwoSpecificSpinner() {
        if (delayCause2 == -1) {
            delayCauseSpecific2 = -1;
            delayCauseTwoSpecificLinearLayout.setVisibility(View.GONE);
            updateSavePath();
            return;
        }

        delayCauseTwoSpecificLinearLayout.setVisibility(View.VISIBLE);
        int arrayId = getResId(DelayCause.childArrayResourceId(delayCause2), R.array.class);
        ArrayList arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayId)));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        registerDelayCauseTwoSpecificSpinner.setAdapter(arrayAdapter);
        registerDelayCauseTwoSpecificSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                delayCauseSpecific2 = index;
                updateSavePath();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        updateSavePath();
    }

    public void updateSavePath() {
        if (delayCause1 == delayCause2 && delayCauseSpecific1 == delayCauseSpecific2) {
            ToastUtil.getInstance().makeShort(R.string.error_same_delay_cause);
            savePathTextView.setText("");
            savePath = -1;
            return;
        }
        String[] savePathStringArray = getResources().getStringArray(R.array.save_path);
        savePath = SavePath.getSavePath(delayCause1, delayCauseSpecific1, delayCause2, delayCauseSpecific2);
        if (savePath == -1)
            savePathTextView.setText("");
        else
            savePathTextView.setText(savePathStringArray[savePath]);
    }
}
