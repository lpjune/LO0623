
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import tool.Tool;
import tool.ToolFactory;

public class RentalAgreement {
    private final Tool tool;
    private final int numRentalDays;
    private final LocalDate checkoutDate;
    private final BigDecimal discountPercent;

    private final LocalDate dueDate;
    private final int numChargeDays;
    private final BigDecimal preDiscountCharge;
    private final BigDecimal discountAmount;
    private final BigDecimal finalCharge;


    public RentalAgreement(String toolCode, int numRentalDays, LocalDate checkoutDate, int discountPercent) {
        ToolFactory toolFactory = new ToolFactory();
        this.tool = toolFactory.getTool(toolCode);
        this.numRentalDays = numRentalDays;
        this.checkoutDate = checkoutDate;
        this.discountPercent = BigDecimal.valueOf(discountPercent);

        this.dueDate = calcDueDate();
        this.numChargeDays = calcNumChargeDays(this.tool);
        this.preDiscountCharge = calcPreDiscountCharge();
        this.discountAmount = calcDiscountAmount();
        this.finalCharge = this.calcFinalCharge();
    }

    private LocalDate calcDueDate() {
        return checkoutDate.plusDays(this.numRentalDays);
    }

    private int calcNumChargeDays(Tool tool) {
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

    private BigDecimal calcPreDiscountCharge() {
        return (this.tool.getDailyCharge().multiply(BigDecimal.valueOf(this.numChargeDays)).setScale(2, RoundingMode.HALF_UP));
    }

    private BigDecimal calcDiscountAmount() {
        if (!discountPercent.equals(BigDecimal.ZERO)) {
            return this.preDiscountCharge.multiply
                    (this.discountPercent.divide(BigDecimal.valueOf(100))
                    ).setScale(2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.valueOf(0);
        }
    }

    private BigDecimal calcFinalCharge() {
        return this.preDiscountCharge.subtract(this.discountAmount);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return (
                "Rental days: " + this.numRentalDays + "\n" +
                "Check out date: " + dateTimeFormatter.format(this.checkoutDate) + "\n" +
                "Due date: " + dateTimeFormatter.format(this.dueDate) + "\n" +
                "Daily rental charge: " + currencyFormat.format(this.tool.getDailyCharge()) + "\n" +
                "Charge days: " + this.numChargeDays + "\n" +
                "Pre-discount charge: " + currencyFormat.format(this.preDiscountCharge) + "\n" +
                "Discount percent: " + this.discountPercent + "%\n" +
                "Discount amount: " + currencyFormat.format(this.discountAmount) + "\n" +
                "Final charge: " + currencyFormat.format(this.finalCharge)
                );
    }

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

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getNumChargeDays() {
        return numChargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }
}
