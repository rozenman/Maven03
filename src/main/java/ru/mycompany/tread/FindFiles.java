package ru.mycompany.tread;

import javafx.application.Platform;

import java.io.File;

public class FindFiles extends Thread {

    @Override
    public void run() {
        System.out.println("Привет из потока " +
                Thread.currentThread().getName());
    }


}
