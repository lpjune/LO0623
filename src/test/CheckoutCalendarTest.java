import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class CheckoutCalendarTest {

    @Test
    @DisplayName("Should create and get checkout calendar")
    void shouldCreateAndGetCheckoutCalendar() {
        CheckoutCalendar.init(2023);
        assertNotNull(CheckoutCalendar.getInstance());
    }

    @Test
    @DisplayName("Should check if date is weekday")
    void shouldCheckIfDateIsWeekday() {
        CheckoutCalendar.init(2023);
        LocalDate isMonday = LocalDate.of(2023, 6, 26);
        LocalDate isSaturday = LocalDate.of(2023, 6, 24);
        Assertions.assertTrue(CheckoutCalendar.getInstance().isWeekday(isMonday));
        Assertions.assertFalse(CheckoutCalendar.getInstance().isWeekday(isSaturday));
    }

    @Test
    @DisplayName("Should check if date is weekend")
    void shouldCheckIfDateIsWeekend() {
        CheckoutCalendar.init(2023);
        LocalDate isMonday = LocalDate.of(2023, 6, 26);
        LocalDate isSaturday = LocalDate.of(2023, 6, 24);
        Assertions.assertFalse(CheckoutCalendar.getInstance().isWeekend(isMonday));
        Assertions.assertTrue(CheckoutCalendar.getInstance().isWeekend(isSaturday));
    }

    @Test
    @DisplayName("Should check if date is holiday")
    void shouldCheckIfDateIsHoliday() {
        CheckoutCalendar.init(2023);
        LocalDate independenceDay = LocalDate.of(2023, 7, 4);
        LocalDate notLaborDay = LocalDate.of(2023, 10, 10);
        Assertions.assertTrue(CheckoutCalendar.getInstance().isHoliday(independenceDay));
        Assertions.assertFalse(CheckoutCalendar.getInstance().isHoliday(notLaborDay));
    }
}