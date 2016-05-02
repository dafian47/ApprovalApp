package com.xeranta.dev.approvalapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xeranta.dev.approvalapp.R;
import com.xeranta.dev.approvalapp.model.entity.ItemTask;
import com.xeranta.dev.approvalapp.presenter.MainPresenter;
import com.xeranta.dev.approvalapp.view.adapter.RecyclerItemClickListener;
import com.xeranta.dev.approvalapp.view.adapter.RecyclerViewAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tv_total_task) TextView tvTotalTask;
    @Bind(R.id.tv_remaining_task) TextView tvRemainTask;
    @Bind(R.id.recyclerView) RecyclerView rvListTask;

    private RecyclerViewAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        presenter   = new MainPresenter(this);
        presenter.attachView(this);

        initEvent();
    }

    private void initEvent() {
        rvListTask.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvListTask.setItemAnimator(new DefaultItemAnimator());

        presenter.getTaskList();
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(rvListTask, msg, Snackbar.LENGTH_LONG).show();
        Log.i(TAG, "Message : "+msg);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        Log.i(TAG, "Error : "+msg);
    }

    @Override
    public void reloadData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showItemTaskList(final List<ItemTask> list, String remainTask) {

        tvRemainTask.setText(remainTask);
        tvTotalTask.setText(String.valueOf(list.size()));

        adapter     = new RecyclerViewAdapter(this, list);
        rvListTask.setAdapter(adapter);
        rvListTask.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String id = list.get(position).getTaskId();
                        String name = list.get(position).getTaskName();
                        detailTask(id, name);
                    }
                })
        );
    }

    @Override
    public Context getContext() {
        return null;
    }

    private void detailTask(String id, String name) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("task_id", id);
        intent.putExtra("task_name", name);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
