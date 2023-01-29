package org.wust.carshop.service;

import lombok.AllArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.wust.carshop.mapper.PartMapper;
import org.wust.carshop.mapper.PartPairMapper;
import org.wust.carshop.mapper.RepairTemplateMapper;
import org.wust.carshop.model.Part;
import org.wust.carshop.model.RepairTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.wust.carshop.query.GetQueries.*;
import static org.wust.carshop.query.GetQueries.GET_PARTS_BY_MANUFACTURER_AND_CAR;

@AllArgsConstructor
public class UtilsService {
    private final Jdbi dbHandler;

    public List<Part> getAllParts() {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_ALL_PARTS)
                        .map(new PartMapper())
                        .list()
        );
    }

    public List<Part> getPartsByFullFilter(String carModel, String carBrand,
                                               String manufacturer, String type) {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_PARTS_BY_MANUFACTURER_AND_TYPE_AND_CAR)
                        .bind("type", type)
                        .bind("carModel", carModel)
                        .bind("carBrand", carBrand)
                        .bind("manufacturer", manufacturer)
                        .map(new PartMapper())
                        .list()
        );
    }

    public List<Part> getPartsByCar(String carModel, String carBrand) {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_PARTS_BY_CAR)
                        .bind("carModel", carModel)
                        .bind("carBrand", carBrand)
                        .map(new PartMapper())
                        .list()
        );
    }

    public List<Part> getPartsByCarAndType(String carModel,
                                               String carBrand, String type) {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_PARTS_BY_TYPE_AND_CAR)
                        .bind("carBrand", carBrand)
                        .bind("carModel", carModel)
                        .bind("type", type)
                        .map(new PartMapper())
                        .list()
        );
    }

    public List<Part> getPartsByCarAndManufacturer(String carModel,
                                                       String carBrand, String manufacturer) {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_PARTS_BY_MANUFACTURER_AND_CAR)
                        .bind("carBrand", carBrand)
                        .bind("carModel", carModel)
                        .bind("manufacturer", manufacturer)
                        .map(new PartMapper())
                        .list()
        );
    }

    public List<RepairTemplate> getAllRepairTemplates() {
        var templateSignatures = dbHandler.withHandle(handle ->
                handle.createQuery(GET_ALL_TEMPLATES)
                        .map(new RepairTemplateMapper())
                        .list()
        );
        List<RepairTemplate> fullTemplates = new ArrayList<>();

        templateSignatures.forEach(template -> {
            var parts = dbHandler.withHandle(handle -> handle.createQuery(GET_PARTS_FOR_TEMPLATE)
                    .bind("templateId", template.getId())
                    .map(new PartPairMapper())
                    .list()
            );

            template.setRequiredParts(parts);
            fullTemplates.add(template);
        });

        return fullTemplates;
    }

    public RepairTemplate getRepairTemplateByName(String name) {
        var template = dbHandler.withHandle(handle ->
                handle.createQuery(GET_TEMPLATE_BY_NAME)
                        .bind("name", name)
                        .map(new RepairTemplateMapper())
                        .one()
        );


        var parts = dbHandler.withHandle(handle -> handle.createQuery(GET_PARTS_FOR_TEMPLATE)
                .bind("templateId", template.getId())
                .map(new PartPairMapper())
                .list()
        );

        template.setRequiredParts(parts);


        return template;
    }
    public List<String> getTypes() {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_PART_TYPES).mapTo(String.class)
                        .list()
        );
    }
    public List<String> getMarka() {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_CAR_MANUFACTURERS).mapTo(String.class)
                        .list()
        );
    }

    public List<String> getModel() {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_CAR_MODELS).mapTo(String.class)
                        .list()
        );
    }

    public List<String> getProducent() {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_PART_PRODUCERS).mapTo(String.class)
                        .list()
        );
    }

    public List<String> getTemplatesNames() {
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_TEMPLATE_NAMES).mapTo(String.class)
                        .list()
        );
    }
    public List<Part> getPartByID(String id){
        return dbHandler.withHandle(handle ->
                handle.createQuery(GET_PART_BY_ID).bind("name", id)
                        .map(new PartMapper())
                        .list()

        );
    }

}