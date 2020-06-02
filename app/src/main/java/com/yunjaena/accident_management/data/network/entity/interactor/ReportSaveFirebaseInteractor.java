package com.yunjaena.accident_management.data.network.entity.interactor;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.yunjaena.accident_management.data.network.entity.Report;
import com.yunjaena.accident_management.util.DateUtil;
import com.yunjaena.accident_management.util.ImageQualityUtil;
import com.yunjaena.accident_management.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ReportSaveFirebaseInteractor implements ReportSaveInteractor {
    public static final String TAG = "ReportSaveFirebase";
    private final String REFERENCE_URL = "gs://accidentmanagement-b90c8.appspot.com";

    @Override
    public Observable<Boolean> saveReport(Report report) {
       return saveImage(report.getImageBitmap()).
                map(StringUtil::stringListToString)
                .doOnNext(report::setImageFileArrayString)
                .flatMap(s -> saveReportAtFireBase(report));
    }



    @Override
    public Observable<List<String>> saveImage(List<Bitmap> bitmapList) {
        List<Observable<String>> imageStringList = new ArrayList<>();
        for (Bitmap bitmap : bitmapList) {
            imageStringList.add(saveImageFromFireBase(bitmap));
        }
        return Observable.fromArray(imageStringList)
                .flatMapIterable(stringObservable -> stringObservable)
                .flatMap(stringObservable -> stringObservable.observeOn(Schedulers.io()))
                .toList()
                .toObservable();
    }

    public Observable<Boolean> saveReportAtFireBase(Report report) {
        return Observable.create(subscriber -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> childUpdates = new HashMap<>();
            Map<String, Object> postValues = null;
            report.setId(DateUtil.getCurrentDateWithOutTime() + UUID.randomUUID());
            postValues = report.toMap();
            childUpdates.put("/report_list/" + report.getId(), postValues);
            Task<Void> task = databaseReference.updateChildren(childUpdates);
            task.addOnFailureListener(e -> subscriber.onError(e)).addOnSuccessListener(aVoid -> {
                subscriber.onNext(true);
                subscriber.onComplete();
            });
        });
    }

    public Observable<String> saveImageFromFireBase(Bitmap bitmap) {
        return Observable.create(subscriber -> {
            byte[] data = ImageQualityUtil.reduceSize(bitmap, 1);
            if (data == null) {
                subscriber.onError(new Throwable("image compress failed"));
                return;
            }
            String fileName = DateUtil.getCurrentDate() + UUID.randomUUID() + ".jpg";
            UploadTask uploadTask = FirebaseStorage.getInstance().getReferenceFromUrl(REFERENCE_URL).child(fileName).putBytes(data);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                subscriber.onNext(fileName);
                subscriber.onComplete();
            }).addOnFailureListener(command ->
            {
                subscriber.onError(new Throwable(command.getMessage()));
                Log.e(TAG, "saveImageFromFireBase: ", command.getCause());
            });
        });
    }

}
