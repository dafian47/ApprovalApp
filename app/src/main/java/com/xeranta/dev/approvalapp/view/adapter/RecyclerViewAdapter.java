package com.xeranta.dev.approvalapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xeranta.dev.approvalapp.model.entity.ItemTask;
import com.xeranta.dev.approvalapp.R;

import java.util.List;

/**
 * Created by mactc02 on 2/15/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final static String TAG = "RecyclerView";
    private List<ItemTask> itemTaskList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ItemTask> itemTaskList) {
        this.context = context;
        this.itemTaskList = itemTaskList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameTask;
        public TextView tvDescTask;
        public TextView tvStatusTask;
        public TextView tvDateTask;
        public LinearLayout llTask;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvNameTask = (TextView) itemLayoutView.findViewById(R.id.tv_name_task);
            tvDescTask = (TextView) itemLayoutView.findViewById(R.id.tv_desc_task);
            tvStatusTask = (TextView) itemLayoutView.findViewById(R.id.tv_status_approval);
            tvDateTask = (TextView) itemLayoutView.findViewById(R.id.tv_date_task);
            llTask = (LinearLayout) itemLayoutView.findViewById(R.id.ll_task);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task, null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvNameTask.setText(itemTaskList.get(position).getTaskName());
        holder.tvDescTask.setText(itemTaskList.get(position).getTaskDesc());
        holder.tvDateTask.setText(itemTaskList.get(position).getCreateDate());

        String status = "";

        if (itemTaskList.get(position).getTaskStatus().equalsIgnoreCase("0")) {
            holder.tvStatusTask.setVisibility(View.VISIBLE);
            holder.llTask.setBackground(context.getResources().getDrawable(R.drawable.gray_circle));
            status = "W";
        } else if (itemTaskList.get(position).getTaskStatus().equalsIgnoreCase("1")) {
            holder.tvStatusTask.setVisibility(View.VISIBLE);
            holder.llTask.setBackground(context.getResources().getDrawable(R.drawable.green_circle));
            status = "A";
        } else if (itemTaskList.get(position).getTaskStatus().equalsIgnoreCase("2")) {
            holder.tvStatusTask.setVisibility(View.VISIBLE);
            holder.llTask.setBackground(context.getResources().getDrawable(R.drawable.red_circle));
            status = "R";
        }

        holder.tvStatusTask.setText(status);
    }

    @Override
    public int getItemCount() {
        return itemTaskList.size();
    }
}
