package tech.kmartita.tests.web;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tech.kmartita.app.pages.HomePage;
import tech.kmartita.app.WebApp;
import tech.kmartita.tests.AbstractWebTest;

import static org.assertj.core.api.Assertions.assertThat;

@Owner("Marta Kravchuk")
@Feature("Web")
@Story("Page")
public class VerifyHomePageTest extends AbstractWebTest {

    private HomePage homePage;

    @BeforeClass
    public void beforeTest() {
        homePage = new WebApp().openNewLoginPage();
    }

    @Test
    public void verifyPageIsLoaded() {
        homePage.clickOnFirstLogo();

        assertThat(homePage.isLogoPresent())
                .as("Home page should be opened.")
                .isTrue();
    }
}
