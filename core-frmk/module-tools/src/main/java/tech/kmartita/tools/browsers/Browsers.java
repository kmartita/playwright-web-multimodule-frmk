package tech.kmartita.tools.browsers;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.ViewportSize;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

public enum Browsers implements IBrowser {

    CHROME {
        private final List<String> knownNames = Arrays.asList("chrome", "googlechrome", "google_chrome");

        @Override
        public Browser.NewContextOptions setOptions() {
            return new Browser.NewContextOptions()
                    .setViewportSize(setDimension())
                    .setIgnoreHTTPSErrors(true);
        }

        @Override
        public BrowserContext launchBrowser(Playwright playwright, boolean isHeadless) {
            BrowserType chromium = playwright.chromium();

            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                    .setArgs(List.of(
                            "--disable-extensions",
                            "--disable-notifications",
                            "--allow-file-access-from-files",
                            "--use-fake-device-for-media-stream",
                            "--use-fake-ui-for-media-stream",
                            "--disable-web-security",
                            "--remote-allow-origins=*",
                            "--enable-geolocation"
                    ))
                    .setChannel("chrome")
                    .setHeadless(isHeadless);
            Browser browser = chromium.launch(options);
            return browser.newContext(setOptions());
        }

        protected boolean isKnownAs(String name) {
            return knownNames.contains(name);
        }
    },

    FIREFOX {
        private final List<String> knownNames = Arrays.asList("firefox", "mozillafirefox", "mozilla_firefox");

        @Override
        public Browser.NewContextOptions setOptions() {
            return new Browser.NewContextOptions()
                    .setViewportSize(setDimension());
        }

        @Override
        public BrowserContext launchBrowser(Playwright playwright, boolean isHeadless) {
            BrowserType firefox = playwright.firefox();

            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(isHeadless);
            Browser browser = firefox.launch(options);
            return browser.newContext(setOptions());
        }

        protected boolean isKnownAs(String name) {
            return knownNames.contains(name);
        }
    },

    WEBKIT {
        private final List<String> knownNames = Collections.singletonList("safari");

        @Override
        public Browser.NewContextOptions setOptions() {
            return new Browser.NewContextOptions()
                    .setViewportSize(setDimension());
        }

        @Override
        public BrowserContext launchBrowser(Playwright playwright, boolean isHeadless) {
            BrowserType webkit = playwright.webkit();

            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(isHeadless);
            Browser browser = webkit.launch(options);
            return browser.newContext(setOptions());
        }

        protected boolean isKnownAs(String name) {
            return knownNames.contains(name);
        }
    };

    protected abstract boolean isKnownAs(String name);

    private static ViewportSize setDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        return new ViewportSize(width, height);
    }

    public static Browsers from(String browserName) {
        return Arrays.stream(Browsers.values())
                .filter(value -> value.isKnownAs(browserName.trim().toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format("\nUnsupported browser: '%s'\n", browserName)));
    }
}
