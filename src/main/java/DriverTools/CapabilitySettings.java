package DriverTools;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CapabilitySettings {
    private static final String pathToFile = "src/main/resources/Capabilities.json";
    private static final Gson gson = new Gson();

    private String Environment;
    private String Platform;
    private String PlatformVersion;
    private String BrowserName;
    private String BrowserVersion;
    private String URL;
    private String Port;
    private String accessKey;

    public static CapabilitySettings SettingsRead(CapabilitySettings capSettings) throws IOException {
        capSettings = new CapabilitySettings();
        Reader reader = new FileReader(pathToFile);
        capSettings = gson.fromJson(reader, CapabilitySettings.class);
        reader.close();
        return capSettings;
    }

    public String getEnvironment() {
        return Environment.toLowerCase();
    }

    public String getPlatform() {
        return Platform.toLowerCase();
    }

    public String getPlatformVersion() {
        return PlatformVersion.toLowerCase();
    }

    public String getBrowserName() {
        return BrowserName.toLowerCase();
    }

    public String getBrowserVersion() {
        return BrowserVersion.toLowerCase();
    }

    public String getURL() {
        return URL.toLowerCase();
    }

    public String getPort() {
        return Port;
    }

}
