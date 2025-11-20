package edu.ccrm.cli;
public class CLIApp {
    public void start() {
        MainMenu menu = new MainMenu();
        menu.start();
    }
    public static void main(String[] args) {
        new CLIApp().start();
    }
}
