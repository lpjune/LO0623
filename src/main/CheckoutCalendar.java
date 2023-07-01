package main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

public class CheckoutCalendar {
    private static CheckoutCalendar checkoutCalendar = null;
    private final int year;
    private List<LocalDate> holidays;

    private CheckoutCalendar(int year) {
        this.year = year;
        this.setHolidays();
    }

    public static CheckoutCalendar getInstance() {
        return checkoutCalendar;
    }

    public synchronized static CheckoutCalendar init(int year) {
        checkoutCalendar = new CheckoutCalendar(year);
        return checkoutCalendar;
    }

    private void setHolidays() {
        // Independence Day
        LocalDate julyFourth = LocalDate.of(this.year, 7, 4);
        if (julyFourth.getDayOfWeek() == DayOfWeek.SATURDAY) {
            julyFourth = julyFourth.minusDays(1);
        } else if (julyFourth.getDayOfWeek() == DayOfWeek.SUNDAY) {
            julyFourth = julyFourth.plusDays(1);
        }
        // Labor Day
        LocalDate laborDay = LocalDate.of(this.year, 9, 1);
        laborDay = laborDay.with(firstInMonth(DayOfWeek.MONDAY));
        this.holidays = new ArrayList<>();
        this.holidays.add(0, julyFourth);
        this.holidays.add(1, laborDay);
    }

    public boolean isWeekday(LocalDate localDate) {
        DayOfWeek day = localDate.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }

    public boolean isWeekend(LocalDate localDate) {
        DayOfWeek day = localDate.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public boolean isHoliday(LocalDate localDate) {
        return this.holidays.contains(localDate);
    }
}
