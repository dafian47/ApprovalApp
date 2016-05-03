package com.xeranta.dev.approvalapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xeranta.dev.approvalapp.R;
import com.xeranta.dev.approvalapp.model.entity.ItemTask;
import com.xeranta.dev.approvalapp.presenter.DetailPresenter;
import com.xeranta.dev.approvalapp.utility.Helper;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private final static String TAG = "DetailTask";

    @Bind(R.id.tv_detail_task_name) TextView tvDetailTaskName;
    @Bind(R.id.tv_detail_task_desc) TextView tvDetailTaskDesc;
    @Bind(R.id.tv_detail_task_createdate) TextView tvDetailTaskDate;

    @Bind(R.id.tv_header_approved_by) ImageView tvHeaderApproveBy;
    @Bind(R.id.tv_header_approved_date) ImageView tvHeaderApproveDate;
    @Bind(R.id.tv_approved_by) TextView tvApproveBy;
    @Bind(R.id.tv_approved_date) TextView tvApproveDate;

    @Bind(R.id.rb_approve) RadioButton rbApprove;
    @Bind(R.id.rb_reject) RadioButton rbReject;
    @Bind(R.id.bt_submit) Button btSubmit;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private String taskId;
    private String taskStatus;

    private DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        ButterKnife.bind(this);

        presenter   = new DetailPresenter(DetailActivity.this, DetailActivity.this);
        presenter.attachView(this);

        Bundle b = getIntent().getExtras();
        taskId = b.getString("task_id");
        String title = b.getString("task_name");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }

        presenter.getTaskById(taskId);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTask();
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(btSubmit, msg, Snackbar.LENGTH_LONG).show();
        Log.i(TAG, "Message : "+msg);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        Log.i(TAG, "Error : "+msg);
    }

    @Override
    public void showTaskById(HashMap<String, String> map) {

        String taskName = map.get("taskName");
        String taskStatus = map.get("taskStatus");
        String description = map.get("description");
        String createDate = map.get("createDate");
        String approveDate = map.get("approveDate");
        String approvedBy = map.get("approvedBy");

        tvDetailTaskName.setText(taskName);
        tvDetailTaskDesc.setText(description);
        tvDetailTaskDate.setText(Helper.changeDateFormat(createDate));

        if (taskStatus.equalsIgnoreCase("1")) {
            rbApprove.setChecked(true);
        } else if (taskStatus.equalsIgnoreCase("2")) {
            rbReject.setChecked(true);
        }

        if (taskStatus.equalsIgnoreCase("0")) {
            tvHeaderApproveBy.setVisibility(View.INVISIBLE);
            tvApproveBy.setVisibility(View.INVISIBLE);
            tvHeaderApproveDate.setVisibility(View.INVISIBLE);
            tvApproveDate.setVisibility(View.INVISIBLE);
        } else {
            tvApproveBy.setText(approvedBy);
            tvApproveDate.setText(Helper.changeDateFormat(approveDate));
            tvHeaderApproveBy.setVisibility(View.VISIBLE);
            tvHeaderApproveDate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUpdateSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        taskStatus = "";

        switch (view.getId()) {
            case R.id.rb_approve:
                if (checked)
                    taskStatus = "1";
                break;
            case R.id.rb_reject:
                if (checked)
                    taskStatus = "2";
                break;
        }
    }

    private void submitTask() {

        if (rbApprove.isChecked()) {
            taskStatus = "1";
        } else if (rbReject.isChecked()) {
            taskStatus = "2";
        } else {
            taskStatus = "0";
        }

        String taskName = tvDetailTaskName.getText().toString();
        String taskDesc = tvDetailTaskDesc.getText().toString();

        ItemTask itemTask = new ItemTask();
        itemTask.setTaskName(taskName);
        itemTask.setTaskDesc(taskDesc);
        itemTask.setTaskStatus(taskStatus);
        itemTask.setTaskId(taskId);

        presenter.updateTaskById(taskId, taskStatus);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
