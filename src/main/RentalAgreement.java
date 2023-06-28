import tool.Tool;
import tool.ToolFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalAgreement {
    private Tool tool;
    private int numRentalDays;
    private LocalDate checkoutDate;
    private int discountPercent;
    private ToolFactory toolFactory = new ToolFactory();


    public RentalAgreement(String toolCode, int numRentalDays, LocalDate checkoutDate, int discountPercent) {
        this.tool = toolFactory.getTool(toolCode);
        this.numRentalDays = numRentalDays;
        this.checkoutDate = checkoutDate;
        this.discountPercent = discountPercent;
    }

    private LocalDate dueDate() {
        return checkoutDate.plusDays(this.numRentalDays);
    };
    
    private Integer numChargeDays(Tool tool) {
        CheckoutCalendar checkoutCalendar = CheckoutCalendar.getInstance();
        int chargeDays = 0;
        for (int i = 1; i < this.numRentalDays + 1; i++) {
            LocalDate day = checkoutDate.plusDays(i);
            if (checkoutCalendar.isHoliday(day)) {
                if (tool.getIsHolidayCharge()) {
                    chargeDays++;
                }
                continue;
            }
            if (checkoutCalendar.isWeekday(day) && tool.getIsWeekdayCharge()) {
                chargeDays++;
            }
            if (checkoutCalendar.isWeekend(day) && tool.getIsWeekendCharge()) {
                chargeDays++;
            }
        }
        return chargeDays;
    }

    private BigDecimal preDiscountCharge() {
        return (this.tool.getDailyCharge().multiply(BigDecimal.valueOf(this.numChargeDays(this.tool))))
                .round(new MathContext(2, RoundingMode.HALF_UP));
    }

    private BigDecimal discountAmount() {
        if (discountPercent != 0) {
            return this.preDiscountCharge().multiply(BigDecimal.valueOf(this.discountPercent / 100))
                    .round(new MathContext(2, RoundingMode.HALF_UP));
        } else {
            return BigDecimal.valueOf(0);
        }
    }

    private BigDecimal finalCharge() {
        return this.preDiscountCharge().subtract(this.discountAmount());
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return (
                "Rental days: " + this.numRentalDays + "\n" +
                "Check out date: " + dateTimeFormatter.format(this.checkoutDate) + "\n" +
                "Due date: " + dateTimeFormatter.format(this.dueDate()) + "\n" +
                "Daily rental charge: " + currencyFormat.format(this.tool.getDailyCharge()) + "\n" +
                "Charge days: " + this.numChargeDays(this.tool) + "\n" +
                "Pre-discount charge: " + currencyFormat.format(this.preDiscountCharge()) + "\n" +
                "Discount percent: " + this.discountPercent + "%\n" +
                "Discount amount: " + currencyFormat.format(this.discountAmount()) + "\n" +
                "Final charge: " + currencyFormat.format(this.finalCharge())
                );
    };

    public void printAgreement() {
        System.out.println(this.tool.toString());
        System.out.println(this);
    }

    public Tool getTool() {
        return tool;
    }

    public int getNumRentalDays() {
        return numRentalDays;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }
}
