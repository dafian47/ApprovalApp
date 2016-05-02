package com.xeranta.dev.approvalapp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.xeranta.dev.approvalapp.BaseApplication;
import com.xeranta.dev.approvalapp.utility.Constants;
import com.xeranta.dev.approvalapp.view.activity.DetailView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailPresenter implements BasePresenter<DetailView> {

    private static final String TAG = "DetailPresenter";

    private DetailView detailView;

    private AQuery aq;
    private ProgressDialog dialog;
    private BaseApplication application;

    public DetailPresenter(Context context) {
        this.aq     = new AQuery(context);
        this.dialog = new ProgressDialog(context);
    }

    @Override
    public void attachView(DetailView view) {
        this.detailView = view;
    }

    @Override
    public void detachView() {
        this.detailView = null;
    }

    public void getTaskById(String taskId) {
        new GetTaskById(taskId).execute();
    }

    public void updateTaskById(String taskId, String taskStatus) {
        application = (BaseApplication) detailView.getContext();
        new UpdateApproveStatus(taskId, taskStatus).execute();
    }

    private class GetTaskById extends AsyncTask<Void, Void, Void> {

        private String taskId;

        private GetTaskById(String taskId) {
            this.taskId = taskId;
        }

        @Override
        protected Void doInBackground(Void... params) {

            final String URL = Constants.Code.GET_TASK_BY_ID;
            JSONObject input = new JSONObject();
            try {
                input.put("taskId", taskId);
            } catch (JSONException je) {
                je.printStackTrace();
            }

            aq.progress(dialog).post(URL, input, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {

                    if (status.getCode() == 200) {

                        try {

                            JSONObject task = json.getJSONObject("task");

                            String taskName = task.getString("taskName");
                            String taskStatus = task.getString("taskStatus");
                            String description = task.getString("description");
                            String createDate = task.getString("createDate");
                            String approveDate = task.getString("approveDate");
                            String approvedBy = task.getString("approvedBy");

                            HashMap<String, String> map = new HashMap<>();
                            map.put("taskName", taskName);
                            map.put("taskStatus", taskStatus);
                            map.put("description", description);
                            map.put("createDate", createDate);
                            map.put("approveDate", approveDate);
                            map.put("approvedBy", approvedBy);

                            detailView.showTaskById(map);
                            detailView.showMessage("Success");

                        } catch (JSONException je) {
                            je.printStackTrace();
                            detailView.showError(je.getMessage());
                        }

                    } else {
                        Integer code = status.getCode();
                        String message = status.getMessage();
                        detailView.showError("Error with : "+code+":"+message);
                    }

                }
            });
            return null;
        }
    }

    private class UpdateApproveStatus extends AsyncTask<Void, Void, Void> {

        private String taskId;
        private String taskStatus;

        private static final String DATE_SERVER_FORMAT = "yyyy-MM-dd HH:mm:ss";

        private UpdateApproveStatus(String taskId, String taskStatus) {
            this.taskId = taskId;
            this.taskStatus = taskStatus;
        }

        @Override
        protected Void doInBackground(Void... params) {

            String user = application.getGlobalUserName();

            DateTime currentDate = new DateTime();
            String date = DateTimeFormat.forPattern(DATE_SERVER_FORMAT).print(currentDate);

            Log.i(TAG, "Current Date: "+date);
            Log.i(TAG, "Username: "+user);
            Log.i(TAG, "Task Status: "+taskStatus);
            Log.i(TAG, "Task Id: " + taskId);

            String URL = Constants.Code.UPDATE_TASK_BY_ID;
            JSONObject input = new JSONObject();
            try {
                input.put("taskId", taskId);
                input.put("taskStatus", taskStatus);
                input.put("approvedBy", user);
                input.put("approveDate", date);
            } catch (JSONException je) {
                je.printStackTrace();
            }

            aq.progress(dialog).post(URL, input, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {

                    if (status.getCode() == 200) {

                        detailView.showMessage("Success Update");
                        detailView.onUpdateSuccess();

                    } else {

                        Integer code = status.getCode();
                        String message = status.getMessage();
                        detailView.showError("Error with : "+code+":"+message);
                    }

                }
            });

            return null;
        }
    }
}
