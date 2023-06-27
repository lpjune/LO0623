public class Main {
    public static void main(String[] args) {
        Checkout checkout = new Checkout("CHNS",3,0,"09/04/23");
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        rentalAgreement.printAgreement();
    }
}