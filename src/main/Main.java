import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<String> toolStock = Arrays.asList("CHNS", "LADW", "JAKD", "JAKR");
    public static void main(String[] args) {
        Checkout checkout = new Checkout(toolStock);
        Scanner scanner = new Scanner(System.in);
        checkout.startCheckout(scanner);
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        rentalAgreement.printAgreement();
    }
}