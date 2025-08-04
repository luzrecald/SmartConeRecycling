package guiSmartConeRecycling;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;

import conepurchase.ConePurchaseServiceGrpc;
import conepurchase.ConeSaleRequest;
import conepurchase.ConeSaleResponse;
import io.grpc.Status;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;



public class panelPurchase extends javax.swing.JFrame {

    
   public panelPurchase() {
        initComponents();
            
        //No show the location from the start
        locationTextField.setVisible(false);
        jLabel3.setVisible(false);
        
        backButton.setText("Back to menu Services");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        //shown and hide the location in real time
        quantityTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                try {
                    int value = Integer.parseInt(quantityTextField.getText().trim());
                    boolean show = value >= 100;
                    locationTextField.setVisible(show);
                    jLabel3.setVisible(show);
                } catch (NumberFormatException ex) {
                    locationTextField.setVisible(false);
                    jLabel3.setVisible(false);
                }
            }
        });
        
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        quantityLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sentRequestButton = new javax.swing.JButton();
        quantityTextField = new javax.swing.JTextField();
        locationTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultTextArea = new javax.swing.JTextArea();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Sell cone to Soltex");

        quantityLabel.setText("Cone Cuantity ");

        jLabel3.setText("Location ");

        sentRequestButton.setText("Sent Request ");
        sentRequestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sentRequestButtonActionPerformed(evt);
            }
        });

        quantityTextField.setText("Cuantity");
        quantityTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityTextFieldActionPerformed(evt);
            }
        });

        locationTextField.setText("Location");
        locationTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationTextFieldActionPerformed(evt);
            }
        });

        resultTextArea.setColumns(20);
        resultTextArea.setRows(5);
        jScrollPane1.setViewportView(resultTextArea);

        backButton.setText("Back yo menu Services");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(quantityTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                            .addComponent(sentRequestButton)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(locationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(backButton)
                                .addGap(21, 21, 21))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(quantityLabel)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(quantityLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(quantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(locationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sentRequestButton)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backButton)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  
    private void sentRequestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sentRequestButtonActionPerformed
             String quantityText = quantityTextField.getText().trim();
             String locationInput = locationTextField.getText().trim();

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityText);
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (quantity >= 100 && locationInput.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Location is required for large orders.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String location = quantity >= 100 ? locationInput : "N/A";

                sentRequestButton.setEnabled(false);
                resultTextArea.setText("Sending request...\n");

                ManagedChannel channel = createChannel();
                ConePurchaseServiceGrpc.ConePurchaseServiceStub stub = createStubWithAuth(channel);

                ConeSaleRequest request = ConeSaleRequest.newBuilder()
                        .setQuantity(quantity)
                        .setLocation(location)
                        .build();

                stub.requestConeSale(request, new StreamObserver<ConeSaleResponse>() {
                    @Override
                    public void onNext(ConeSaleResponse response) {
                        SwingUtilities.invokeLater(() -> {
                            resultTextArea.append("[" + response.getMessageType() + "] " + response.getMessage() + "\n");
                            if (!response.getDay().isEmpty() && !response.getTimeslot().isEmpty()) {
                                resultTextArea.append("[pickupOption] Pickup on " + response.getDay() + " during " + response.getTimeslot() + "\n");
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable t) {
                        SwingUtilities.invokeLater(() -> {
                            resultTextArea.append("Error: " + t.getMessage() + "\n");
                            sentRequestButton.setEnabled(true);
                        });
                        channel.shutdownNow();
                    }

                    @Override
                    public void onCompleted() {
                        SwingUtilities.invokeLater(() -> {
                            resultTextArea.append("Request completed.\n");
                            sentRequestButton.setEnabled(true);
                            quantityTextField.setText("");
                            locationTextField.setText("");
                            locationTextField.setVisible(false);
                            jLabel3.setVisible(false);
                        });
                        channel.shutdown();
                    }
                });

    }//GEN-LAST:event_sentRequestButtonActionPerformed

    private void quantityTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityTextFieldActionPerformed

    private void locationTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_locationTextFieldActionPerformed

      private ManagedChannel createChannel() {
                    return ManagedChannelBuilder.forAddress("localhost", 50054)
                         .usePlaintext()
                            .build();
                }

            private ConePurchaseServiceGrpc.ConePurchaseServiceStub createStubWithAuth(ManagedChannel channel) {
                  Metadata metadata = new Metadata();
                Metadata.Key<String> AUTH_TOKEN_KEY = Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
                metadata.put(AUTH_TOKEN_KEY, "securetoken123");

                 ConePurchaseServiceGrpc.ConePurchaseServiceStub rawStub = ConePurchaseServiceGrpc.newStub(channel);
                return MetadataUtils.attachHeaders(rawStub, metadata);
}

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
            java.util.logging.Logger.getLogger(panelPurchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(panelPurchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(panelPurchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(panelPurchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new panelPurchase().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField locationTextField;
    private javax.swing.JLabel quantityLabel;
    private javax.swing.JTextField quantityTextField;
    private javax.swing.JTextArea resultTextArea;
    private javax.swing.JButton sentRequestButton;
    // End of variables declaration//GEN-END:variables
}
