package com.mio.typeSecure.controllers;

import com.mio.typeSecure.views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class MainController {


    private MainFrame mainFrame;

    public MainController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public File findFile(){
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this.mainFrame);


        if(option == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile().getAbsoluteFile();
        }

        return null;
    }

    public String readFile(File file) throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()){
            content.append(scanner.nextLine()).append("\n");
        }

        return content.toString();

    }

    public boolean save(String fileName, String content){
        System.out.println(fileName);
        if(fileName.equals(MainFrame.DEFAULT_NAME)){
            return saveAs(content);
        }

        try(FileWriter writer = new FileWriter(fileName)){
            writer.write(content);
        } catch (IOException e){
            return false;
        }

        return true;
    }

    public boolean saveAs(String content){
        try {

            FileDialog fileDialog = null;
            fileDialog = new FileDialog(fileDialog, "Guardar Como", FileDialog.SAVE);
            fileDialog.setVisible(true);
            fileDialog.dispose();
            if(fileDialog.getFile() != null && fileDialog.getDirectory() != null){
                FileWriter writer = new FileWriter(fileDialog.getDirectory()+fileDialog.getFile()+".ts");
                writer.write(content);
                writer.close();
                return true;
            }
        }catch (IOException ignored){
            return false;
        }

        return false;
    }

}
