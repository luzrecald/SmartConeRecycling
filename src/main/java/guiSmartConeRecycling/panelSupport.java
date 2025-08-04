
package guiSmartConeRecycling;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import recyclingsupport.*;

import javax.swing.*;
import java.awt.*;

public class panelSupport extends javax.swing.JFrame {

  // gRPC communication
    private ManagedChannel channel;
    private StreamObserver<ChatMessage> requestObserver;

    public panelSupport() {
        initComponents();

        // Set vertical layout for chat panel (like VBox)
        chatContainerPanel.setLayout(new BoxLayout(chatContainerPanel, BoxLayout.Y_AXIS));

        // Optional: ensure the scroll panel has enough height to show messages
        chatContainerPanel.setPreferredSize(new Dimension(330, 1000));

        // Attach the chat container to the scroll pane
        chatScrollPane.setViewportView(chatContainerPanel);

        // Start the gRPC connection to the server
        startGrpcConnection();
        
         backButton.setText("Back to menu Services");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
    }

    /**
     * Connects the client to the gRPC server and sets up the bidirectional stream
     */
    private void startGrpcConnection() {
        try {
            RecyclingSupportServiceDiscovery.ServiceDetails service =
                    RecyclingSupportServiceDiscovery.discover("RecyclingSupport");

            if (service == null) {
                JOptionPane.showMessageDialog(this, "Service 'RecyclingSupport' not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            channel = ManagedChannelBuilder.forAddress(service.host, service.port)
                    .usePlaintext()
                    .build();

            RecyclingSupportServiceGrpc.RecyclingSupportServiceStub asyncStub =
                    RecyclingSupportServiceGrpc.newStub(channel);

            requestObserver = asyncStub.chat(new StreamObserver<ChatMessage>() {
                @Override
                public void onNext(ChatMessage value) {
                    // When receiving a message from the server, show it in the UI
                    SwingUtilities.invokeLater(() -> {
                        JLabel serverMessage = new JLabel("Support: " + value.getMessage());
                        serverMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
                        chatContainerPanel.add(serverMessage);
                        chatContainerPanel.revalidate();
                        chatContainerPanel.repaint();

                        JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
                        vertical.setValue(vertical.getMaximum());
                    });
                }

                @Override
                public void onError(Throwable t) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(panelSupport.this, "Chat error: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
                }

                @Override
                public void onCompleted() {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(panelSupport.this, "Chat ended by server.", "Info", JOptionPane.INFORMATION_MESSAGE));
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error starting gRPC: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sends a message to the gRPC server and displays it in the UI
     */
    private void sendMessage() {
        String message = messageInputTextField.getText().trim();

        if (!message.isEmpty() && requestObserver != null) {
            // Display user's message
            JLabel userMessage = new JLabel("You: " + message);
            userMessage.setAlignmentX(Component.RIGHT_ALIGNMENT);
            chatContainerPanel.add(userMessage);

            // Build the gRPC message
            ChatMessage grpcMessage = ChatMessage.newBuilder()
                    .setSender("client")
                    .setMessage(message)
                    .setTimestamp(java.time.Instant.now().toString())
                    .build();

            // Send it to the server
            requestObserver.onNext(grpcMessage);

            // Update UI
            chatContainerPanel.revalidate();
            chatContainerPanel.repaint();

            JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());

            messageInputTextField.setText("");
        }
    }

    /**
     * This method initializes all components (auto-generated)
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        chatContainerPanel = new javax.swing.JPanel();
        chatScrollPane = new javax.swing.JScrollPane();
        messageInputTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Chat Support");

        javax.swing.GroupLayout chatContainerPanelLayout = new javax.swing.GroupLayout(chatContainerPanel);
        chatContainerPanel.setLayout(chatContainerPanelLayout);
        chatContainerPanelLayout.setHorizontalGroup(
            chatContainerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 799, Short.MAX_VALUE)
        );
        chatContainerPanelLayout.setVerticalGroup(
            chatContainerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        messageInputTextField.setText("Message ");
        messageInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageInputTextFieldActionPerformed(evt);
            }
        });

        sendButton.setText("send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        backButton.setText("Back to menu Services");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sendButton)
                                    .addComponent(messageInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(380, 380, 380)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(354, 354, 354))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(chatContainerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chatScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(backButton)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(chatContainerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chatScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(messageInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sendButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backButton)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    @Override
    public void dispose() {
        // Properly close the gRPC channel
        if (requestObserver != null) requestObserver.onCompleted();
        if (channel != null && !channel.isShutdown()) channel.shutdown();
        super.dispose();
    }

    private void messageInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageInputTextFieldActionPerformed
        sendMessage();
    }//GEN-LAST:event_messageInputTextFieldActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        sendMessage();
    }//GEN-LAST:event_sendButtonActionPerformed

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
            java.util.logging.Logger.getLogger(panelSupport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(panelSupport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(panelSupport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(panelSupport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new panelSupport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel chatContainerPanel;
    private javax.swing.JScrollPane chatScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField messageInputTextField;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
