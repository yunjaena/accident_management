package com.yunjaena.accident_management.data.network.entity.interactor;

import android.graphics.Bitmap;


import com.yunjaena.accident_management.data.network.entity.Report;

import java.util.List;

import io.reactivex.Observable;

public interface ReportSaveInteractor {
    Observable<Boolean> saveReport(Report report);

    Observable<List<String>> saveImage(List<Bitmap> bitmapList);
}
