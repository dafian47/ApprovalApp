package com.xeranta.dev.approvalapp.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xeranta.dev.approvalapp.model.entity.ItemTask;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "db_taskapproval";
    private static int DATABASE_VERSION = 1;

    private static String TBL_TASK = "tbl_task";
    private static String TASK_ID = "task_id";
    private static String TASK_NAME = "task_name";
    private static String TASK_DESC = "task_desc";
    private static String TASK_STATUS = "task_status";

    private static String CREATE_TABLE_TASK = "CREATE TABLE "+TBL_TASK+" " +
            "("+TASK_ID+" TEXT, "+TASK_NAME+" TEXT, "+TASK_DESC+" TEXT, "+TASK_STATUS+" TEXT );";

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_TASK);
        db.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_TASK);
        onCreate(db);
    }

    public void createItemTask(ItemTask itemTask) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TASK_ID, itemTask.getTaskId());
        values.put(TASK_NAME, itemTask.getTaskName());
        values.put(TASK_DESC, itemTask.getTaskDesc());
        values.put(TASK_STATUS, itemTask.getTaskStatus());

        db.insert(TBL_TASK, null, values);
    }

    public List<ItemTask> getDataTask() {

        List<ItemTask> itemTasks = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TBL_TASK;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                ItemTask itemTask = new ItemTask();
                itemTask.setTaskId((c.getString(c.getColumnIndex(TASK_ID))));
                itemTask.setTaskName(c.getString(c.getColumnIndex(TASK_NAME)));
                itemTask.setTaskDesc(c.getString(c.getColumnIndex(TASK_DESC)));
                itemTask.setTaskStatus(c.getString(c.getColumnIndex(TASK_STATUS)));

                itemTasks.add(itemTask);
            } while (c.moveToNext());
        }
        return itemTasks;
    }

    public ItemTask getDataTaskById(String taskId) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TBL_TASK + " WHERE " + TASK_ID + " = '"+taskId+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        ItemTask itemTask = new ItemTask();
        itemTask.setTaskId((c.getString(c.getColumnIndex(TASK_ID))));
        itemTask.setTaskName(c.getString(c.getColumnIndex(TASK_NAME)));
        itemTask.setTaskDesc(c.getString(c.getColumnIndex(TASK_DESC)));
        itemTask.setTaskStatus(c.getString(c.getColumnIndex(TASK_STATUS)));
        return itemTask;
    }

    public int updateDataTask(ItemTask itemTask) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_STATUS, itemTask.getTaskStatus());

        return db.update(TBL_TASK, values, TASK_ID + " = ?", new String[] { String.valueOf(itemTask.getTaskId()) });
    }
}
