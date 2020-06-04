package com.yunjaena.accident_management.data.network.entity;

import com.yunjaena.accident_management.util.StringUtil;

import java.io.Serializable;
import java.util.List;

public class ReportSerial implements Serializable {
    public String id;
    public String saveDate;
    public String updateDate;
    public String companyName;
    public String accidentDate;
    public String realStartDate;
    public String realEndDate;
    public int constructType;
    public int constructDetailType;
    public int delayCauseOne;
    public int delayCauseDetailOne;
    public int delayCauseTwo;
    public int delayCauseDetailTwo;
    public int savePath;
    public String designChangeAndError;
    public String contractChangeAndViolation;
    public String inevitableClause;
    public String concurrentOccurrence;
    public String imageFileArrayString;
    public boolean isDelete;

    public ReportSerial() {
    }

    public ReportSerial(Report report) {
        this.id = report.id;
        this.saveDate = report.saveDate;
        this.updateDate = report.updateDate;
        this.companyName = report.companyName;
        this.accidentDate = report.accidentDate;
        this.realStartDate = report.realStartDate;
        this.realEndDate = report.realEndDate;
        this.constructType = report.constructType;
        this.constructDetailType = report.constructDetailType;
        this.designChangeAndError = report.designChangeAndError;
        this.delayCauseOne = report.delayCauseOne;
        this.delayCauseDetailOne = report.delayCauseDetailOne;
        this.delayCauseTwo = report.delayCauseTwo;
        this.delayCauseDetailTwo = report.delayCauseDetailTwo;
        this.savePath = report.savePath;
        this.contractChangeAndViolation = report.contractChangeAndViolation;
        this.inevitableClause = report.inevitableClause;
        this.concurrentOccurrence = report.concurrentOccurrence;
        this.imageFileArrayString = report.imageFileArrayString;
        this.isDelete = report.isDelete;
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

    public String getImageFileArrayString() {
        return imageFileArrayString;
    }

    public void setImageFileArrayString(String imageFileArrayString) {
        this.imageFileArrayString = imageFileArrayString;
    }

    public List<String> getImageFileArray() {
        return StringUtil.stringToStringList(imageFileArrayString);
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
