package tech.kmartita.tests.web;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tech.kmartita.app.WebApp;
import tech.kmartita.app.components.Header;
import tech.kmartita.tests.AbstractWebTest;

import static org.assertj.core.api.Assertions.assertThat;

@Owner("Marta Kravchuk")
@Feature("Web")
@Story("Component")
public class VerifyComponentTest extends AbstractWebTest {

    private Header header;

    @BeforeClass
    public void beforeTest() {
        new WebApp().openNewLoginPage();
        header = new Header();
    }

    @Test
    public void verifyComponentIsLoaded() {
        header.clickOnLogo();

        assertThat(header.isLogoPresent())
                .as("Component should be opened.")
                .isTrue();
    }
}