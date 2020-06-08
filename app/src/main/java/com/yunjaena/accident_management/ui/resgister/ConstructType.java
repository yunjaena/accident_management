package com.yunjaena.accident_management.ui.resgister;

public enum ConstructType {
    TEMPORARY_WORK(0, false, null),
    EXCAVATION_AND_FOUNDATION(1, false, null),
    REINFORCED_CONCRETE_STRUCTURE(2, true, "construction_specific_type_2"),
    STEEL_STRUCTURE_CONSTRUCTION(3, false, null),
    MASONRY_CONSTRUCTION(4, true, "construction_specific_type_4"),
    WOOD_WORK(5, false, null),
    OTHER_CONSTRUCTION(6, true, "construction_specific_type_6"),
    OTHER_WORKS(7, false, null);


    int index;
    boolean hasChildStringArray;
    String childStringArrayName;

    ConstructType(int idx, boolean hasChild, String childStringArrayResourceName) {
        this.index = idx;
        this.hasChildStringArray = hasChild;
        this.childStringArrayName = childStringArrayResourceName;
    }

    public static boolean hasChild(int index) {
        for (ConstructType constructType : values()) {
            if (constructType.getIndex() == index)
                return constructType.hasChildStringArray;
        }
        return false;
    }

    public static ConstructType getConstructTypeWithIndex(int index) {
        for (ConstructType constructType : values()) {
            if (constructType.getIndex() == index)
                return constructType;
        }
        return null;
    }

    public static String childArrayResourceId(int index) {
        if (hasChild(index) && getConstructTypeWithIndex(index) != null) {
            return getConstructTypeWithIndex(index).getChildStringArrayName();
        } else {
            return null;
        }
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


}
