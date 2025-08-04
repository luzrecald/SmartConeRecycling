/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guiSmartConeRecycling;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import wasteanalytics.*;

public class panelWasteAnalitycs extends javax.swing.JFrame {

    /**
     * Creates new form panelWasteAnalitycs
     */
    public panelWasteAnalitycs() {

       
        initComponents();

         backButton.setText("Back to menu Services");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        
        // Set up the table with proper column names
        String[] columnNames = {"Cone Material", "Cone Condition", "Cone Size", "Date"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        recordTable.setModel(model);

        // Add Record button action
        addRecordButton.addActionListener(evt -> {
            String material = materialComboBox.getSelectedItem().toString().trim();
            String condition = conditionComboBox.getSelectedItem().toString().trim();
            String size = sizeComboBox.getSelectedItem().toString().trim();
            String date = dateTextField.getText().trim();

            if (material.isEmpty() || condition.isEmpty() || size.isEmpty() || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(null, "Please fill all fields correctly (Date format: YYYY-MM-DD)");
                return;
            }

            DefaultTableModel tableModel = (DefaultTableModel) recordTable.getModel();
            tableModel.addRow(new Object[]{material, condition, size, date});
        });

        // Done button action
        doneButton.addActionListener(evt -> {
            DefaultTableModel tableModel = (DefaultTableModel) recordTable.getModel();
    int rowCount = tableModel.getRowCount();

    if (rowCount == 0) {
        reportTextArea.setText("No records to send.");
        return;
    }

    try {
        String service_type = "_grpc._tcp.local.";
        String service_name = "WasteAnalytics";

        javax.jmdns.JmDNS jmdns = javax.jmdns.JmDNS.create(java.net.InetAddress.getLocalHost());
        javax.jmdns.ServiceInfo serviceInfo = jmdns.getServiceInfo(service_type, service_name, 5000);

        if (serviceInfo == null) {
            reportTextArea.setText("Service '" + service_name + "' not found.");
            return;
        }

        String host = serviceInfo.getHostAddresses()[0];
        int port = serviceInfo.getPort();

        io.grpc.ManagedChannel channel = io.grpc.ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        io.grpc.Metadata metadata = new io.grpc.Metadata();
        io.grpc.Metadata.Key<String> authKey = io.grpc.Metadata.Key.of("auth-token", io.grpc.Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(authKey, "secreto123");

        WasteAnalyticsServiceGrpc.WasteAnalyticsServiceStub stub =
                WasteAnalyticsServiceGrpc.newStub(channel);
        stub = io.grpc.stub.MetadataUtils.attachHeaders(stub, metadata);

        java.util.List<WasteRecord> records = new java.util.ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            String material = tableModel.getValueAt(i, 0).toString();
            String condition = tableModel.getValueAt(i, 1).toString();
            String size = tableModel.getValueAt(i, 2).toString();
            String date = tableModel.getValueAt(i, 3).toString();

            WasteRecord record = WasteRecord.newBuilder()
                    .setMaterial(material)
                    .setCondition(condition)
                    .setSize(size)
                    .setDate(date)
                    .build();

            records.add(record);
        }

        reportTextArea.setText("Sending records... please wait.");

        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);

       StreamObserver<DailyReport> responseObserver = new StreamObserver<DailyReport>() {
            @Override
            public void onNext(DailyReport report) {
                StringBuilder result = new StringBuilder();

                result.append("=== Daily Summary ===\n");
                result.append("Total Waste: ").append(report.getDailySummary().getTotalWaste()).append("\n");
                result.append("Eligible for Sale: ").append(report.getDailySummary().getEligibleForSale()).append("\n");
                result.append("Recyclable: ").append(report.getDailySummary().getRecyclable()).append("\n");
                result.append("Non-Recoverable: ").append(report.getDailySummary().getNonRecoverable()).append("\n\n");

                result.append("=== Recycling Benefits ===\n");
                result.append("Reused by Soltex: ").append(report.getRecyclingBenefits().getReusedBySoltex()).append("\n");
                result.append("Recycled Cones: ").append(report.getRecyclingBenefits().getRecycledCones()).append("\n");
                result.append("Estimated Savings: ").append(report.getRecyclingBenefits().getEstimatedSavings()).append("\n");
                result.append("Waste Diverted: ").append(report.getRecyclingBenefits().getWasteDivertedFromLandfill()).append("\n");
                result.append("Tip: ").append(report.getRecyclingBenefits().getTip()).append("\n");

                reportTextArea.setText(result.toString());
            }

            @Override
            public void onError(Throwable t) {
                reportTextArea.setText("Error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };

        stub = stub.withDeadlineAfter(30, java.util.concurrent.TimeUnit.SECONDS);
        io.grpc.stub.StreamObserver<WasteRecord> requestObserver = stub.submitDailyWaste(responseObserver);
        for (WasteRecord r : records) {
            requestObserver.onNext(r);
        }
        requestObserver.onCompleted();

        latch.await(10, java.util.concurrent.TimeUnit.SECONDS);
        channel.shutdown();

    } catch (Exception e) {
        reportTextArea.setText("Exception: " + e.getMessage());
        e.printStackTrace();
    }
});
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        materialComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        conditionComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        sizeComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        dateTextField = new javax.swing.JTextField();
        addRecordButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        recordTable = new javax.swing.JTable();
        doneButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        reportTextArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("       Waste Record ");

        materialComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "plastic", "cardboard", " " }));
        materialComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materialComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Select cone material ");

        jLabel3.setText("Select cone Condition ");

        conditionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "good", "damaged", " " }));
        conditionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conditionComboBoxActionPerformed(evt);
            }
        });

        jLabel4.setText("Select cone Size");

        sizeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "small", "large", " " }));
        sizeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeComboBoxActionPerformed(evt);
            }
        });

        jLabel5.setText("Select date");

        dateTextField.setText("YYYY-MM-DD");
        dateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateTextFieldActionPerformed(evt);
            }
        });

        addRecordButton.setText("Add record ");

        recordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cone Material", "Cone Condition", "Cone size", "Date"
            }
        ));
        jScrollPane1.setViewportView(recordTable);

        doneButton.setText("Done ");

        reportTextArea.setColumns(20);
        reportTextArea.setRows(5);
        jScrollPane2.setViewportView(reportTextArea);

        jLabel6.setText("Waste Report");

        backButton.setText("Back to menu Services");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(materialComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(conditionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sizeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateTextField)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(addRecordButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(doneButton))))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(materialComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conditionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addRecordButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doneButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backButton)
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void materialComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materialComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_materialComboBoxActionPerformed

    private void sizeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sizeComboBoxActionPerformed

    private void conditionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conditionComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conditionComboBoxActionPerformed

    private void dateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateTextFieldActionPerformed

     private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
                new MainGUI().setVisible(true); // abre el menú principal
                dispose(); // cierra esta ventana
            }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(panelWasteAnalitycs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(panelWasteAnalitycs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(panelWasteAnalitycs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(panelWasteAnalitycs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new panelWasteAnalitycs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRecordButton;
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> conditionComboBox;
    private javax.swing.JTextField dateTextField;
    private javax.swing.JButton doneButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> materialComboBox;
    private javax.swing.JTable recordTable;
    private javax.swing.JTextArea reportTextArea;
    private javax.swing.JComboBox<String> sizeComboBox;
    // End of variables declaration//GEN-END:variables
}
