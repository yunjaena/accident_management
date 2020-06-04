package com.yunjaena.accident_management.data.network.interactor;

import android.graphics.Bitmap;
import com.yunjaena.accident_management.data.network.entity.Report;
import java.util.List;
import io.reactivex.Observable;

public interface ReportInteractor {
    Observable<Boolean> saveReport(Report report);

    Observable<List<String>> saveImage(List<Bitmap> bitmapList);

    Observable<List<Report>> loadAllReport();
}
