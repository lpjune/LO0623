
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
        Assertions.assertAll(() -> assertEquals(checkout.getToolCode(), "JAKR"),
                () -> assertEquals(checkout.getNumRentalDays(), 5),
                () -> assertEquals(checkout.getCheckoutDate(), LocalDate.of(2015, 9, 3)),
                () -> assertEquals(checkout.getDiscountPercent(), 10),
                // error message because 101 percent was invalid input
                () -> assertTrue(printed.contains("Please enter a discount percentage between 0 and 100:")));
    }

    @Test
    @DisplayName("Should run Test 2")
    void shouldRunTest2() {
        Scanner scanner = new Scanner("2" + "\n" + "7/2/20" + "\n" + "3" + "\n" + "10");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> assertEquals(checkout.getToolCode(), "LADW"),
                () -> assertEquals(checkout.getNumRentalDays(), 3),
                () -> assertEquals(checkout.getCheckoutDate(), LocalDate.of(2020, 7, 2)),
                () -> assertEquals(checkout.getDiscountPercent(), 10));
    }

    @Test
    @DisplayName("Should run Test 3")
    void shouldRunTest3() {
        Scanner scanner = new Scanner("1" + "\n" + "7/2/15" + "\n" + "5" + "\n" + "25");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> assertEquals(checkout.getToolCode(), "CHNS"),
                () -> assertEquals(checkout.getNumRentalDays(), 5),
                () -> assertEquals(checkout.getCheckoutDate(), LocalDate.of(2015, 7, 2)),
                () -> assertEquals(checkout.getDiscountPercent(), 25));
    }

    @Test
    @DisplayName("Should run Test 4")
    void shouldRunTest4() {
        Scanner scanner = new Scanner("3" + "\n" + "9/3/15" + "\n" + "6" + "\n" + "0");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> assertEquals(checkout.getToolCode(), "JAKD"),
                () -> assertEquals(checkout.getCheckoutDate(), LocalDate.of(2015, 9, 3)),
                () -> assertEquals(checkout.getNumRentalDays(), 6),
                () -> assertEquals(checkout.getDiscountPercent(), 0));
    }

    @Test
    @DisplayName("Should run Test 5")
    void shouldRunTest5() {
        Scanner scanner = new Scanner("4" + "\n" + "7/2/15" + "\n" + "9" + "\n" + "0");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> assertEquals(checkout.getToolCode(), "JAKR"),
                () -> assertEquals(checkout.getCheckoutDate(), LocalDate.of(2015, 7, 2)),
                () -> assertEquals(checkout.getNumRentalDays(), 9),
                () -> assertEquals(checkout.getDiscountPercent(), 0));
    }

    @Test
    @DisplayName("Should run Test 6")
    void shouldRunTest6() {
        Scanner scanner = new Scanner("4" + "\n" + "7/2/20" + "\n" + "4" + "\n" + "50");
        checkout = new Checkout(toolStock);
        checkout.startCheckout(scanner);
        scanner.close();
        Assertions.assertAll(() -> assertEquals(checkout.getToolCode(), "JAKR"),
                () -> assertEquals(checkout.getCheckoutDate(), LocalDate.of(2020, 7, 2)),
                () -> assertEquals(checkout.getNumRentalDays(), 4),
                () -> assertEquals(checkout.getDiscountPercent(), 50));
    }
}