package com.mycompany.app;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import java.lang.reflect.InvocationTargetException;

/**
 * Hello world!
 */
public class App
{

    private final String message = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        IRunnableWithProgress runnable = new IRunnableWithProgress();
        ProgressMonitorDialog dlg = new ProgressMonitorDialog();
        try {
            dlg.run(true,true, runnable);
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return message;
    }

}
