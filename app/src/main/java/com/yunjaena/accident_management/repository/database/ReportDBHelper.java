package com.yunjaena.accident_management.repository.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yunjaena.accident_management.repository.entity.Report;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Report.db";
    public static final String TABLE_NAME = "Report";
    public static final int DB_VERSION = 1;
    private static ReportDBHelper instance;
    private SQLiteDatabase sqLiteDatabase;

    public static ReportDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ReportDBHelper(context.getApplicationContext());
        }
        return instance;
    }


    private ReportDBHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DB_VERSION);
        try {
            sqLiteDatabase = this.getWritableDatabase();
        } catch (SQLException e) {
            sqLiteDatabase = this.getReadableDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(");
        stringBuffer.append("ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuffer.append("SAVE_DATE TEXT, ");
        stringBuffer.append("UPDATE_DATE TEXT, ");
        stringBuffer.append("COMPANY_NAME TEXT, ");
        stringBuffer.append("ACCIDENT_DATE TEXT,");
        stringBuffer.append("DELAY_CAUSE TEXT,");
        stringBuffer.append("SAVE_PATH TEXT,");
        stringBuffer.append("DESIGN_CHANGE_AND_ERROR TEXT,");
        stringBuffer.append("CONTRACT_CHANGE_AND_VIOLATION TEXT,");
        stringBuffer.append("INEVITABLE_CLAUSE TEXT,");
        stringBuffer.append("CONCURRENT_OCCURRENCE TEXT,");
        stringBuffer.append("IMAGE_FILE_NAME TEXT,");
        stringBuffer.append("IS_DELETE INTEGER");
        stringBuffer.append(");");
        db.execSQL(stringBuffer.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public int getLastId() {
        final String MY_QUERY = "SELECT MAX(ID) FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(MY_QUERY, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();
        return id;
    }


    public Report getReportById(int id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Report.Builder builder;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                builder = new Report.Builder();
                builder = builder.id(cursor.getInt(0));
                builder.saveDate(cursor.getString(1));
                builder.updateDate(cursor.getString(2));
                builder.companyName(cursor.getString(3));
                builder.accidentDate(cursor.getString(4));
                builder.delayCause(cursor.getString(5));
                builder.savePath(cursor.getString(6));
                builder.designChangeAndError(cursor.getString(6));
                builder.contractChangeAndViolation(cursor.getString(7));
                builder.inevitableClause(cursor.getString(8));
                builder.concurrentOccurrence(cursor.getString(9));
                builder.imageFileName(imageNamesStringToList(cursor.getString(10)));
                builder.isDelete(isDelete(cursor.getInt(11)));
            } while (cursor.moveToNext());
        } else {
            return new Report();
        }

        cursor.close();
        return builder.build();
    }


    public List<Report> getReportTotalList() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        List<Report> reportItemList = new ArrayList<>();
        Report.Builder builder;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                builder = new Report.Builder();
                builder = builder.id(cursor.getInt(0));
                builder.saveDate(cursor.getString(1));
                builder.updateDate(cursor.getString(2));
                builder.companyName(cursor.getString(3));
                builder.accidentDate(cursor.getString(4));
                builder.delayCause(cursor.getString(5));
                builder.savePath(cursor.getString(6));
                builder.designChangeAndError(cursor.getString(6));
                builder.contractChangeAndViolation(cursor.getString(7));
                builder.inevitableClause(cursor.getString(8));
                builder.concurrentOccurrence(cursor.getString(9));
                builder.imageFileName(imageNamesStringToList(cursor.getString(10)));
                builder.isDelete(isDelete(cursor.getInt(11)));
                reportItemList.add(builder.build());
            } while (cursor.moveToNext());
        } else {
            return reportItemList;
        }
        cursor.close();
        return reportItemList;
    }


    public List<Report> getValidReportList() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE IS_DELETE = " + 0 + " ORDER BY ID DESC;";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        List<Report> reportItemList = new ArrayList<>();
        Report.Builder builder;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                builder = new Report.Builder();
                builder = builder.id(cursor.getInt(0));
                builder.saveDate(cursor.getString(1));
                builder.updateDate(cursor.getString(2));
                builder.companyName(cursor.getString(3));
                builder.accidentDate(cursor.getString(4));
                builder.delayCause(cursor.getString(5));
                builder.savePath(cursor.getString(6));
                builder.designChangeAndError(cursor.getString(6));
                builder.contractChangeAndViolation(cursor.getString(7));
                builder.inevitableClause(cursor.getString(8));
                builder.concurrentOccurrence(cursor.getString(9));
                builder.imageFileName(imageNamesStringToList(cursor.getString(10)));
                builder.isDelete(isDelete(cursor.getInt(11)));
                reportItemList.add(builder.build());
            } while (cursor.moveToNext());
        } else {
            return reportItemList;
        }
        cursor.close();
        return reportItemList;
    }


    public boolean saveReport(Report reportItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SAVE_DATE", reportItem.getSaveDate());
        contentValues.put("UPDATE_DATE", reportItem.getUpdateDate());
        contentValues.put("COMPANY_NAME", reportItem.getCompanyName());
        contentValues.put("ACCIDENT_DATE", reportItem.getAccidentDate());
        contentValues.put("DELAY_CAUSE", reportItem.getDelayCause());
        contentValues.put("SAVE_PATH", reportItem.getSavePath());
        contentValues.put("DESIGN_CHANGE_AND_ERROR", reportItem.getDesignChangeAndError());
        contentValues.put("CONTRACT_CHANGE_AND_VIOLATION", reportItem.getContractChangeAndViolation());
        contentValues.put("INEVITABLE_CLAUSE", reportItem.getInevitableClause());
        contentValues.put("CONCURRENT_OCCURRENCE", reportItem.getConcurrentOccurrence());
        contentValues.put("IMAGE_FILE_NAME", imageNameListToString(reportItem.getImageFileName()));
        contentValues.put("IS_DELETE", isDelete(reportItem.isDelete()));
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean updateReport(Report reportItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SAVE_DATE", reportItem.getSaveDate());
        contentValues.put("UPDATE_DATE", reportItem.getUpdateDate());
        contentValues.put("COMPANY_NAME", reportItem.getCompanyName());
        contentValues.put("ACCIDENT_DATE", reportItem.getAccidentDate());
        contentValues.put("DELAY_CAUSE", reportItem.getDelayCause());
        contentValues.put("SAVE_PATH", reportItem.getSavePath());
        contentValues.put("DESIGN_CHANGE_AND_ERROR", reportItem.getDesignChangeAndError());
        contentValues.put("CONTRACT_CHANGE_AND_VIOLATION", reportItem.getContractChangeAndViolation());
        contentValues.put("INEVITABLE_CLAUSE", reportItem.getInevitableClause());
        contentValues.put("CONCURRENT_OCCURRENCE", reportItem.getConcurrentOccurrence());
        contentValues.put("IMAGE_FILE_NAME", imageNameListToString(reportItem.getImageFileName()));
        contentValues.put("IS_DELETE", isDelete(reportItem.isDelete()));
        long result = sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{String.valueOf(reportItem.getId())});
        if (result == -1)
            return false;
        else
            return true;
    }


    private String imageNameListToString(List<String> imageListId) {
        JSONArray jsonArray = new JSONArray(imageListId);
        return jsonArray.toString();
    }

    private List<String> imageNamesStringToList(String jsonArrayString) {
        List<String> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            String[] nameArray = new String[jsonArray.length()];
            for (int i = 0; i < nameArray.length; ++i) {
                nameArray[i] = jsonArray.optString(i);
            }
            list.addAll(Arrays.asList(nameArray));
            return list;
        } catch (JSONException e) {
            return list;
        }
    }

    /**
     * 지워졌는지 여부를 boolean 값으로 받는다.
     *
     * @param flag isDelete
     * @return isDelete
     */
    private boolean isDelete(int flag) {
        return flag == 1;
    }

    /**
     * 지워졌는지 여부를 int 값으로 받는다.
     *
     * @param flag isDelete
     * @return isDelete
     */
    private int isDelete(boolean flag) {
        return (flag) ? 1 : 0;
    }
}
