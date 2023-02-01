package org.wust.carshop.view;

import org.wust.carshop.service.MechanicService;
import org.wust.carshop.view.viewutil.AddPartsToRepair;
import org.wust.carshop.view.viewutil.CreateRepair;
import org.wust.carshop.view.viewutil.CreateZamowienie;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MechanicUI extends JFrame {

    private JPanel panel1;
    private JTable table1;
    private JTextField repairedField;
    private JTextField mechanicField;
    private JTextField startField;
    private JTextField endField;
    private JTextField priceField;
    private JButton finalizeSelectedButton;
    private JButton clearSelectionButton;
    private JButton addNewButton;
    private JButton createOrderButton;
    private JButton addToRepairButton;

    ListSelectionModel cellSelection = table1.getSelectionModel();

    ListSelectionListener tableListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int a = table1.getSelectedRow();
            int b = table1.getSelectedColumn();
            if(model.getColumnName(b).equals("repairedCar") || model.getColumnName(b).equals("mechanic")) {
                JOptionPane.showMessageDialog(null, table1.getValueAt(a,b).toString());
            }
        }
    };

    private DefaultTableModel model = new DefaultTableModel();

    public MechanicUI(MechanicService service) {
        setTitle("CarShopDB - mechanic");
        setContentPane(panel1);
        setSize(1000,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        table1.setDefaultEditor(Object.class, null);
        setModel(service.getActiveRepairs());
        table1.setModel(model);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelection.addListSelectionListener(tableListener);
        finalizeSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                service.finalizeRepair((int)table1.getValueAt(row,0));
                model.removeRow(row);
            }
        });

        addToRepairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                new AddPartsToRepair(service, (int)model.getValueAt(row, 0));
            }
        });
        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateRepair(service);
            }
        });
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateZamowienie(service);
            }
        });
        clearSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.clearSelection();
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
}
