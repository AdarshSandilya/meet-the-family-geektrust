package com.adarsh.geektrust.models;

import java.util.Arrays;

public enum RelationshipType {
    PATERNAL_UNCLE("PATERNAL-UNCLE"),
    MATERNAL_UNCLE("MATERNAL-UNCLE"),
    PATERNAL_AUNT("PATERNAL-AUNT"),
    MATERNAL_AUNT("MATERNAL-AUNT"),
    SISTER_IN_LAW("SISTER-IN-LAW"),
    BROTHER_IN_LAW("BROTHER-IN-LAW"),
    SON("SON"),
    DAUGHTER("DAUGHTER"),
    SIBLINGS("SIBLINGS");

    private final String type;

    RelationshipType(String type) {
        this.type = type;
    }

    public String getValue() {
        return type;
    }

    static public RelationshipType fromString(String value) {
        return Arrays.stream(RelationshipType.values()).filter(operator -> operator.getValue().equals(value.toUpperCase())).findFirst().orElse(null);
    }
}
