
package gui;


import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;



public class EncryptorAndDecryptorProgress extends javax.swing.JFrame
{
    private String encryptOrDecrypt;
    private boolean completedTask;

    public void setCompletedTask(boolean completedTask)
    {
        this.completedTask = completedTask;
    }
    
    public EncryptorAndDecryptorProgress(String encryptOrDecrypt)
    {
        this.encryptOrDecrypt=encryptOrDecrypt;
        
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(EncryptorAndDecryptorProgress.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(EncryptorAndDecryptorProgress.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(EncryptorAndDecryptorProgress.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(EncryptorAndDecryptorProgress.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        */
        //</editor-fold>
        
        /* Create and display the form */
        
        initComponents();
        setResizable(false);
        progressOfFilesTextField.setEditable(false);
        oKButton.setVisible(false);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                JFrame frame = (JFrame)e.getSource();
                int result;
                if(completedTask)
                {
                    Toolkit.getDefaultToolkit().beep();
                    result = JOptionPane.showConfirmDialog(frame, "Do you want to close the application?", "Exit Application", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION)
                    {
                        
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                    
                }
                else if(encryptOrDecrypt.equals("encrypt"))
                {
                    Toolkit.getDefaultToolkit().beep();
                    result = JOptionPane.showConfirmDialog(frame, "Encryption is in progress. "+"Are you sure you want to exit the application?", "Exit Application", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION)
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                
                else if (encryptOrDecrypt.equals("decrypt"))
                {
                    Toolkit.getDefaultToolkit().beep();
                     result = JOptionPane.showConfirmDialog(frame, "Decryption is in progress. "+"Are you sure you want to exit the application?", "Exit Application", JOptionPane.YES_NO_OPTION);
                     if (result == JOptionPane.YES_OPTION)
                     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                
            }
        });
        if(encryptOrDecrypt.equalsIgnoreCase("encrypt"))
        {
            heading.setText("Encrypting:");
        }
        else if(encryptOrDecrypt.equalsIgnoreCase("decrypt"))
        {
            heading.setText("Decrypting:");
        }
        getRootPane().setDefaultButton(oKButton);
        this.setVisible(true);
        
        
    }

    public JButton getoKButton()
    {
        return oKButton;
    }

    public JProgressBar getProgressBar()
    {
        return progressBar;
    }

    public JTextArea getProgressOfFilesTextField()
    {
        return progressOfFilesTextField;
    }

    public JLabel getProgressPercentLabel()
    {
        return progressPercentLabel;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        heading = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        progressOfFilesTextField = new javax.swing.JTextArea();
        progressBar = new javax.swing.JProgressBar();
        progressPercentLabel = new javax.swing.JLabel();
        oKButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        heading.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        progressOfFilesTextField.setColumns(20);
        progressOfFilesTextField.setRows(5);
        progressOfFilesTextField.setToolTipText("OK");
        jScrollPane1.setViewportView(progressOfFilesTextField);

        progressPercentLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        progressPercentLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        progressPercentLabel.setText("0%");

        oKButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        oKButton.setToolTipText("OK");
        oKButton.setEnabled(false);
        oKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oKButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(heading)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(oKButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(196, 196, 196)
                        .addComponent(progressPercentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(heading)
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(progressPercentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(oKButton, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void oKButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_oKButtonActionPerformed
    {//GEN-HEADEREND:event_oKButtonActionPerformed
        new Main(true).setVisible(true);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_oKButtonActionPerformed
    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel heading;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton oKButton;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTextArea progressOfFilesTextField;
    private javax.swing.JLabel progressPercentLabel;
    // End of variables declaration//GEN-END:variables

}
