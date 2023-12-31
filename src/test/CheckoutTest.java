import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CheckoutTest {

    private static final List<String> toolStock = Arrays.asList("CHNS", "LADW", "JAKD", "JAKR");
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static Checkout checkout;

    @BeforeAll
    static void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should start checkout")
    void shouldStartCheckout() {
        Scanner scanner = new Scanner("2" + "\n" + "3/9/24" + "\n" + "3" + "\n" + "10");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> assertEquals(checkout.getToolCode(), "LADW"),
                () -> assertEquals(checkout.getNumRentalDays(), 3),
                () -> assertEquals(checkout.getCheckoutDate(), LocalDate.of(2024, 3, 9)),
                () -> assertEquals(checkout.getDiscountPercent(), 10));
    }

    @Test
    @DisplayName("Should get tool code input")
    void shouldGetToolCodeInput() {
        checkout = new Checkout(toolStock);
        Scanner scanner = new Scanner("a" + "\n" + "\n" + "0" + "\n" + "2");
        String code = checkout.getToolCodeInput(scanner);
        List<String> printed = List.of(outContent.toString().split("\n"));
        Assertions.assertTrue(printed.contains(Checkout.TOOL_CODE_ERR_MSG));
        Assertions.assertEquals(code, "LADW");
    }

    @Test
    @DisplayName("Should get number of rental days input")
    void shouldGetNumberOfRentalDaysInput() {
        checkout = new Checkout(toolStock);
        Scanner scanner = new Scanner("0" + "\n" + "a" + "\n" + "3");
        int numDays = checkout.getNumRentalDaysInput(scanner);
        List<String> printed = List.of(outContent.toString().split("\n"));
        Assertions.assertTrue(printed.contains(Checkout.NUM_RENTAL_DAYS_ERR_MSG));
        Assertions.assertEquals(numDays, 3);
    }

    @Test
    @DisplayName("Should get checkout date input")
    void shouldGetCheckoutDateInput() {
        checkout = new Checkout(toolStock);
        Scanner scanner = new Scanner("\r\n" + "a" + "\n" + "3" + "\n" + "6/9/24");
        LocalDate checkoutDate = checkout.getCheckoutDateInput(scanner);
        List<String> printed = List.of(outContent.toString().split("\n"));
        Assertions.assertTrue(printed.contains(Checkout.CHECKOUT_DATE_ERR_MSG));
        Assertions.assertEquals(checkoutDate, LocalDate.of(2024, 6, 9));
    }

    @Test
    @DisplayName("Should get discount percent input")
    void shouldGetDiscountPercentInput() {
        checkout = new Checkout(toolStock);
        Scanner scanner = new Scanner("a" + "\n" + "-1" + "\n" + "15");
        int discountPercent = checkout.getDiscountPercentInput(scanner);
        List<String> printed = List.of(outContent.toString().split("\n"));
        Assertions.assertTrue(printed.contains(Checkout.DISCOUNT_PERCENT_ERR_MSG));
        Assertions.assertEquals(discountPercent, 15);
    }

    @Test
    @DisplayName("Should construct checkout date")
    void shouldConstructCheckoutDate() {
        checkout = new Checkout(toolStock);

        LocalDate checkoutDate = checkout.constructCheckoutDate("6/27/23");
        Assertions.assertEquals(LocalDate.of(2023, 6, 27), checkoutDate);
    }

    @Test
    @DisplayName("Should generate rental agreement")
    void shouldGenerateRentalAgreement() {
        Checkout checkout = new Checkout(toolStock);
        checkout.setToolCode("CHNS");
        checkout.setNumRentalDays(5);
        checkout.setCheckoutDate(LocalDate.of(2024, 6, 7));
        checkout.setDiscountPercent(20);
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        Assertions.assertAll(() -> assertEquals(rentalAgreement.getTool().getCode(), checkout.getToolCode()),
                () -> assertEquals(rentalAgreement.getNumRentalDays(), checkout.getNumRentalDays()),
                () -> assertEquals(rentalAgreement.getCheckoutDate(), checkout.getCheckoutDate()),
                () -> assertEquals(rentalAgreement.getDiscountPercent(), BigDecimal.valueOf(checkout.getDiscountPercent())));
    }
}