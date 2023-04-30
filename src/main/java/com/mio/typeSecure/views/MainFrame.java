/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mio.typeSecure.views;

import com.mio.typeSecure.controllers.MainController;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author mio
 */
public class MainFrame extends javax.swing.JFrame {


    private final String DEFAULT_NAME = "file.ts";
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        mainController = new MainController(this);
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Pane = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        saveAsButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        newFileButton = new javax.swing.JButton();
        filesTabbedPane = new javax.swing.JTabbedPane();
        openButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Pane.setBackground(new java.awt.Color(51, 51, 51));

        closeButton.setBackground(new java.awt.Color(0, 204, 102));
        closeButton.setText("Cerrar Archivo");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        saveAsButton.setBackground(new java.awt.Color(0, 204, 102));
        saveAsButton.setText("Guardar Como");
        saveAsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsButtonActionPerformed(evt);
            }
        });

        saveButton.setBackground(new java.awt.Color(0, 204, 102));
        saveButton.setText("Guardar Archivo");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        newFileButton.setBackground(new java.awt.Color(0, 204, 102));
        newFileButton.setText("Nuevo Archivo");
        newFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileButtonActionPerformed(evt);
            }
        });

        openButton.setBackground(new java.awt.Color(0, 204, 102));
        openButton.setText("Abrir Archivo");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PaneLayout = new javax.swing.GroupLayout(Pane);
        Pane.setLayout(PaneLayout);
        PaneLayout.setHorizontalGroup(
            PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneLayout.createSequentialGroup()
                .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaneLayout.createSequentialGroup()
                        .addContainerGap(270, Short.MAX_VALUE)
                        .addComponent(openButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveAsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton))
                    .addComponent(filesTabbedPane))
                .addContainerGap())
        );
        PaneLayout.setVerticalGroup(
            PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(saveAsButton)
                    .addComponent(saveButton)
                    .addComponent(newFileButton)
                    .addComponent(openButton))
                .addGap(18, 18, 18)
                .addComponent(filesTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileButtonActionPerformed
        // TODO add your handling code here:
        this.filesTabbedPane.addTab(DEFAULT_NAME, new CompilerPanel());

    }//GEN-LAST:event_newFileButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveButtonActionPerformed

    private void saveAsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveAsButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        CompilerPanel panel = (CompilerPanel) (this.filesTabbedPane.getSelectedComponent());

        if(!panel.getInputArea().getText().trim().isEmpty()){
            int option = JOptionPane.showConfirmDialog(null, "¿Desea guardar el archivo antes de borrar?","TypeSecure", JOptionPane.YES_NO_OPTION);
            if(option == 0){
                System.out.println("Guardando");

            }
        }

        this.filesTabbedPane.remove(this.filesTabbedPane.getSelectedComponent());
    }//GEN-LAST:event_closeButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        // TODO add your handling code here:
        File file = mainController.findFile();
        CompilerPanel compilerPanel = new CompilerPanel();
        try {
            compilerPanel.setFileInfo(file, mainController.readFile(file));
            this.filesTabbedPane.addTab(file.getName(), compilerPanel);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }//GEN-LAST:event_openButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pane;
    private javax.swing.JButton closeButton;
    private javax.swing.JTabbedPane filesTabbedPane;
    private javax.swing.JButton newFileButton;
    private javax.swing.JButton openButton;
    private javax.swing.JButton saveAsButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

    private MainController mainController;
}
