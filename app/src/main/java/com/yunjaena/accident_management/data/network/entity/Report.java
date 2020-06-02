package com.yunjaena.accident_management.data.network.entity;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;
import com.yunjaena.accident_management.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Report {
    private String id;
    private String saveDate;
    private String updateDate;
    private String companyName;
    private String accidentDate;
    private int constructType;
    private int constructDetailType;
    private int delayCauseOne;
    private int delayCauseDetailOne;
    private int delayCauseTwo;
    private int delayCauseDetailTwo;
    private int savePath;
    private String designChangeAndError;
    private String contractChangeAndViolation;
    private String inevitableClause;
    private String concurrentOccurrence;
    private String imageFileArrayString;
    private List<String> imageFileName;
    private List<Bitmap> imageBitmap;
    private boolean isDelete;

    public Report() {
    }


    public Report(Builder builder) {
        this.id = builder.id;
        this.saveDate = builder.saveDate;
        this.updateDate = builder.updateDate;
        this.companyName = builder.companyName;
        this.accidentDate = builder.accidentDate;
        this.constructType = builder.constructType;
        this.constructDetailType = builder.constructDetailType;
        this.designChangeAndError = builder.designChangeAndError;
        this.delayCauseOne = builder.delayCauseOne;
        this.delayCauseDetailOne = builder.delayCauseDetailOne;
        this.delayCauseTwo = builder.delayCauseTwo;
        this.delayCauseDetailTwo = builder.delayCauseDetailTwo;
        this.savePath = builder.savePath;
        this.contractChangeAndViolation = builder.contractChangeAndViolation;
        this.inevitableClause = builder.inevitableClause;
        this.concurrentOccurrence = builder.concurrentOccurrence;
        this.imageFileArrayString = builder.imageFileArrayString;
        this.imageFileName = builder.imageFileName;
        this.imageBitmap = builder.imageBitmap;
        this.isDelete = builder.isDelete;
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

    public String getContractChangeAndViolation() {
        return contractChangeAndViolation;
    }

    public void setContractChangeAndViolation(String contractChangeAndViolation) {
        this.contractChangeAndViolation = contractChangeAndViolation;
    }

    public String getInevitableClause() {
        return inevitableClause;
    }

    public void setInevitableClause(String inevitableClause) {
        this.inevitableClause = inevitableClause;
    }

    public String getConcurrentOccurrence() {
        return concurrentOccurrence;
    }

    public void setConcurrentOccurrence(String concurrentOccurrence) {
        this.concurrentOccurrence = concurrentOccurrence;
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

    public String getDesignChangeAndError() {
        return designChangeAndError;
    }

    public void setDesignChangeAndError(String designChangeAndError) {
        this.designChangeAndError = designChangeAndError;
    }

    public int getSavePath() {
        return savePath;
    }

    public void setSavePath(int savePath) {
        this.savePath = savePath;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("saveDate", saveDate);
        result.put("updateDate", updateDate);
        result.put("companyName", companyName);
        result.put("accidentDate", accidentDate);
        result.put("constructType", constructType);
        result.put("constructDetailType", constructDetailType);
        result.put("delayCauseOne", delayCauseOne);
        result.put("delayCauseDetailOne", delayCauseDetailOne);
        result.put("delayCauseTwo", delayCauseTwo);
        result.put("delayCauseDetailTwo", delayCauseDetailTwo);
        result.put("designChangeAndError", designChangeAndError);
        result.put("savePath", savePath);
        result.put("contractChangeAndViolation", contractChangeAndViolation);
        result.put("inevitableClause", inevitableClause);
        result.put("concurrentOccurrence", concurrentOccurrence);
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
                ", constructType=" + constructType +
                ", constructDetailType=" + constructDetailType +
                ", delayCauseOne=" + delayCauseOne +
                ", delayCauseDetailOne=" + delayCauseDetailOne +
                ", delayCauseTwo=" + delayCauseTwo +
                ", delayCauseDetailTwo=" + delayCauseDetailTwo +
                ", savePath=" + savePath +
                ", designChangeAndError='" + designChangeAndError + '\'' +
                ", contractChangeAndViolation='" + contractChangeAndViolation + '\'' +
                ", inevitableClause='" + inevitableClause + '\'' +
                ", concurrentOccurrence='" + concurrentOccurrence + '\'' +
                ", imageFileArrayString='" + imageFileArrayString + '\'' +
                ", imageFileName=" + imageFileName +
                ", imageBitmap=" + imageBitmap +
                ", isDelete=" + isDelete +
                '}';
    }

    public static class Builder {
        private String id = "";
        private String saveDate = DateUtil.getCurrentDateWithOutTime();
        private String updateDate = DateUtil.getCurrentDateWithOutTime();
        private String companyName = "";
        private String accidentDate = DateUtil.getCurrentDateWithOutTime();
        private int constructType = -1;
        private int constructDetailType = -1;
        private int delayCauseOne = -1;
        private int delayCauseDetailOne = -1;
        private int delayCauseTwo = -1;
        private int delayCauseDetailTwo = -1;
        private int savePath = -1;
        private String designChangeAndError = "";
        private String contractChangeAndViolation = "";
        private String inevitableClause = "";
        private String concurrentOccurrence = "";
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

        public Builder designChangeAndError(String designChangeAndError) {
            this.designChangeAndError = designChangeAndError;
            return this;
        }

        public Builder contractChangeAndViolation(String contractChangeAndViolation) {
            this.contractChangeAndViolation = contractChangeAndViolation;
            return this;
        }

        public Builder inevitableClause(String inevitableClause) {
            this.inevitableClause = inevitableClause;
            return this;
        }

        public Builder concurrentOccurrence(String concurrentOccurrence) {
            this.concurrentOccurrence = concurrentOccurrence;
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
