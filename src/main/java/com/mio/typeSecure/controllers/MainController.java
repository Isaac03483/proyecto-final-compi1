package com.mio.typeSecure.controllers;

import com.mio.typeSecure.views.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
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

}
