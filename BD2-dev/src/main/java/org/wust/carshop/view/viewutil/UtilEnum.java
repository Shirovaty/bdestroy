package org.wust.carshop.view.viewutil;

public enum UtilEnum {
    ALL_FILTER("Parts - full filter"),
    ALL_PARTS("All parts"),
    FILTER_BY_CAR("Parts filtered by car"),
    FILTER_BY_CAR_AND_MANU("Parts filtered by car and manufacturer"),
    FILTER_BY_CAR_AND_TYPE("Parts filtered by car and type"),
    ALL_TEMPLATES("All templates"),
    TEMPLATE_BY_NAME("Templates filtered by name"),
    POSITIONS("Positions"),
    ALL_EMPLOYEES("All employees"),
    EMPLOYEES_BY_FULL_NAME("Employees filtered by full name"),
    EMPLOYEES_BY_POSITION("Employees filtered by position"),
    EMPLOYEES_BY_FULL_NAME_AND_POSITION("Employees filtered by full name and position");

    public final String label;
    private UtilEnum(String label) {
        this.label = label;
    }
}
