package com.yunjaena.accident_management.ui.resgister;

public enum DelayCause {
    MATERIAL(0, true, "delay_cause_specific_0"),
    LABOR(1, true, "delay_cause_specific_1"),
    EQUIPMENT(2, true, "delay_cause_specific_2"),
    CONSTRUCTION_METHOD(3, true, "delay_cause_specific_3"),
    FUND(4, true, "delay_cause_specific_4"),
    MANAGEMENT(5, true, "delay_cause_specific_5"),
    ORDER_PERSON(6, true, "delay_cause_specific_6"),
    DESIGN_AND_ADMINISTRATION(7, true, "delay_cause_specific_7"),
    THE_THIRD_PERSON(8, true, "delay_cause_specific_8");


    int index;
    boolean hasChildStringArray;
    String childStringArrayName;

    DelayCause(int idx, boolean hasChild, String childStringArrayResourceName) {
        this.index = idx;
        this.hasChildStringArray = hasChild;
        this.childStringArrayName = childStringArrayResourceName;
    }

    public int getIndex() {
        return index;
    }

    public boolean isHasChildStringArray() {
        return hasChildStringArray;
    }

    public String getChildStringArrayName() {
        return childStringArrayName;
    }

    public static boolean hasChild(int index) {
        for (DelayCause delayCause : values()) {
            if (delayCause.getIndex() == index)
                return delayCause.hasChildStringArray;
        }
        return false;
    }

    public static DelayCause getDelayCauseWithIndex(int index) {
        for (DelayCause delayCause : values()) {
            if (delayCause.getIndex() == index)
                return delayCause;
        }
        return null;
    }

    public static String childArrayResourceId(int index) {
        if (hasChild(index) && getDelayCauseWithIndex(index) != null) {
            return getDelayCauseWithIndex(index).getChildStringArrayName();
        } else {
            return null;
        }
    }
}
