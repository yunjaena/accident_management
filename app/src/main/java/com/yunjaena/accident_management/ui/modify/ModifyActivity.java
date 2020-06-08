package com.yunjaena.accident_management.ui.modify;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.Image;
import com.yunjaena.accident_management.data.network.entity.Report;
import com.yunjaena.accident_management.data.network.entity.ReportSerial;
import com.yunjaena.accident_management.data.network.interactor.ReportFirebaseInteractor;
import com.yunjaena.accident_management.ui.modify.adapter.ModifyImageAdapter;
import com.yunjaena.accident_management.ui.modify.presenter.ModifyContract;
import com.yunjaena.accident_management.ui.modify.presenter.ModifyPresenter;
import com.yunjaena.accident_management.ui.resgister.ConstructType;
import com.yunjaena.accident_management.ui.resgister.DelayCause;
import com.yunjaena.accident_management.ui.resgister.SavePath;
import com.yunjaena.accident_management.ui.retrieve.RetrieveActivity;
import com.yunjaena.accident_management.util.DateUtil;
import com.yunjaena.core.activity.ActivityBase;
import com.yunjaena.core.recyclerview.RecyclerViewClickListener;
import com.yunjaena.core.toast.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ModifyActivity extends ActivityBase implements ModifyContract.View, View.OnClickListener {
    public static final String TAG = "ModifyActivity";
    public static final int MAX_IMAGE_SELECT = 10;
    public static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_IMAGE_REQUEST = 2;
    public static final int MIN_IMAGE_INPUT = 0;
    private Context context;
    private String currentPhotoPath;
    private ModifyPresenter modifyPresenter;
    private LinearLayout modifyConstructionTypeLinearLayout;
    private LinearLayout modifyConstructionTypeSpecificLinearLayout;
    private LinearLayout delayCauseOneSpecificLinearLayout;
    private LinearLayout delayCauseTwoSpecificLinearLayout;
    private EditText companyNameEditText;
    private TextView accidentDateTextView;
    private TextView expectStartDayTextView;
    private TextView expectEndDayTextView;
    private TextView realStartDayTextView;
    private TextView realEndDayTextView;
    private Spinner constructionTypeSpinner;
    private Spinner constructionTypeSpecificSpinner;
    private Spinner modifyDelayCauseOneSpinner;
    private Spinner modifyDelayCauseOneSpecificSpinner;
    private Spinner modifyDelayCauseTwoSpinner;
    private Spinner modifyDelayCauseTwoSpecificSpinner;
    private TextView savePathTextView;
    private RecyclerView cameraRecyclerView;
    private LinearLayout cameraLinearLayout;
    private ImageView cameraImageView;
    private EditText pictureDescribeEditText;
    private File currentPhotoFile;
    private List<Image> imageList;
    private ModifyImageAdapter modifyImageAdapter;
    private LinearLayoutManager linearLayoutManager;

    private String id;
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
        setContentView(R.layout.activity_modify);
        init();
    }

    public void init() {
        Report report = new Report((ReportSerial) getIntent().getSerializableExtra(RetrieveActivity.REPORT));
        imageList = new ArrayList<>();
        context = this;
        id = report.getId();
        constructionType = report.getConstructType();
        constructionTypeSpecific = report.getConstructDetailType();
        delayCause1 = report.getDelayCauseOne();
        delayCauseSpecific1 = report.getDelayCauseDetailOne();
        delayCause2 = report.getDelayCauseTwo();
        delayCauseSpecific2 = report.getDelayCauseDetailTwo();
        savePath = report.getSavePath();
        for (String fileName : report.getImageFileArray())
            imageList.add(new Image(fileName));

        modifyPresenter = new ModifyPresenter(this, new ReportFirebaseInteractor());
        initView();
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.modify);


        updateUI();
        companyNameEditText.setText(report.getCompanyName());
        accidentDateTextView.setText(report.getAccidentDate());
        expectStartDayTextView.setText(report.getExpectStartDate());
        expectEndDayTextView.setText(report.getExpectEndDate());
        realStartDayTextView.setText(report.getRealStartDate());
        realEndDayTextView.setText(report.getRealEndDate());
        pictureDescribeEditText.setText(report.getPictureDescribe());

        constructionTypeSpinner.setSelection(constructionType);
        modifyDelayCauseOneSpinner.setSelection(report.getDelayCauseOne() + 1);
        modifyDelayCauseTwoSpinner.setSelection(report.getDelayCauseTwo() + 1);
        if (report.getConstructDetailType() != -1)
            constructionTypeSpecificSpinner.postDelayed(() -> constructionTypeSpecificSpinner.setSelection(report.getConstructDetailType(), true), 1000);
        modifyDelayCauseOneSpecificSpinner.postDelayed(() -> modifyDelayCauseOneSpecificSpinner.setSelection(report.getDelayCauseDetailOne(), true), 1000);
        modifyDelayCauseTwoSpecificSpinner.postDelayed(() -> modifyDelayCauseTwoSpecificSpinner.setSelection(report.getDelayCauseDetailTwo(), true), 1000);
    }

    public void initView() {
        modifyConstructionTypeSpecificLinearLayout = findViewById(R.id.modify_construction_type_specific_linear_layout);
        modifyConstructionTypeLinearLayout = findViewById(R.id.modify_construction_type_linear_layout);
        constructionTypeSpecificSpinner = findViewById(R.id.modify_construction_type_specific_spinner);
        companyNameEditText = findViewById(R.id.modify_company_name_edit_text);
        accidentDateTextView = findViewById(R.id.modify_accident_date_text_view);
        expectStartDayTextView = findViewById(R.id.modify_expect_start_date_text_view);
        expectEndDayTextView = findViewById(R.id.modify_expect_end_date_text_view);
        constructionTypeSpinner = findViewById(R.id.modify_construction_type_spinner);
        modifyDelayCauseOneSpinner = findViewById(R.id.modify_delay_cause_one_spinner);
        modifyDelayCauseOneSpecificSpinner = findViewById(R.id.modify_delay_cause_specific_one_spinner);
        modifyDelayCauseTwoSpinner = findViewById(R.id.modify_delay_cause_two_spinner);
        modifyDelayCauseTwoSpecificSpinner = findViewById(R.id.modify_delay_cause_specific_two_spinner);
        savePathTextView = findViewById(R.id.modify_save_path_text_view);
        cameraRecyclerView = findViewById(R.id.modify_camera_recycler_view);
        cameraLinearLayout = findViewById(R.id.modify_camera_linear_layout);
        cameraImageView = findViewById(R.id.modify_camera_image_view);
        delayCauseOneSpecificLinearLayout = findViewById(R.id.modify_delay_cause_specific_one_linear_layout);
        delayCauseTwoSpecificLinearLayout = findViewById(R.id.modify_delay_cause_specific_two_linear_layout);
        realStartDayTextView = findViewById(R.id.modify_real_start_date_text_view);
        realEndDayTextView = findViewById(R.id.modify_real_end_date_text_view);
        pictureDescribeEditText = findViewById(R.id.modify_picture_describe_edit_text);
        setEditTextGravityStartWhenLengthOverZero(pictureDescribeEditText);
        cameraLinearLayout.setOnClickListener(this);
        accidentDateTextView.setOnClickListener(this);
        expectStartDayTextView.setOnClickListener(this);
        expectEndDayTextView.setOnClickListener(this);
        realStartDayTextView.setOnClickListener(this);
        realEndDayTextView.setOnClickListener(this);
        setCameraRecyclerView();

        /*Set Spinner*/
        setConstructionTypeSpinner();
        setRegisterDelayCauseOneSpinner();
        setRegisterDelayCauseTwoSpinner();

    }

    public void setCameraRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        modifyImageAdapter = new ModifyImageAdapter(this, imageList);
        modifyImageAdapter.setRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position >= imageList.size()) {
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
        cameraRecyclerView.setAdapter(modifyImageAdapter);
        modifyImageAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modify, menu);
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
        String expectStartDate = expectStartDayTextView.getText().toString().trim();
        String expectEndDate = expectEndDayTextView.getText().toString().trim();
        String realStartDate = realStartDayTextView.getText().toString().trim();
        String realEndDate = realEndDayTextView.getText().toString().trim();
        int constructType = this.constructionType;
        int constructDetailType = this.constructionTypeSpecific;
        int delayCauseOne = this.delayCause1;
        int delayCauseDetailOne = this.delayCauseSpecific1;
        int delayCauseTwo = this.delayCause2;
        int delayCauseDetailTwo = this.delayCauseSpecific2;
        int savePath = this.savePath;
        String pictureDescribe = pictureDescribeEditText.getText().toString().trim();


        if (companyName.isEmpty() || savePath == -1
        ) {
            ToastUtil.getInstance().makeShort(R.string.input_all_waring);
            return;
        }

        if ((!expectStartDate.isEmpty() && !expectEndDate.isEmpty()) && DateUtil.dateCompare(expectStartDate, expectEndDate) >= 0) {
            ToastUtil.getInstance().makeShort(R.string.expect_time_faster_than_before_warning);
            return;
        }

        if ((!realStartDate.isEmpty() && !realEndDate.isEmpty()) && DateUtil.dateCompare(realStartDate, realEndDate) >= 0) {
            ToastUtil.getInstance().makeShort(R.string.time_faster_than_before_warning);
            return;
        }

        if (delayCauseOne == -1 && delayCauseDetailOne == -1) {
            delayCauseDetailOne = delayCauseDetailTwo;
            delayCauseOne = delayCauseTwo;
        }
        List<Bitmap> bitmapList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();
        for (Image image : imageList) {
            if (image.getBitmap() != null)
                bitmapList.add(image.getBitmap());
            else
                fileList.add(image.getImageString());
        }
        Report.Builder builder = new Report.Builder();
        builder.id(id);
        builder.companyName(companyName);
        builder.accidentDate(accidentDate);
        builder.realStartDate(realStartDate);
        builder.updateDate(DateUtil.getCurrentDate());
        builder.saveDate(DateUtil.getCurrentDate());
        builder.expectStartDate(expectStartDate);
        builder.expectEndDate(expectEndDate);
        builder.realEndDate(realEndDate);
        builder.constructType(constructType);
        builder.constructDetailType(constructDetailType);
        builder.delayCauseOne(delayCauseOne);
        builder.delayCauseDetailOne(delayCauseDetailOne);
        builder.delayCauseTwo(delayCauseTwo);
        builder.delayCauseDetailTwo(delayCauseDetailTwo);
        builder.savePath(savePath);
        builder.pictureDescribe(pictureDescribe);
        builder.isDelete(false);
        builder.imageFileName(fileList);
        builder.imageBitmap(bitmapList);
        modifyPresenter.updateReport(builder.build());

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
        modifyPresenter.releaseView();
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
    public void setPresenter(ModifyPresenter presenter) {
        this.modifyPresenter = presenter;
    }

    @Override
    public void showSuccessSaved() {
        finish();
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
            imageList.remove(position);
            modifyImageAdapter.notifyDataSetChanged();
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
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
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
        String timeStamp = DateUtil.getCurrentDateWithUnderBar();
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
                    imageList.add(new Image(bitmap));
                } else {
                    ClipData clipData = data.getClipData();
                    if (clipData.getItemCount() > 10) {
                        ToastUtil.getInstance().makeShort(MAX_IMAGE_SELECT + getResources().getString(R.string.image_over_select_warning));
                        return;
                    } else if (clipData.getItemCount() == 1) {
                        Uri uri = clipData.getItemAt(0).getUri();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                        imageList.add(new Image(bitmap));
                    } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 10) {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                            imageList.add(new Image(bitmap));
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
                imageList.add(new Image(bitmap));
                updateUI();
            }
        } catch (Exception e) {
            ToastUtil.getInstance().makeShort(R.string.image_fail);
        }
    }

    public void updateUI() {
        if (imageList.size() < 1) {
            cameraLinearLayout.setVisibility(View.VISIBLE);
        } else {
            cameraLinearLayout.setVisibility(View.GONE);
        }
        modifyImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_camera_linear_layout:
                showCameraOrGallerySelectDialog();
                break;
            case R.id.modify_accident_date_text_view:
            case R.id.modify_expect_start_date_text_view:
            case R.id.modify_expect_end_date_text_view:
            case R.id.modify_real_end_date_text_view:
            case R.id.modify_real_start_date_text_view:
                openDateTimePicker(findViewById(v.getId()));
                break;
        }
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
    }

    public void setConstructionTypeSpecificSpinner() {
        if (ConstructType.childArrayResourceId(constructionType) == null) {
            constructionTypeSpecific = -1;
            modifyConstructionTypeSpecificLinearLayout.setVisibility(View.GONE);
            return;
        }

        constructionTypeSpecific = 0;
        modifyConstructionTypeSpecificLinearLayout.setVisibility(View.VISIBLE);
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
        modifyDelayCauseOneSpinner.setAdapter(arrayAdapter);
        modifyDelayCauseOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        modifyDelayCauseOneSpecificSpinner.setAdapter(arrayAdapter);
        modifyDelayCauseOneSpecificSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        modifyDelayCauseTwoSpinner.setAdapter(arrayAdapter);
        modifyDelayCauseTwoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        modifyDelayCauseTwoSpecificSpinner.setAdapter(arrayAdapter);
        modifyDelayCauseTwoSpecificSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void openDateTimePicker(TextView textView) {
        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(view -> {
            DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
            TimePicker timePicker = dialogView.findViewById(R.id.time_picker);
            Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth(),
                    timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute());

            textView.setText(DateUtil.getCurrentDate(calendar));
            alertDialog.dismiss();
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }
}
