package com.yunjaena.accident_management.repository.entity;

import android.graphics.Bitmap;

import com.yunjaena.accident_management.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private int id;
    private String saveDate;
    private String updateDate;
    private String companyName;
    private String accidentDate;
    private String constructType;
    private String delayCause;
    private String savePath;
    private String designChangeAndError;
    private String contractChangeAndViolation;
    private String inevitableClause;
    private String concurrentOccurrence;
    private List<String> imageFileName;
    private List<Bitmap> imageBitmap;
    private boolean isDelete;

    public Report() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConstructType() {
        return constructType;
    }

    public void setConstructType(String constructType) {
        this.constructType = constructType;
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

    public String getDelayCause() {
        return delayCause;
    }

    public void setDelayCause(String delayCause) {
        this.delayCause = delayCause;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getDesignChangeAndError() {
        return designChangeAndError;
    }

    public void setDesignChangeAndError(String designChangeAndError) {
        this.designChangeAndError = designChangeAndError;
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

    public List<String> getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(List<String> imageFileName) {
        this.imageFileName = imageFileName;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
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

    public List<Bitmap> getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(List<Bitmap> imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", saveDate='" + saveDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", companyName='" + companyName + '\'' +
                ", accidentDate='" + accidentDate + '\'' +
                ", constructType='" + constructType + '\'' +
                ", delayCause='" + delayCause + '\'' +
                ", savePath='" + savePath + '\'' +
                ", designChangeAndError='" + designChangeAndError + '\'' +
                ", contractChangeAndViolation='" + contractChangeAndViolation + '\'' +
                ", inevitableClause='" + inevitableClause + '\'' +
                ", concurrentOccurrence='" + concurrentOccurrence + '\'' +
                ", imageFileName=" + imageFileName +
                ", imageBitmap=" + imageBitmap +
                ", isDelete=" + isDelete +
                '}';
    }

    public static class Builder {
        private int id;
        private String saveDate = DateUtil.getCurrentDate();
        private String updateDate = DateUtil.getCurrentDate();
        private String companyName = "";
        private String accidentDate = "";
        private String delayCause = "";
        private String constructType = "";
        private String savePath = "";
        private String designChangeAndError = "";
        private String contractChangeAndViolation = "";
        private String inevitableClause = "";
        private String concurrentOccurrence = "";
        private List<String> imageFileName = new ArrayList<>();
        private List<Bitmap> imageBitmap = new ArrayList<>();
        private boolean isDelete = false;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder saveDate(String saveDate) {
            this.saveDate = saveDate;
            return this;
        }

        public Builder constructType(String constructType) {
            this.constructType = constructType;
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

        public Builder delayCause(String delayCause) {
            this.delayCause = delayCause;
            return this;
        }

        public Builder savePath(String savePath) {
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

        public Builder isDelete(boolean isDelete) {
            this.isDelete = isDelete;
            return this;
        }

        public Builder imageFileName(List<String> imageFileName) {
            this.imageFileName = imageFileName;
            return this;
        }

        public Builder imageBitmap(List<Bitmap> imageBitmap) {
            this.imageBitmap.addAll(imageBitmap);
            return this;
        }

        public Report build() {
            return new Report(this);
        }


    }

    private Report(Builder builder) {
        this.id = builder.id;
        this.saveDate = builder.saveDate;
        this.updateDate = builder.updateDate;
        this.companyName = builder.companyName;
        this.accidentDate = builder.accidentDate;
        this.delayCause = builder.delayCause;
        this.constructType = builder.constructType;
        this.savePath = builder.savePath;
        this.designChangeAndError = builder.designChangeAndError;
        this.contractChangeAndViolation = builder.contractChangeAndViolation;
        this.inevitableClause = builder.inevitableClause;
        this.concurrentOccurrence = builder.concurrentOccurrence;
        this.imageFileName = builder.imageFileName;
        this.imageBitmap = builder.imageBitmap;
        this.isDelete = builder.isDelete;
    }
}
