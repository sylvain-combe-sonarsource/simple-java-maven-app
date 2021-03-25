package com.mycompany.app;

/**
 * Hello world!
 */
public class App
{

    private final String message = "Hello World!";
    public String msg1;
    public String msg2;


    public App() {
        msg1 = "one";
        msg2="two";
    }

    public static void main(String[] args) {
        App myApp = new App();
        try {
            System.out.println(myApp.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final String getMessage(boolean isSync) throws InterruptedException {
        synchronized (this.msg1) {
            // threadB can't enter this block to request this.mon2 lock & release threadA
            synchronized (this.msg2) {
                this.msg2.wait();  // Noncompliant; threadA is stuck here holding lock on this.mon1
            }
        }
        return message;
    }

    private final String getMessage() throws InterruptedException {
        return message;
    }


}
