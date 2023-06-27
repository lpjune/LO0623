import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CheckoutTest {

//    @BeforeAll
//    void setUp() {
//        Checkout checkout = new Checkout()
//    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should construct checkout date")
    void shouldConstructCheckoutDate() {
        Checkout checkout = new Checkout("CHNS", 4, 0, "06/09/24");

        LocalDate checkoutDate = checkout.constructCheckoutDate(checkout.stringCheckoutDate);
        Assertions.assertEquals(LocalDate.of(2024, 6, 9), checkoutDate);
    }

    @Test
    @DisplayName("Should generate rental agreement")
    void shouldGenerateRentalAgreement() {
        Checkout checkout = new Checkout("CHNS", 4, 0, "06/09/93");
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        Assertions.assertAll(() -> assertEquals(rentalAgreement.tool.getCode(), checkout.toolCode),
                             () -> assertEquals(rentalAgreement.numRentalDays, checkout.numRentalDays),
                             () -> assertEquals(rentalAgreement.checkoutDate, checkout.checkoutDate),
                             () -> assertEquals(rentalAgreement.discountPercent, checkout.discountPercent));
    }
}