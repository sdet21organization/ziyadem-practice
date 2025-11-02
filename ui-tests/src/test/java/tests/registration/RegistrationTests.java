package tests.registration;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.components.Header;
import pages.components.LoginModal;
import tests.BaseTest;
@DisplayName("Registration Tests")
public class RegistrationTests extends BaseTest {

@Test
    @Story("")
    @DisplayName("Check registration modsal is opened")
    public void registrationModalIsOpened (){
    Header header = new Header(context);
    header.open();
    header.clickAccountButton();
    LoginModal loginModal = new LoginModal(context);
    Assertions.assertTrue(loginModal.LoginModalIsOpened());
}

}
