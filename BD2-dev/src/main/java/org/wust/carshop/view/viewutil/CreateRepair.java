package org.wust.carshop.view.viewutil;

import org.wust.carshop.model.Address;
import org.wust.carshop.model.Car;
import org.wust.carshop.model.Client;
import org.wust.carshop.model.RepairTemplate;
import org.wust.carshop.service.MechanicService;
import org.wust.carshop.view.LoginUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateRepair extends JFrame{
    private JComboBox<String> kolorBox;
    private JComboBox<String> modelBox;
    private JComboBox<String > markaBox;
    private JTextField vinField;
    private JPanel panel1;
    private JTextField imieField;
    private JTextField nazwiskoField;
    private JTextField miastoField;
    private JTextField kodField;
    private JTextField ulicaField;
    private JTextField budynekField;
    private JTextField mieszkanieField;
    private  JButton dodajNaprawe;
    private JTextField rokField;
    private JCheckBox templateCheckbox;
    private JComboBox<RepairTemplate> templateBox;

    boolean isTemplate = false;

    public CreateRepair(MechanicService service) {
        setTitle("CarShopDB - create repair");
        setContentPane(panel1);
        setSize(1000,800);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        markaBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelBox.setModel(new DefaultComboBoxModel(LoginUI.us.getModelsByBrand(markaBox.getSelectedItem().toString()).toArray()));
            }
        });
        markaBox.setModel(new DefaultComboBoxModel<>(service.getCarBrands().toArray(String[]::new)));
        kolorBox.setModel(new DefaultComboBoxModel<>(service.getCarColors().toArray(String[]::new)));
        templateBox.setModel(new DefaultComboBoxModel<>(service.getAllRepairTemplates().toArray(RepairTemplate[]::new)));
        templateBox.setEnabled(false);
        templateBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,templateBox.getSelectedItem());
            }
        });
        templateCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!templateCheckbox.isSelected()) {
                    templateBox.setEnabled(false);
                    isTemplate = false;
                }
                if(templateCheckbox.isSelected()){
                    templateBox.setEnabled(true);
                    isTemplate = true;
                }
            }
        });


        dodajNaprawe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //try {
                    Address address = Address.builder()
                            .city(miastoField.getText())
                            .postalCode(kodField.getText())
                            .street(ulicaField.getText())
                            .buildingNumber(budynekField.getText())
                            .apartment(Integer.valueOf(mieszkanieField.getText()))
                            .build();
                    address.setId(service.getAddressId(address));
                    Client client = Client.builder()
                            .name(imieField.getText())
                            .surname(nazwiskoField.getText())
                            .address(address)
                            .build();
                    client.setId(service.getClientId(client));
                    Car car = Car.builder()
                            .owner(client)
                            .brand(markaBox.getSelectedItem().toString())
                            .model(modelBox.getSelectedItem().toString())
                            .color(kolorBox.getSelectedItem().toString())
                            .VIN(vinField.getText())
                            .productionYear(Integer.valueOf(rokField.getText()))
                            .build();
                    if (!service.clientExists(client)) {
                        service.createClient(client);
                    }
                    if (!service.carExists(vinField.getText())) {
                        service.createCar(car);
                    }
                    if(isTemplate){
                        service.createRepairFromTemplate((RepairTemplate) templateBox.getSelectedItem(), car);
                    } else {
                        service.createRepair(car);
                    }
                    JOptionPane.showMessageDialog(null, "Car added successfully");
                    setVisible(false);
                    dispose();
                //} catch (Exception exd) {
                   // JOptionPane.showMessageDialog(null, "Error");
                //}
            }

        });


    }

}
