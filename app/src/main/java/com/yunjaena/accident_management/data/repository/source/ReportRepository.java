package com.yunjaena.accident_management.data.repository.source;

import com.yunjaena.accident_management.data.repository.database.ReportDBHelper;
import com.yunjaena.accident_management.data.repository.entity.Report;
import com.yunjaena.accident_management.util.DateUtil;
import com.yunjaena.accident_management.util.ImageUtil;

import java.util.List;

public class ReportRepository implements ReportSource {

    @Override
    public boolean saveReport(Report report) {
        for (int i = 0; i < report.getImageBitmap().size(); i++) {
            String fileName = DateUtil.getCurrentDate() + ".png";
            if (ImageUtil.getInstance().setExternal(true).setFileName(fileName).setDirectoryName("pictures")
                    .save(report.getImageBitmap().get(i))) {
                report.getImageFileName().add(fileName);
            } else {
                return false;
            }
        }
        return ReportDBHelper.getInstance().saveReport(report);
    }

    @Override
    public boolean updateReport(Report report) {
        return ReportDBHelper.getInstance().updateReport(report);
    }

    @Override
    public Report loadReport(int id) {
        return ReportDBHelper.getInstance().getReportById(id);
    }

    @Override
    public List<Report> loadReportList(int id) {
        return ReportDBHelper.getInstance().getValidReportList();
    }
}
