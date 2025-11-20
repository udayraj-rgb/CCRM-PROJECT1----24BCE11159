package edu.ccrm;

import edu.ccrm.cli.CLIApp;
import edu.ccrm.config.AppConfig;

public class Main {
    public static void main(String[] args) {
        AppConfig config = AppConfig.getInstance();
        System.out.println(config.getAppName() + " v" + config.getVersion());
        System.out.println("Welcome to CCRM!\n");
        CLIApp app = new CLIApp(); 
        app.start();   // instance method
    }
}
