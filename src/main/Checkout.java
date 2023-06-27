import java.time.LocalDate;

public class Checkout {
    String toolCode;
    int numRentalDays;
    int discountPercent;
    String stringCheckoutDate;
    LocalDate checkoutDate;


    public Checkout(String toolCode, int numRentalDays, int discountPercent, String stringCheckoutDate) {
        this.toolCode = toolCode;
        this.numRentalDays = numRentalDays;
        this.discountPercent = discountPercent;
        this.stringCheckoutDate = stringCheckoutDate;
        this.checkoutDate = constructCheckoutDate(stringCheckoutDate);
        CheckoutCalendar.init(checkoutDate.getYear());
    }

    public LocalDate constructCheckoutDate(String stringDate) {
        stringDate = stringDate.replace("/", "");
        int month = Integer.parseInt(stringDate.substring(0, 2));
        int day = Integer.parseInt(stringDate.substring(2, 4));
        int year = Integer.parseInt("20" + stringDate.substring(4));
        return LocalDate.of(year, month, day);
    }

    public RentalAgreement generateRentalAgreement() {
        return new RentalAgreement(this.toolCode, this.numRentalDays, this.checkoutDate, this.discountPercent);
    }

    // TODO: input validation
}
