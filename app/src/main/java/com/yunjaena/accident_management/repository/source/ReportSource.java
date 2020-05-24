package com.yunjaena.accident_management.repository.source;

import com.yunjaena.accident_management.repository.entity.Report;

import java.util.List;

public interface ReportSource {
    boolean saveReport(Report report);

    boolean updateReport(Report report);

    Report loadReport(int id);

    List<Report> loadReportList(int id);
}
