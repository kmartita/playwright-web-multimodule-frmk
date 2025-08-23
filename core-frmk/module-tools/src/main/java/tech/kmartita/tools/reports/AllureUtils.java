package tech.kmartita.tools.reports;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.Reporter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureUtils {

    private AllureUtils() {}

    @Step("{0}")
    public static void step(String step) {
        Reporter.log("\nLOG - " + step, true);
    }

    public static synchronized void takeScreenshot(Page page, Path path, String screenshotName) {
        page.screenshot(new Page.ScreenshotOptions().setPath(path).setFullPage(true));

        try (InputStream is = Files.newInputStream(path)) {
            Allure.attachment(screenshotName, is);

        } catch (IOException e) {
            System.err.printf("In AllureUtils#takeScreenshot: %s\n", e.getMessage());
        }
    }
}
