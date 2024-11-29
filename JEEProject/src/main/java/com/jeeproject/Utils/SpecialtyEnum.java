package com.jeeproject.Utils;

public enum SpecialtyEnum {
    COMPUTER_SCIENCE("Computer Science"),
    MATHEMATICS("Mathematics"),
    PHYSICS("Physics"),
    CHEMISTRY("Chemistry"),
    BIOLOGY("Biology"),
    ENGINEERING("Engineering"),
    ECONOMICS("Economics"),
    LITERATURE("Literature"),
    PHILOSOPHY("Philosophy"),
    HISTORY("History");

    private final String displayName;


    SpecialtyEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
