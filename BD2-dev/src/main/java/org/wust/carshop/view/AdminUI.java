package org.wust.carshop.view;

import org.wust.carshop.service.AdminService;
import org.wust.carshop.view.viewutil.AdminAddForm;
import org.wust.carshop.view.viewutil.ComboboxEnumRenderer;
import org.wust.carshop.view.viewutil.UtilEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AdminUI extends JFrame{

    private JPanel panel1;
    private JTable table1;

    JFrame frame;
    private JComboBox<UtilEnum> comboBox1;
    private JComboBox modelBox;
    private JComboBox markaBox;
    private JComboBox producentBox;
    private JComboBox typBox;
    private JComboBox imieBox;
    private JComboBox nazwiskoBox;
    private JComboBox stanowiskoBox;
    private JButton filterButton;
    private JButton addButton;
    private JButton deleteSelectedButton;
    private JTextField wzoryField;
    private DefaultTableModel model = new DefaultTableModel();
    private boolean deleteBool;
    AdminService service;
    ActionListener allPartsListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] filterValues = getValues();
            setModel(service.getPartsByFullFilter(filterValues[0], filterValues[1], filterValues[2], filterValues[3]));
            table1.setModel(model);
        }
    };
    ActionListener filterByCarListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] filterValues = getValues();
            setModel(service.getPartsByCar(filterValues[0], filterValues[1]));
            table1.setModel(model);
        }
    };
    ActionListener filterByCarAndManufacturerListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] filterValues = getValues();
            setModel(service.getPartsByCarAndManufacturer(filterValues[0], filterValues[1], filterValues[2]));
            table1.setModel(model);
        }
    };
    ActionListener filterByCarAndTypeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] filterValues = getValues();
            setModel(service.getPartsByCarAndType(filterValues[0], filterValues[1], filterValues[2]));
            table1.setModel(model);
        }
    };
    ActionListener templateWhereNameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setModel(List.of(service.getRepairTemplateWhereName(wzoryField.getText())));
            table1.setModel(model);
        }
    };
    ActionListener employeesByFullNameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] filterValues = getValues();
            setModel(service.getEmployeesByFullName(filterValues[0], filterValues[1]));
            table1.setModel(model);
        }
    };

    ActionListener employeesByPositionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] filterValues = getValues();
            setModel(service.getEmployeesByPosition(filterValues[0]));
            table1.setModel(model);
        }
    };

    ActionListener employeesByFullNameAndPositionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] filterValues = getValues();
            setModel(service.getEmployeeByFullNameAndPosition(filterValues[0], filterValues[1], filterValues[2]));
            table1.setModel(model);
        }
    };

    ListSelectionModel cellSelection = table1.getSelectionModel();

    ListSelectionListener tableListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int a = table1.getSelectedRow();
            int b = table1.getSelectedColumn();
            if(model.getColumnName(b).equals("requiredParts")) {
                JOptionPane.showMessageDialog(null, table1.getValueAt(a,b).toString());
            }

        }
    };

    ActionListener previousActionListener;



    public AdminUI(AdminService service, JFrame frame) {
        this.frame = frame;
        this.service = service;
        setTitle("CarShopDB - admin");
        comboBox1.setModel(new DefaultComboBoxModel<UtilEnum>(UtilEnum.values()));
        comboBox1.setRenderer(new ComboboxEnumRenderer());
        setContentPane(panel1);
        setSize(1000,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        resetAccess();
        table1.setDefaultEditor(Object.class, null);
        setModel(service.getAllParts());
        table1.setModel(model);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelection.addListSelectionListener(tableListener);
        addButton.setMultiClickThreshhold(1000);


        markaBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelBox.setModel(new DefaultComboBoxModel(LoginUI.us.getModelsByBrand(markaBox.getSelectedItem().toString()).toArray()));
            }
        });

        comboBox1.addActionListener(e -> {
            resetAccess();
            filterButton.removeActionListener(previousActionListener);
            switch ((UtilEnum)comboBox1.getSelectedItem()) {
                case ALL_PARTS:
                    setModel(service.getAllParts());
                    table1.setModel(model);
                    break;
                case POSITIONS:
                    setModel(service.getAllPositions());
                    table1.setModel(model);
                case ALL_TEMPLATES:
                    setModel(service.getAllRepairTemplates());
                    table1.setModel(model);
                    deleteSelectedButton.setEnabled(true);
                    break;
                case ALL_EMPLOYEES:
                    setModel(service.getAllEmployees());
                    table1.setModel(model);
                    deleteBool = false;
                    deleteSelectedButton.setEnabled(true);
                    break;
                case ALL_FILTER:
                    previousActionListener = allPartsListener;
                    setComboboxes(true, true, true, true, false, false, false);
                    filterButton.addActionListener(allPartsListener);
                    break;
                case FILTER_BY_CAR:
                    previousActionListener = filterByCarListener;
                    setComboboxes(false, true, true, false,  false, false, false);
                    filterButton.addActionListener(filterByCarListener);
                    break;
                case FILTER_BY_CAR_AND_MANU:
                    previousActionListener = filterByCarAndManufacturerListener;
                    setComboboxes(true, true, true, false,  false, false, false);
                    filterButton.addActionListener(filterByCarAndManufacturerListener);
                    break;
                case FILTER_BY_CAR_AND_TYPE:
                    previousActionListener = filterByCarAndTypeListener;
                    setComboboxes(false, true, true, true,  false, false, false);
                    filterButton.addActionListener(filterByCarAndTypeListener);
                    break;
                case TEMPLATE_WHERE_NAME:
                    previousActionListener = templateWhereNameListener;
                    wzoryField.setEnabled(true);
                    filterButton.setEnabled(true);
                    deleteSelectedButton.setEnabled(true);
                    filterButton.addActionListener(templateWhereNameListener);
                    break;
                case EMPLOYEES_BY_FULL_NAME:
                    previousActionListener = employeesByFullNameListener;
                    setComboboxes(false, false, false, false,  true, true, false);
                    deleteSelectedButton.setEnabled(true);
                    filterButton.addActionListener(employeesByFullNameListener);
                    break;
                case EMPLOYEES_BY_POSITION:
                    previousActionListener = employeesByPositionListener;
                    setComboboxes(false, false, false, false,  false, false, true);
                    deleteSelectedButton.setEnabled(true);
                    filterButton.addActionListener(employeesByPositionListener);
                    break;
                case EMPLOYEES_BY_FULL_NAME_AND_POSITION:
                    previousActionListener = employeesByFullNameAndPositionListener;
                    setComboboxes(false, false, false, false,  true, true, true);
                    deleteSelectedButton.setEnabled(true);
                    filterButton.addActionListener(employeesByFullNameAndPositionListener);
                    break;

            }

        });
        deleteSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var item = comboBox1.getSelectedItem();
                if(item== UtilEnum.ALL_EMPLOYEES ||
                        item == UtilEnum.EMPLOYEES_BY_POSITION ||
                        item == UtilEnum.EMPLOYEES_BY_FULL_NAME ||
                        item == UtilEnum.EMPLOYEES_BY_FULL_NAME_AND_POSITION) {
                        var row = table1.getSelectedRow();
                        var vector = model.getDataVector().get(row);
                        service.deleteEmployee((Integer)vector.get(0));
                        model.removeRow(row);
                }
                if(item == UtilEnum.ALL_TEMPLATES || item == UtilEnum.TEMPLATE_WHERE_NAME) {
                    var row = table1.getSelectedRow();
                    var vector = model.getDataVector().get(row);
                    service.deleteRepairTemplate((Integer)vector.get(0));
                    model.removeRow(row);
                }
            }
        });
    }

    <E> void setModel(List<E> list) {
        model = new DefaultTableModel();
        List<String> headers = new ArrayList<>();
        Field[] fields = new Field[1];
        try{
            fields = list.get(0).getClass().getDeclaredFields();
        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "No record found");
        }
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        for (Field field : fields) {
            headers.add(field.getName());
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminAddForm(service);
            }
        });
        model.setColumnIdentifiers(headers.toArray());
        for(E item : list) {
            Object temp = null;
            List<Object> dataArray = new ArrayList<>();
            for(Field field: fields) {
                field.setAccessible(true);
                try{
                    dataArray.add(field.get(item));
                } catch (IllegalAccessException e) {
                    System.out.println(e);
                }
            }
            model.addRow(dataArray.toArray());
        }
    }

    public String[] getValues() {
        String[] output = new String[4];
        int count = 0;
        if (modelBox.isEnabled()) {
            output[count] = modelBox.getSelectedItem().toString();
            count++;
        }
        if(markaBox.isEnabled()) {
            output[count] = markaBox.getSelectedItem().toString();
            count++;
        }
        if(producentBox.isEnabled()) {
            output[count] = producentBox.getSelectedItem().toString();
            count++;
        }
        if(typBox.isEnabled()) {
            output[count] = typBox.getSelectedItem().toString();
            count++;
        }
        if(imieBox.isEnabled()) {
            output[count] = imieBox.getSelectedItem().toString();
            count++;
        }
        if (nazwiskoBox.isEnabled()) {
            output[count] = nazwiskoBox.getSelectedItem().toString();
            count++;
        }
        if (stanowiskoBox.isEnabled()) {
            output[count] = stanowiskoBox.getSelectedItem().toString();
            count++;
        }

        return output;
    }

    private void setComboboxes(boolean isProducent,
                               boolean isMarka,
                               boolean isModel,
                               boolean isTyp,
                               boolean isImie,
                               boolean isNazwisko,
                               boolean isStanowisko){
        resetAccess();
        filterButton.setEnabled(true);
        if (isProducent) {
            producentBox.setEnabled(true);
            producentBox.setModel(new DefaultComboBoxModel(LoginUI.us.getProducent().toArray()));

        }
        if (isMarka) {
            markaBox.setEnabled(true);
            markaBox.setModel(new DefaultComboBoxModel(LoginUI.us.getMarka().toArray()));
        }
        if(isModel) {
            if(isMarka){
                modelBox.setEnabled(true);
                modelBox.setModel(new DefaultComboBoxModel(LoginUI.us.getModelsByBrand(markaBox.getSelectedItem().toString()).toArray()));
            }else{
                modelBox.setEnabled(true);
                modelBox.setModel(new DefaultComboBoxModel(LoginUI.us.getModel().toArray()));
            }
        }
        if(isTyp) {
            typBox.setEnabled(true);
            typBox.setModel(new DefaultComboBoxModel(LoginUI.us.getTypes().toArray()));
        }
        if (isImie) {
            imieBox.setEnabled(true);
            imieBox.setModel(new DefaultComboBoxModel(LoginUI.us.getPracownicyNames().toArray()));
        }
        if (isNazwisko) {
            nazwiskoBox.setEnabled(true);
            nazwiskoBox.setModel(new DefaultComboBoxModel(LoginUI.us.getPracownicySurnames().toArray()));
        }
        if(isStanowisko) {
            stanowiskoBox.setEnabled(true);
            stanowiskoBox.setModel(new DefaultComboBoxModel(service.getAllPositions().toArray()));
        }

    }

    private void resetAccess(){
        producentBox.setEnabled(false);
        markaBox.setEnabled(false);
        modelBox.setEnabled(false);
        typBox.setEnabled(false);
        imieBox.setEnabled(false);
        nazwiskoBox.setEnabled(false);
        stanowiskoBox.setEnabled(false);
        filterButton.setEnabled(false);
        deleteSelectedButton.setEnabled(false);
        wzoryField.setEnabled(false);
    }




}
