/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import lk.amc.controller.SensorController;
import lk.amc.dto.Sensor;

/**
 *
 * @author Dakshika
 */
public class SensorDetails extends javax.swing.JPanel implements ActionListener {

    // javax.swing.Timer timer = new javax.swing.Timer(900, this);
    javax.swing.Timer timer = new javax.swing.Timer(5000, this);

    /**
     * Creates new form SenosrDetails
     */
   

    public SensorDetails() {
        initComponents();
        timer.start();
        loadSernsorDetails();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        sensorTable = new javax.swing.JTable(){

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                JComponent c =(JComponent) super.prepareRenderer(renderer, row, col);

                String smokeLevel =  (String)getModel().getValueAt(row, 4);
                String co2Level = (String)getModel().getValueAt(row, 5);

                //int smokeLevel  = (int)getModel().getValueAt(row,4);
                //  System.out.println("LLL"+smokeLevel);
                //        int co2Level  = (int)getModel().getValueAt(row,5);
                System.out.println("KK"+co2Level);
                if(smokeLevel!=null && co2Level !=null ){
                    if(col==4 && Integer.parseInt(smokeLevel)>=5){
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }else if (col==5 && Integer.parseInt(co2Level)>=5) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }

                return c;
            }
        };

        sensorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(sensorTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable sensorTable;
    // End of variables declaration//GEN-END:variables

    private void loadSernsorDetails() {

        try {
            List<Sensor> sensor = SensorController.getAllSensorDetails();
            Vector<Vector<String>> vectors = new Vector<>();
            Vector<String> header = new Vector<>();

            for (Sensor s2 : sensor) {
                Vector<String> vector = new Vector<>();
                
                //assign data to vector
                vector.add(s2.getStatus());
                vector.add(s2.getSensorId());
                vector.add(Integer.toString(s2.getFloorNumber()));
                vector.add(Integer.toString(s2.getRoomNumber()));
                vector.add(Integer.toString(s2.getSmokeLevel()));
                vector.add(Integer.toString(s2.getCo2Level()));

                vectors.add(vector);

            }
            
            // Add Headers to JTable
            header.add("Status");
            header.add("Sensor Id");
            header.add("Floor Number");
            header.add("Room Number");
            header.add("Smoke Level");
            header.add("Co2 Level");
            
            //Declare Default Table Model
            DefaultTableModel model = new DefaultTableModel(vectors, header);
            //Add Model to sensor table
            sensorTable.setModel(model);  //set table
        } catch (Exception ex) {
            Logger.getLogger(SensorDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        loadSernsorDetails();
    }

}
