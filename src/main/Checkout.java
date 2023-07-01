

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Checkout {
    private String toolCode;
    private int numRentalDays;
    private int discountPercent;
    private LocalDate checkoutDate;

    List<String> toolStock;
    private static Pattern DATE_PATTERN = Pattern.compile(
            // February only has 28 days
            "^(02 | 2)/(0?[1-9]|1[0-9]|2[0-8])$"
            // Months with 31 days
            + "|^((0?[13578]|10|12)/(0?[1-9]|[12][0-9]|3[01])/([0-9]{2}))$"
            // Months with 30 days
            + "|^((0?[469]|11)/(0?[1-9]|[12][0-9]|30)/([0-9]{2}))$"
    );

    public Checkout(List<String> toolStock) {
        this.toolStock = toolStock;
    }

    public void startCheckout(Scanner scanner) {
        this.toolCode = getToolCodeInput(scanner);
        this.checkoutDate = getCheckoutDateInput(scanner);
        this.numRentalDays = getNumRentalDaysInput(scanner);
        this.discountPercent = getDiscountPercentInput(scanner);
        CheckoutCalendar.init(checkoutDate.getYear());
    }

    public String getToolCodeInput(Scanner scanner) {
        int toolNumber;
        System.out.println("Please select a tool by code: ");
        for (int i = 0; i < toolStock.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, toolStock.get(i));
        }
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number corresponding to one of the tool codes above: ");
                scanner.next();
            }
            toolNumber = scanner.nextInt();
            if (toolNumber <= 0 || toolNumber > toolStock.size()) {
                System.out.println("Please enter a number corresponding to one of the tool codes above: ");
            }
        } while (toolNumber <= 0 || toolNumber > toolStock.size());
        return toolStock.get(toolNumber - 1);
    };

    public LocalDate getCheckoutDateInput(Scanner scanner) {
        String checkoutDateString;
        System.out.println("Please enter the checkout date (mm/dd/yy): ");
        scanner.nextLine();
        do {
            while (!scanner.hasNextLine()) {
                scanner.next();
            }
            checkoutDateString = scanner.nextLine();

            if (!DATE_PATTERN.matcher(checkoutDateString).matches()) {
                System.out.println("Please enter the checkout date in the format (mm/dd/yy): ");
            }
        } while (!DATE_PATTERN.matcher(checkoutDateString).matches());
        return constructCheckoutDate(checkoutDateString);
    }

    public int getNumRentalDaysInput(Scanner scanner) {
        int numRentalDays;
        System.out.println("Please enter the number of rental days:");
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number of rental days:");
                scanner.next();
            }
            numRentalDays = scanner.nextInt();
            if (numRentalDays <= 0) {
                System.out.println("Please enter the number of rental days (1 or more):");
            }
        } while (numRentalDays <= 0);
        return numRentalDays;
    }

    public int getDiscountPercentInput(Scanner scanner) {
        int discountPercent;
        System.out.println("Please enter a discount percentage:");
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a discount percentage between 0 and 100:");
                scanner.next();
            }
            discountPercent = scanner.nextInt();
            if (discountPercent < 0 || discountPercent > 100) {
                System.out.println("Please enter a discount percentage between 0 and 100:");
            }
        } while (discountPercent < 0 || discountPercent > 100);
        return discountPercent;
    }



    public LocalDate constructCheckoutDate(String stringDate) {
        List<String> arrayDate = List.of(stringDate.split("/"));

        int month = Integer.parseInt(arrayDate.get(0));
        int day = Integer.parseInt(arrayDate.get(1));
        int year = Integer.parseInt("20" + arrayDate.get(2));
        return LocalDate.of(year, month, day);
    }

    public RentalAgreement generateRentalAgreement() {
        return new RentalAgreement(this.toolCode, this.numRentalDays, this.checkoutDate, this.discountPercent);
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public int getNumRentalDays() {
        return numRentalDays;
    }

    public void setNumRentalDays(int numRentalDays) {
        this.numRentalDays = numRentalDays;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
