package com.yunjaena.accident_management.ui.resgister;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.accident_management.R;
import com.yunjaena.core.activity.ActivityBase;

public class RegisterActivity extends ActivityBase {
    private EditText companyNameEditText;
    private EditText accidentDateEditText;
    private Spinner constructionTypeSpinner;
    private Spinner registerDelayCauseSpinner;
    private Spinner savePathSpinner;
    private RecyclerView cameraRecyclerView;
    private LinearLayout cameraLinearLayout;
    private EditText designChangeAndErrorEditText;
    private EditText contractChangeAndViolationEditText;
    private EditText inevitableClauseEditText;
    private EditText concurrentOccurrenceEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        initView();
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.register);

    }

    public void initView() {
        companyNameEditText = findViewById(R.id.register_company_name_edit_text);
        accidentDateEditText = findViewById(R.id.register_accident_date_edit_text);
        constructionTypeSpinner = findViewById(R.id.register_construction_type_spinner);
        registerDelayCauseSpinner = findViewById(R.id.register_delay_cause_spinner);
        savePathSpinner = findViewById(R.id.register_save_path_spinner);
        cameraRecyclerView = findViewById(R.id.register_camera_recycler_view);
        cameraLinearLayout = findViewById(R.id.register_camera_linear_layout);
        designChangeAndErrorEditText = findViewById(R.id.register_design_change_and_error_edit_text);
        contractChangeAndViolationEditText = findViewById(R.id.register_contract_change_and_violation_edit_text);
        inevitableClauseEditText = findViewById(R.id.register_inevitable_clause_edit_text);
        concurrentOccurrenceEditText = findViewById(R.id.register_concurrent_occurrence_edit_text);
        setEditTextGravityStartWhenLengthOverZero(designChangeAndErrorEditText);
        setEditTextGravityStartWhenLengthOverZero(contractChangeAndViolationEditText);
        setEditTextGravityStartWhenLengthOverZero(inevitableClauseEditText);
        setEditTextGravityStartWhenLengthOverZero(concurrentOccurrenceEditText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu) ;
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void save(){

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
                    editText.setGravity(Gravity.LEFT | Gravity.TOP);
                } else {
                    editText.setGravity(Gravity.CENTER);
                }
            }
        });
    }

}
