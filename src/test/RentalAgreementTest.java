import main.CheckoutCalendar;
import main.RentalAgreement;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RentalAgreementTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    static void setUp() {
        CheckoutCalendar.init(2023);
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should print formatted rental agreement")
    void shouldPrintFormattedRentalAgreement() {
        RentalAgreement rentalAgreement = new RentalAgreement(
                "CHNS",
                4,
                LocalDate.of(2023, 6, 26),
                10
        );
        rentalAgreement.printAgreement();
        List<String> printed = List.of(outContent.toString().split("\n"));
        Assertions.assertAll(() -> assertTrue(printed.contains("Discount percent: 10%")),
                             () -> assertFalse(printed.contains("Discount percent: 11%"))
        );
    }

}