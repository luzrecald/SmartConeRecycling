package guiSmartConeRecycling;

import coneclassification.*;
import io.grpc.*;
import io.grpc.stub.MetadataUtils;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class panelClassification extends javax.swing.JFrame {

    /**
     * Creates new form panelClassification
     */
    public panelClassification() {
        initComponents();
          setLocationRelativeTo(null); // Centrar la ventana

        // Inicializar valores de los ComboBoxes
        typeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"plastic", "cardboard"}));
        sizeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"small", "large"}));
        conditionComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"good", "damaged"}));

        suggestionTextArea.setEditable(false);

        // Acción del botón "Volver"
        backButton.addActionListener(evt -> {
            new MainGUI().setVisible(true);
            dispose();
            
            
        });
        
       classifyButton.addActionListener(evt -> classifyButtonActionPerformed());
        
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        sizeComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        conditionComboBox = new javax.swing.JComboBox<>();
        classifyButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        suggestionTextArea = new javax.swing.JTextArea();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Cone Classification");

        jLabel3.setText("Cone Type ");

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "plastic", "cardboard", " " }));

        jLabel2.setText("Cone Size");

        sizeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "small", "large" }));

        jLabel4.setText("Cone Condition");

        conditionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "good", "damaged", " " }));

        classifyButton.setText("Classify");

        jLabel5.setText("Suggestion ");

        suggestionTextArea.setColumns(20);
        suggestionTextArea.setRows(5);
        jScrollPane1.setViewportView(suggestionTextArea);

        backButton.setText("Back to menu Services");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(typeComboBox, 0, 100, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sizeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(conditionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(backButton)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(classifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conditionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(classifyButton)
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backButton)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

            private void classifyButtonActionPerformed() {
    String type = (String) typeComboBox.getSelectedItem();
    String size = (String) sizeComboBox.getSelectedItem();
    String condition = (String) conditionComboBox.getSelectedItem();

    if (type == null || size == null || condition == null) {
        suggestionTextArea.setText("❌ Please select all fields.");
        return;
    }

    ManagedChannel channel = null;

    try {
        ConeClassificationServiceDiscovery.ServiceDetails service =
                ConeClassificationServiceDiscovery.discover("ConeClassifier");

        if (service == null) {
            suggestionTextArea.setText("❌ Service 'ConeClassifier' not found.");
            return;
        }

        channel = ManagedChannelBuilder
                .forAddress(service.host, service.port)
                .usePlaintext()
                .build();

        Metadata metadata = new Metadata();
        Metadata.Key<String> AUTH_TOKEN_KEY =
                Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(AUTH_TOKEN_KEY, "secrettoken123");

        ConeClassificationServiceGrpc.ConeClassificationServiceBlockingStub stub =
                ConeClassificationServiceGrpc.newBlockingStub(channel);
        stub = MetadataUtils.attachHeaders(stub, metadata)
                .withDeadlineAfter(3, TimeUnit.SECONDS);

        ConeInfo request = ConeInfo.newBuilder()
                .setType(type)
                .setSize(size)
                .setCondition(condition)
                .build();

        Suggestion response = stub.classifyCone(request);
        suggestionTextArea.setText("✅ Suggestion: " + response.getSuggestion());

    } catch (Exception ex) {
        suggestionTextArea.setText("❌ Error: " + ex.getMessage());
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
            java.util.logging.Logger.getLogger(panelClassification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(panelClassification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(panelClassification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(panelClassification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new panelClassification().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton classifyButton;
    private javax.swing.JComboBox<String> conditionComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> sizeComboBox;
    private javax.swing.JTextArea suggestionTextArea;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
