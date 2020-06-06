package com.yunjaena.accident_management.data.network.entity;

import android.graphics.Bitmap;

import androidx.annotation.Keep;

import com.google.firebase.database.Exclude;
import com.yunjaena.accident_management.util.DateUtil;
import com.yunjaena.accident_management.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Keep
public class Report implements Serializable {
    public String id;
    public String saveDate;
    public String updateDate;
    public String companyName;
    public String accidentDate;
    public String expectStartDate;
    public String expectEndDate;
    public String realStartDate;
    public String realEndDate;
    public int constructType;
    public int constructDetailType;
    public int delayCauseOne;
    public int delayCauseDetailOne;
    public int delayCauseTwo;
    public int delayCauseDetailTwo;
    public int savePath;
    public String pictureDescribe;
    public String imageFileArrayString;
    @Exclude
    public List<String> imageFileName;
    @Exclude
    public List<Bitmap> imageBitmap;
    @Exclude
    private boolean isSelect;
    public boolean isDelete;

    public Report() {
    }

    public Report(Builder builder) {
        this.id = builder.id;
        this.saveDate = builder.saveDate;
        this.updateDate = builder.updateDate;
        this.companyName = builder.companyName;
        this.accidentDate = builder.accidentDate;
        this.realStartDate = builder.realStartDate;
        this.realEndDate = builder.realEndDate;
        this.expectStartDate = builder.expectStartDate;
        this.expectEndDate = builder.expectEndDate;
        this.constructType = builder.constructType;
        this.constructDetailType = builder.constructDetailType;
        this.pictureDescribe = builder.pictureDescribe;
        this.delayCauseOne = builder.delayCauseOne;
        this.delayCauseDetailOne = builder.delayCauseDetailOne;
        this.delayCauseTwo = builder.delayCauseTwo;
        this.delayCauseDetailTwo = builder.delayCauseDetailTwo;
        this.savePath = builder.savePath;
        this.imageFileArrayString = builder.imageFileArrayString;
        this.imageFileName = builder.imageFileName;
        this.imageBitmap = builder.imageBitmap;
        this.isDelete = builder.isDelete;
    }

    public Report(ReportSerial reportSerial){
        this.id = reportSerial.id;
        this.saveDate = reportSerial.saveDate;
        this.updateDate = reportSerial.updateDate;
        this.companyName = reportSerial.companyName;
        this.accidentDate = reportSerial.accidentDate;
        this.realStartDate = reportSerial.realStartDate;
        this.realEndDate = reportSerial.realEndDate;
        this.expectStartDate = reportSerial.expectStartDate;
        this.expectEndDate = reportSerial.expectEndDate;
        this.constructType = reportSerial.constructType;
        this.constructDetailType = reportSerial.constructDetailType;
        this.pictureDescribe = reportSerial.pictureDescribe;
        this.delayCauseOne = reportSerial.delayCauseOne;
        this.delayCauseDetailOne = reportSerial.delayCauseDetailOne;
        this.delayCauseTwo = reportSerial.delayCauseTwo;
        this.delayCauseDetailTwo = reportSerial.delayCauseDetailTwo;
        this.savePath = reportSerial.savePath;
        this.imageFileArrayString = reportSerial.imageFileArrayString;
        this.imageFileName = new ArrayList<>();
        this.imageBitmap = new ArrayList<>();
        this.isDelete = reportSerial.isDelete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(String accidentDate) {
        this.accidentDate = accidentDate;
    }

    public int getConstructType() {
        return constructType;
    }

    public void setConstructType(int constructType) {
        this.constructType = constructType;
    }

    public int getConstructDetailType() {
        return constructDetailType;
    }

    public void setConstructDetailType(int constructDetailType) {
        this.constructDetailType = constructDetailType;
    }

    public int getDelayCauseOne() {
        return delayCauseOne;
    }

    public void setDelayCauseOne(int delayCauseOne) {
        this.delayCauseOne = delayCauseOne;
    }

    public int getDelayCauseDetailOne() {
        return delayCauseDetailOne;
    }

    public void setDelayCauseDetailOne(int delayCauseDetailOne) {
        this.delayCauseDetailOne = delayCauseDetailOne;
    }

    public int getDelayCauseTwo() {
        return delayCauseTwo;
    }

    public void setDelayCauseTwo(int delayCauseTwo) {
        this.delayCauseTwo = delayCauseTwo;
    }

    public int getDelayCauseDetailTwo() {
        return delayCauseDetailTwo;
    }

    public void setDelayCauseDetailTwo(int delayCauseDetailTwo) {
        this.delayCauseDetailTwo = delayCauseDetailTwo;
    }

    public int getSavePath() {
        return savePath;
    }

    public void setSavePath(int savePath) {
        this.savePath = savePath;
    }

    public String getPictureDescribe() {
        return pictureDescribe;
    }

    public void setPictureDescribe(String pictureDescribe) {
        this.pictureDescribe = pictureDescribe;
    }

    public String getImageFileArrayString() {
        return imageFileArrayString;
    }

    public void setImageFileArrayString(String imageFileArrayString) {
        this.imageFileArrayString = imageFileArrayString;
    }

    public List<String> getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(List<String> imageFileName) {
        this.imageFileName = imageFileName;
    }

    public List<Bitmap> getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(List<Bitmap> imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }


    public String getRealStartDate() {
        return realStartDate;
    }

    public void setRealStartDate(String realStartDate) {
        this.realStartDate = realStartDate;
    }

    public String getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(String realEndDate) {
        this.realEndDate = realEndDate;
    }

    public String getExpectStartDate() {
        return expectStartDate;
    }

    public void setExpectStartDate(String expectStartDate) {
        this.expectStartDate = expectStartDate;
    }

    public List<String> getImageFileArray() {
        return StringUtil.stringToStringList(imageFileArrayString);
    }

    public String getExpectEndDate() {
        return expectEndDate;
    }


    public void setExpectEndDate(String expectEndDate) {
        this.expectEndDate = expectEndDate;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("saveDate", saveDate);
        result.put("updateDate", updateDate);
        result.put("companyName", companyName);
        result.put("accidentDate", accidentDate);
        result.put("expectStartDate", expectStartDate);
        result.put("expectEndDate", expectEndDate);
        result.put("realStartDate", realStartDate);
        result.put("realEndDate", realEndDate);
        result.put("constructType", constructType);
        result.put("constructDetailType", constructDetailType);
        result.put("delayCauseOne", delayCauseOne);
        result.put("delayCauseDetailOne", delayCauseDetailOne);
        result.put("delayCauseTwo", delayCauseTwo);
        result.put("delayCauseDetailTwo", delayCauseDetailTwo);
        result.put("pictureDescribe", pictureDescribe);
        result.put("savePath", savePath);
        result.put("imageFileArrayString", imageFileArrayString);
        result.put("isDelete", isDelete);
        return result;
    }


    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", saveDate='" + saveDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", companyName='" + companyName + '\'' +
                ", accidentDate='" + accidentDate + '\'' +
                ", expectStartDate='" + expectStartDate + '\'' +
                ", expectEndDate='" + expectEndDate + '\'' +
                ", realStartDate='" + realStartDate + '\'' +
                ", realEndDate='" + realEndDate + '\'' +
                ", constructType=" + constructType +
                ", constructDetailType=" + constructDetailType +
                ", delayCauseOne=" + delayCauseOne +
                ", delayCauseDetailOne=" + delayCauseDetailOne +
                ", delayCauseTwo=" + delayCauseTwo +
                ", delayCauseDetailTwo=" + delayCauseDetailTwo +
                ", savePath=" + savePath +
                ", pictureDescribe='" + pictureDescribe + '\'' +
                ", imageFileArrayString='" + imageFileArrayString + '\'' +
                ", imageFileName=" + imageFileName +
                ", imageBitmap=" + imageBitmap +
                ", isDelete=" + isDelete +
                '}';
    }

    public static class Builder {
        private String id = "";
        private String saveDate = DateUtil.getCurrentDate();
        private String updateDate = DateUtil.getCurrentDate();
        private String companyName = "";
        private String accidentDate = "";
        private String expectStartDate = "";
        private String expectEndDate = "";
        private String realStartDate = "";
        private String realEndDate = "";
        private int constructType = -1;
        private int constructDetailType = -1;
        private int delayCauseOne = -1;
        private int delayCauseDetailOne = -1;
        private int delayCauseTwo = -1;
        private int delayCauseDetailTwo = -1;
        private int savePath = -1;
        private String pictureDescribe = "";
        private String imageFileArrayString = "";
        private List<String> imageFileName = new ArrayList<>();
        private List<Bitmap> imageBitmap = new ArrayList<>();
        private boolean isDelete = false;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder saveDate(String saveDate) {
            this.saveDate = saveDate;
            return this;
        }

        public Builder updateDate(String updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public Builder expectStartDate(String expectStartDate) {
            this.expectStartDate = expectStartDate;
            return this;
        }

        public Builder expectEndDate(String expectEndDate) {
            this.expectEndDate = expectEndDate;
            return this;
        }

        public Builder realStartDate(String realStartDate) {
            this.realStartDate = realStartDate;
            return this;
        }

        public Builder realEndDate(String realEndDate) {
            this.realEndDate = realEndDate;
            return this;
        }

        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder accidentDate(String accidentDate) {
            this.accidentDate = accidentDate;
            return this;
        }

        public Builder constructType(int constructType) {
            this.constructType = constructType;
            return this;
        }

        public Builder constructDetailType(int constructDetailType) {
            this.constructDetailType = constructDetailType;
            return this;
        }

        public Builder delayCauseOne(int delayCauseOne) {
            this.delayCauseOne = delayCauseOne;
            return this;
        }

        public Builder delayCauseDetailOne(int delayCauseDetailOne) {
            this.delayCauseDetailOne = delayCauseDetailOne;
            return this;
        }

        public Builder delayCauseTwo(int delayCauseTwo) {
            this.delayCauseTwo = delayCauseTwo;
            return this;
        }

        public Builder delayCauseDetailTwo(int delayCauseDetailTwo) {
            this.delayCauseDetailTwo = delayCauseDetailTwo;
            return this;
        }

        public Builder savePath(int savePath) {
            this.savePath = savePath;
            return this;
        }

        public Builder pictureDescribe(String pictureDescribe) {
            this.pictureDescribe = pictureDescribe;
            return this;
        }

        public Builder imageFileName(List<String> imageFileName) {
            this.imageFileName = imageFileName;
            return this;
        }

        public Builder imageBitmap(List<Bitmap> imageBitmap) {
            this.imageBitmap = imageBitmap;
            return this;
        }

        public Builder imageFileArrayString(String imageFileArrayString) {
            this.imageFileArrayString = imageFileArrayString;
            return this;
        }

        public Builder isDelete(boolean isDelete) {
            this.isDelete = isDelete;
            return this;
        }

        public Report build() {
            return new Report(this);
        }

    }
}
