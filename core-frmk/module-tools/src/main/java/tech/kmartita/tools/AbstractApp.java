package tech.kmartita.tools;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Step;
import tech.kmartita.tools.reports.BaseHTMLReporter;

import static tech.kmartita.tools.helpers.ConfigurationManager.*;
import static tech.kmartita.tools.helpers.ConfigurationManager.isGraphicalMode;

public abstract class AbstractApp extends BaseHTMLReporter {

    private static final ThreadLocal<Playwright> PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    protected AbstractApp() {
    }

    @Step("Start application session")
    public static void startSession() {
        Playwright playwright = Playwright.create();
        PLAYWRIGHT.set(playwright);

        BrowserContext browser = getBrowser()
                .launchBrowser(playwright, isGraphicalMode());
        BROWSER.set(browser);

        Page page = browser.newPage();
        PAGE.set(page);
    }

    public static Playwright getPlaywright() {
        return PLAYWRIGHT.get();
    }

    public static BrowserContext getBrowserContext() {
        return BROWSER.get();
    }

    public static Page getPage() {
        return PAGE.get();
    }

    private static void closePage() {
        if (PAGE.get() != null) {
            try {
                PAGE.get().close();
            } catch (Exception ignored) { } finally {
                PAGE.remove();
            }
        }
    }

    private static void closeBrowser() {
        if (BROWSER.get() != null) {
            try {
                BROWSER.get().close();
            } catch (Exception ignored) { } finally {
                BROWSER.remove();
            }
        }
    }

    private static void closePlaywright() {
        if (PLAYWRIGHT.get() != null) {
            try {
                PLAYWRIGHT.get().close();
            } catch (Exception ignored) { } finally {
                PLAYWRIGHT.remove();
            }
        }
    }

    @Step("Quit application session")
    public static void quitSession() {
        closePage();
        closeBrowser();
        closePlaywright();
    }
}
