package org.wust.carshop.model;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class Part {
    private Integer id;
    private final String carModel;
    private final String carBrand;
    private final String serialNumber;
    private final String producer;
    private final String category;
    private final Double price;

    @Override
    public String toString() {
        return  carModel +
                ", " + carBrand +
                ", " + producer  +
                ", " + category +
                ", " + serialNumber ;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
