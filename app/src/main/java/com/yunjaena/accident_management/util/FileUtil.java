package com.yunjaena.accident_management.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import org.apache.poi.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    public static Uri getFilePathFromUri(Uri uri, Context context) throws IOException {
        String fileName = getFileName(uri,context);
        File file = new File(context.getExternalCacheDir(), fileName);
        file.createNewFile();
        try (OutputStream outputStream = new FileOutputStream(file);
             InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
        return Uri.fromFile(file);
    }

    public static String getFileName(Uri uri, Context context) {
        String fileName = getFileNameFromCursor(uri, context);
        if (fileName == null) {
            String fileExtension = getFileExtension(uri, context);
            fileName = "temp_file" + (fileExtension != null ? "." + fileExtension : "");
        } else if (!fileName.contains(".")) {
            String fileExtension = getFileExtension(uri, context);
            fileName = fileName + "." + fileExtension;
        }
        return fileName;
    }

    public static String getFileExtension(Uri uri, Context context) {
        String fileType = context.getContentResolver().getType(uri);
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType);
    }

    public static String getFileNameFromCursor(Uri uri, Context context) {
        Cursor fileCursor =  context.getContentResolver().query(uri, new String[]{OpenableColumns.DISPLAY_NAME}, null, null, null);
        String fileName = null;
        if (fileCursor != null && fileCursor.moveToFirst()) {
            int cIndex = fileCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (cIndex != -1) {
                fileName = fileCursor.getString(cIndex);
            }
        }
        if (fileCursor != null)
            fileCursor.close();
        return fileName;
    }
}
