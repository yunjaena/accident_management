package com.yunjaena.accident_management.ui.retrieve.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.Report;
import com.yunjaena.core.recyclerview.RecyclerViewClickListener;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<Report> reportList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;

    public ReportAdapter(Context context, List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = reportList.get(position);
        holder.companyNameTextView.setText(report.getCompanyName());
        holder.constructionTypeTextView.setText(context.getResources().getStringArray(R.array.construction_type)[report.getConstructType()]);
        String causeOne = (report.getDelayCauseOne() != -1) ? context.getResources().getStringArray(R.array.delay_cause)[report.getDelayCauseOne()] : context.getResources().getString(R.string.none);
        String causeTwo = (report.getDelayCauseTwo() != -1) ? context.getResources().getStringArray(R.array.delay_cause)[report.getDelayCauseTwo()] : context.getResources().getString(R.string.none);
        holder.delayCauseOneTextView.setText(causeOne);
        holder.delayCauseTwoTextView.setText(causeTwo);
        holder.realStartTextView.setText(report.getRealStartDate());
        holder.realEndTextView.setText(report.getRealEndDate());
        holder.reportLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerViewClickListener != null)
                    recyclerViewClickListener.onClick(holder.reportLinearLayout, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public void setRecyclerViewClickListener(RecyclerViewClickListener recyclerViewClickListener) {
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView companyNameTextView;
        public TextView constructionTypeTextView;
        public TextView delayCauseOneTextView;
        public TextView delayCauseTwoTextView;
        public TextView realStartTextView;
        public TextView realEndTextView;
        public LinearLayout reportLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyNameTextView = itemView.findViewById(R.id.report_company_name_text_view);
            constructionTypeTextView = itemView.findViewById(R.id.report_construction_type_text_view);
            delayCauseOneTextView = itemView.findViewById(R.id.report_delay_cause_one_text_view);
            delayCauseTwoTextView = itemView.findViewById(R.id.report_delay_cause_two_text_view);
            realStartTextView = itemView.findViewById(R.id.report_real_start_text_view);
            realEndTextView = itemView.findViewById(R.id.report_real_end_text_view);
            reportLinearLayout = itemView.findViewById(R.id.report_linear_layout);
        }
    }
}
