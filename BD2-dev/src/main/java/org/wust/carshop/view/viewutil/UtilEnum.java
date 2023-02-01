package org.wust.carshop.view.viewutil;

public enum UtilEnum {
    ALL_PARTS("All parts"),
    ALL_FILTER("Parts - full filter"),
    FILTER_BY_CAR("Parts filtered by car"),
    FILTER_BY_CAR_AND_MANU("Parts filtered by car and manufacturer"),
    FILTER_BY_CAR_AND_TYPE("Parts filtered by car and type"),
    ALL_TEMPLATES("All templates"),
    TEMPLATE_WHERE_NAME("Templates filtered by regex"),
    ALL_EMPLOYEES("All employees"),
    EMPLOYEES_BY_FULL_NAME("Employees filtered by full name"),
    EMPLOYEES_BY_POSITION("Employees filtered by position"),
    EMPLOYEES_BY_FULL_NAME_AND_POSITION("Employees filtered by full name and position");

    public final String label;
    private UtilEnum(String label) {
        this.label = label;
    }
}
