import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.util.Optional;

public class TestListeners implements TestWatcher {
    FailedTestsTools make = new FailedTestsTools();

    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable) {

    }

    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional) {

    }

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        try {
            FailedTestsTools.attachScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FailedTestsTools.attachBrowserVersion();
        FailedTestsTools.attachDateAndTime();
    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext) {

    }
}
