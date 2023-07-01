
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class MainTest {

    private static final List<String> toolStock = Arrays.asList("CHNS", "LADW", "JAKD", "JAKR");
    private static Checkout checkout;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;


    @BeforeAll
    static void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should run Test 1")
    void shouldRunTest1() {
        Scanner scanner = new Scanner("4" + "\n" + "09/03/15" + "\n" + "5" + "\n" + "101" + "\n" + "10");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        List<String> printed = List.of(outContent.toString().split("\n"));
        scanner.close();
        Assertions.assertAll(() -> Assertions.assertEquals(checkout.getToolCode(), "JAKR"),
                () -> Assertions.assertEquals(checkout.getNumRentalDays(), 5),
                () -> Assertions.assertEquals(checkout.getCheckoutDate(), LocalDate.of(2015, 9, 3)),
                () -> Assertions.assertEquals(checkout.getDiscountPercent(), 10),
                // error message because 101 percent was invalid input
                () -> Assertions.assertTrue(printed.contains(Checkout.DISCOUNT_PERCENT_ERR_MSG)));
    }

    @Test
    @DisplayName("Should run Test 2")
    void shouldRunTest2() {
        Scanner scanner = new Scanner("2" + "\n" + "7/2/20" + "\n" + "3" + "\n" + "10");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> Assertions.assertEquals(checkout.getToolCode(), "LADW"),
                () -> Assertions.assertEquals(checkout.getNumRentalDays(), 3),
                () -> Assertions.assertEquals(checkout.getCheckoutDate(), LocalDate.of(2020, 7, 2)),
                () -> Assertions.assertEquals(checkout.getDiscountPercent(), 10));
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        Assertions.assertAll(() -> Assertions.assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2020, 7, 5)),
                             () -> Assertions.assertEquals(rentalAgreement.getNumChargeDays(), 2),
                             () -> Assertions.assertEquals(rentalAgreement.getPreDiscountCharge(), BigDecimal.valueOf(3.98)),
                             () -> Assertions.assertEquals(rentalAgreement.getDiscountAmount().toString(), "0.40"),
                             () -> Assertions.assertEquals(rentalAgreement.getFinalCharge().toString(), "3.58"));
    }

    @Test
    @DisplayName("Should run Test 3")
    void shouldRunTest3() {
        Scanner scanner = new Scanner("1" + "\n" + "7/2/15" + "\n" + "5" + "\n" + "25");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> Assertions.assertEquals(checkout.getToolCode(), "CHNS"),
                () -> Assertions.assertEquals(checkout.getNumRentalDays(), 5),
                () -> Assertions.assertEquals(checkout.getCheckoutDate(), LocalDate.of(2015, 7, 2)),
                () -> Assertions.assertEquals(checkout.getDiscountPercent(), 25));
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        Assertions.assertAll(() -> Assertions.assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2015, 7, 7)),
                () -> Assertions.assertEquals(rentalAgreement.getNumChargeDays(), 3),
                () -> Assertions.assertEquals(rentalAgreement.getPreDiscountCharge(), BigDecimal.valueOf(4.47)),
                () -> Assertions.assertEquals(rentalAgreement.getDiscountAmount().toString(), "1.12"),
                () -> Assertions.assertEquals(rentalAgreement.getFinalCharge().toString(), "3.35"));
    }

    @Test
    @DisplayName("Should run Test 4")
    void shouldRunTest4() {
        Scanner scanner = new Scanner("3" + "\n" + "9/3/15" + "\n" + "6" + "\n" + "0");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> Assertions.assertEquals(checkout.getToolCode(), "JAKD"),
                () -> Assertions.assertEquals(checkout.getCheckoutDate(), LocalDate.of(2015, 9, 3)),
                () -> Assertions.assertEquals(checkout.getNumRentalDays(), 6),
                () -> Assertions.assertEquals(checkout.getDiscountPercent(), 0));
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        Assertions.assertAll(() -> Assertions.assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2015, 9, 9)),
                () -> Assertions.assertEquals(rentalAgreement.getNumChargeDays(), 3),
                () -> Assertions.assertEquals(rentalAgreement.getPreDiscountCharge(), BigDecimal.valueOf(8.97)),
                () -> Assertions.assertEquals(rentalAgreement.getDiscountAmount().toString(), "0"),
                () -> Assertions.assertEquals(rentalAgreement.getFinalCharge().toString(), "8.97"));
    }

    @Test
    @DisplayName("Should run Test 5")
    void shouldRunTest5() {
        Scanner scanner = new Scanner("4" + "\n" + "7/2/15" + "\n" + "9" + "\n" + "0");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> Assertions.assertEquals(checkout.getToolCode(), "JAKR"),
                () -> Assertions.assertEquals(checkout.getCheckoutDate(), LocalDate.of(2015, 7, 2)),
                () -> Assertions.assertEquals(checkout.getNumRentalDays(), 9),
                () -> Assertions.assertEquals(checkout.getDiscountPercent(), 0));
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        Assertions.assertAll(() -> Assertions.assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2015, 7, 11)),
                () -> Assertions.assertEquals(rentalAgreement.getNumChargeDays(), 5),
                () -> Assertions.assertEquals(rentalAgreement.getPreDiscountCharge(), BigDecimal.valueOf(14.95)),
                () -> Assertions.assertEquals(rentalAgreement.getDiscountAmount().toString(), "0"),
                () -> Assertions.assertEquals(rentalAgreement.getFinalCharge().toString(), "14.95"));
    }

    @Test
    @DisplayName("Should run Test 6")
    void shouldRunTest6() {
        Scanner scanner = new Scanner("4" + "\n" + "7/2/20" + "\n" + "4" + "\n" + "50");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> Assertions.assertEquals(checkout.getToolCode(), "JAKR"),
                () -> Assertions.assertEquals(checkout.getCheckoutDate(), LocalDate.of(2020, 7, 2)),
                () -> Assertions.assertEquals(checkout.getNumRentalDays(), 4),
                () -> Assertions.assertEquals(checkout.getDiscountPercent(), 50));
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        Assertions.assertAll(() -> Assertions.assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2020, 7, 6)),
                () -> Assertions.assertEquals(rentalAgreement.getNumChargeDays(), 1),
                () -> Assertions.assertEquals(rentalAgreement.getPreDiscountCharge(), BigDecimal.valueOf(2.99)),
                () -> Assertions.assertEquals(rentalAgreement.getDiscountAmount().toString(), "1.50"),
                () -> Assertions.assertEquals(rentalAgreement.getFinalCharge().toString(), "1.49"));
    }
}