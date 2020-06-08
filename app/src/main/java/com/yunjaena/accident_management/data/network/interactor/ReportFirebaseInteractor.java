package com.yunjaena.accident_management.data.network.interactor;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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

public class ReportFirebaseInteractor implements ReportInteractor {
    public static final String TAG = "ReportFirebase";
    private static final String REFERENCE_URL = "gs://accidentmanagement-b90c8.appspot.com";

    @Override
    public Observable<Boolean> saveReport(Report report) {
        return saveImage(report.getImageBitmap()).
                map(StringUtil::stringListToString)
                .doOnNext(report::setImageFileArrayString)
                .flatMap(s -> saveReportAtFireBase(report));
    }


    @Override
    public Observable<Boolean> updateReport(Report report) {
        return saveImage(report.getImageBitmap()).
                map(stringList -> {
                    stringList.addAll(report.getImageFileName());
                    return stringList;
                })
                .map(StringUtil::stringListToString)
                .doOnNext(report::setImageFileArrayString)
                .flatMap(s -> updateReportAtFireBase(report));
    }

    @Override
    public Observable<List<Report>> loadAllReport() {
        return Observable.create(subscriber -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            List<Report> reportList = new ArrayList<>();
            Query query = reference.child("report_list");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot issue : dataSnapshot.getChildren()) {
                            Report report = issue.getValue(Report.class);
                            Log.d(TAG, "report : " + report);
                            reportList.add(report);
                        }
                        subscriber.onNext(reportList);
                        subscriber.onComplete();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    subscriber.onError(new Throwable("canceled get report"));
                }
            });
        });
    }

    @Override
    public Observable<Boolean> deleteReport(String id) {
        return Observable.create(subscriber -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Task<Void> task = reference.child("report_list").child(id).child("isDelete").setValue(true);
            task.addOnFailureListener(subscriber::onError)
                    .addOnSuccessListener(aVoid -> {
                        subscriber.onNext(true);
                        subscriber.onComplete();
                    });
        });
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
            report.setId(DateUtil.getCurrentDateWithOutTimeWithOutDot() + UUID.randomUUID());
            postValues = report.toMap();
            childUpdates.put("/report_list/" + report.getId(), postValues);
            Task<Void> task = databaseReference.updateChildren(childUpdates);
            task.addOnFailureListener(e -> subscriber.onError(e)).addOnSuccessListener(aVoid -> {
                subscriber.onNext(true);
                subscriber.onComplete();
            });
        });
    }

    public Observable<Boolean> updateReportAtFireBase(Report report) {
        return Observable.create(subscriber -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> childUpdates = new HashMap<>();
            Map<String, Object> postValues = null;
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
            String fileName = DateUtil.getCurrentDateWithOutTimeWithOutDot() + UUID.randomUUID() + ".jpg";
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
