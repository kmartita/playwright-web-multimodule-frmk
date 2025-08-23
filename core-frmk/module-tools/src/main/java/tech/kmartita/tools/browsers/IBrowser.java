package tech.kmartita.tools.browsers;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;

public interface IBrowser {
    Browser.NewContextOptions setOptions();
    BrowserContext launchBrowser(Playwright playwright, boolean isHeadless);
}
