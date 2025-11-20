package edu.ccrm.config;
public class AppConfig {
    private static AppConfig instance;
    private String appName = "Campus Course & Records Manager";
    private String version = "1.0";
    private AppConfig() {}
    public static AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }
    public String getAppName() { return appName; }
    public String getVersion() { return version; }
}
