package com.xeranta.dev.approvalapp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.xeranta.dev.approvalapp.model.entity.ItemTask;
import com.xeranta.dev.approvalapp.utility.Constants;
import com.xeranta.dev.approvalapp.utility.Helper;
import com.xeranta.dev.approvalapp.view.activity.MainView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements BasePresenter<MainView> {

    private static final String TAG = "MainPresenter";

    private MainView mainView;

    private AQuery aq;
    private ProgressDialog dialog;

    public MainPresenter(Context context) {
        this.aq     = new AQuery(context);
        this.dialog = new ProgressDialog(context);
    }

    @Override
    public void attachView(MainView view) {
        this.mainView = view;
    }

    @Override
    public void detachView() {
        this.mainView = null;
    }

    public void getTaskList() {
        new GetTaskList().execute();
    }

    private class GetTaskList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Getting Data...");
            dialog.setIndeterminate(true);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            final String URL = Constants.Code.GET_TASK_LIST;
            JSONObject input = new JSONObject();

            Log.i(TAG, "URL: "+URL);

            aq.progress(dialog).post(URL, input, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {

                    Log.i(TAG, "Status Code: " + status.getCode());
                    Log.i(TAG, "Status Message: " + status.getMessage());
                    Log.i(TAG, "JSON: " + json.toString());

                    if (status.getCode() == 200) {

                        try {

                            int nTask = 0;
                            List<ItemTask> itemTasks = new ArrayList<>();

                            JSONArray tasks = json.getJSONArray("tasks");
                            Log.i(TAG, "Task Array: " + tasks.toString());

                            for (int i = 0; i < tasks.length(); i++) {

                                JSONObject data = tasks.getJSONObject(i);

                                String taskId = data.getString("taskId");
                                String taskName = data.getString("taskName");
                                String taskStatus = data.getString("taskStatus");
                                String description = data.getString("description");
                                String createDate = data.getString("createDate");

                                ItemTask itemTask = new ItemTask(
                                        taskId, taskName, description,
                                        taskStatus, Helper.changeDateFormat(createDate)
                                );

                                itemTasks.add(itemTask);

                                if (taskStatus.equalsIgnoreCase("0")) {
                                    nTask += 1;
                                }
                            }

                            String remainTask = nTask+" Task(s)";

                            mainView.showItemTaskList(itemTasks, remainTask);
                            mainView.showMessage("Success");
                            mainView.reloadData();

                        } catch (JSONException je) {
                            je.printStackTrace();

                            mainView.showError(je.getMessage());
                        }

                    } else {
                        Integer statusCode = status.getCode();
                        String statusMessage = status.getMessage();
                        mainView.showError("Error with :"+statusCode+":"+statusMessage);
                    }
                }
            });

            return null;
        }
    }
}
