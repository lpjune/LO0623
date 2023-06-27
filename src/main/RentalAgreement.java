import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalAgreement {
    Tool tool;
    int numRentalDays;
    LocalDate checkoutDate;
    int discountPercent;
    ToolFactory toolFactory = new ToolFactory();


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

    public void printAgreement() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        System.out.printf("main.Tool code: %s\n", this.tool.getCode());
        System.out.printf("main.Tool type: %s\n", this.tool.getToolTypeName());
        System.out.printf("main.Tool brand: %s\n", this.tool.getBrand());
        System.out.printf("Rental days: %d\n", this.numRentalDays);
        System.out.printf("Check out date: %s\n", dateTimeFormatter.format(this.checkoutDate));
        System.out.printf("Due date: %s\n", dateTimeFormatter.format(this.dueDate()));
        System.out.printf("Daily rental charge: %s\n", numberFormat.format(this.tool.getDailyCharge()));
        System.out.printf("Charge days: %d\n", this.numChargeDays(this.tool));
        System.out.printf("Pre-discount charge: %s\n", numberFormat.format(this.preDiscountCharge()));
        System.out.printf("Discount percent: %d%%\n", this.discountPercent);
        System.out.printf("Discount amount: %s\n", numberFormat.format(this.discountAmount()));
        System.out.printf("Final charge: %s\n", numberFormat.format(this.finalCharge()));
    }
}
