package main;

import main.Checkout;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<String> toolStock = Arrays.asList("CHNS", "LADW", "JAKD", "JAKR");
    private static Checkout checkout;

    public static void main(String[] args) {
        checkout = new Checkout(toolStock);
        Scanner scanner = new Scanner(System.in);
        checkout.startCheckout(scanner);
        RentalAgreement rentalAgreement = checkout.generateRentalAgreement();
        rentalAgreement.printAgreement();
    }
}