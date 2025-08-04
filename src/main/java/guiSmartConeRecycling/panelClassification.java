package guiSmartConeRecycling;

import coneclassification.*;
import io.grpc.*;
import io.grpc.stub.MetadataUtils;

import javax.swing.*;
import java.util.concurrent.TimeUnit;


public class panelClassification extends javax.swing.JFrame {

    // Componentes de la interfaz
    private javax.swing.JButton classifyButton;
    private javax.swing.JComboBox<String> conditionComboBox;
    private javax.swing.JComboBox<String> sizeComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JLabel jLabel1, jLabel2, jLabel3, jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea resultTextArea;

    public panelClassification() {
        initComponents();
        setLocationRelativeTo(null); // Centrar ventana

        // Inicializar combos
        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"plastic", "cardboard"}));
        sizeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"small", "large"}));
        conditionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"good", "damaged"}));

        resultTextArea.setEditable(false);
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        sizeComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        conditionComboBox = new javax.swing.JComboBox<>();
        classifyButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Cone Classification");

        jLabel2.setText("Cone Type");

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "plastic", "cardboard" }));
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("Cone Size ");

        sizeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "small", "large", " " }));
        sizeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeComboBoxActionPerformed(evt);
            }
        });

        jLabel4.setText("Cone Condition");

        conditionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "good", "damaged" }));
        conditionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conditionComboBoxActionPerformed(evt);
            }
        });

        classifyButton.setText("Classify Cone");
        classifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classifyButtonActionPerformed(evt);
            }
        });

        resultTextArea.setColumns(20);
        resultTextArea.setRows(5);
        jScrollPane1.setViewportView(resultTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(classifyButton)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addComponent(typeComboBox, 0, 126, Short.MAX_VALUE)
                                .addComponent(sizeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(conditionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(254, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conditionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(classifyButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void sizeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sizeComboBoxActionPerformed

    private void conditionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conditionComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conditionComboBoxActionPerformed

    private void classifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classifyButtonActionPerformed
        String type = (String) typeComboBox.getSelectedItem();
        String size = (String) sizeComboBox.getSelectedItem();
        String condition = (String) conditionComboBox.getSelectedItem();

        if (type == null || size == null || condition == null) {
            resultTextArea.setText("❌ Please select all fields.");
            return;
        }

        ManagedChannel channel = null;

        try {
            // Service discovery
            ConeClassificationServiceDiscovery.ServiceDetails service =
                    ConeClassificationServiceDiscovery.discover("ConeClassifier");

            if (service == null) {
                resultTextArea.setText("❌ Service 'ConeClassifier' not found.");
                return;
            }

            // Create gRPC channel
            channel = ManagedChannelBuilder
                    .forAddress(service.host, service.port)
                    .usePlaintext()
                    .build();

            // Add authentication token
            Metadata metadata = new Metadata();
            Metadata.Key<String> AUTH_TOKEN_KEY =
                    Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
            metadata.put(AUTH_TOKEN_KEY, "secrettoken123");

            // Create stub with headers and timeout
            ConeClassificationServiceGrpc.ConeClassificationServiceBlockingStub stub =
                    ConeClassificationServiceGrpc.newBlockingStub(channel);
            stub = MetadataUtils.attachHeaders(stub, metadata)
                    .withDeadlineAfter(3, TimeUnit.SECONDS);

            // Build request
            ConeInfo request = ConeInfo.newBuilder()
                    .setType(type)
                    .setSize(size)
                    .setCondition(condition)
                    .build();

            // Make gRPC call
            Suggestion response = stub.classifyCone(request);
            resultTextArea.setText("✅ Suggestion: " + response.getSuggestion());

        } catch (Exception ex) {
            resultTextArea.setText("❌ Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (channel != null) {
                channel.shutdown();
                try {
                    channel.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new panelClassification().setVisible(true));
    }
}

/*
    }//GEN-LAST:event_classifyButtonActionPerformed

/*
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton classifyButton;
    private javax.swing.JComboBox<String> conditionComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea resultTextArea;
    private javax.swing.JComboBox<String> sizeComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
*/