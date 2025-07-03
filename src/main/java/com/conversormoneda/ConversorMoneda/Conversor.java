package com.conversormoneda.ConversorMoneda;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Conversor {

    private static final Scanner scanner = new Scanner(System.in);

    public static void userMenu() throws IOException, InterruptedException {
        while (true) {
            System.out.println("========== Currency Converter ==========");
            System.out.println("1. Currency Converter\n2. Show Available Currencies\n3. Exit");
            System.out.println("Choose an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> makeConversion();
                case "2" -> showCurrencies();
                case "3" -> {
                    System.out.println("Good Bye");
                    return;
                }
                default -> System.out.println("Invalid option, Try again.");
            }
        }
    }

    private static void makeConversion() throws IOException, InterruptedException {

        Set<String> validCodes = ConversorApp.getValidCodes();

        System.out.println("Enter the currency that you should convert: ");
        String currency = scanner.nextLine().toUpperCase();
        if(!validCodes.contains(currency)) {
            System.out.println("Invalid currency code.\nCheck currency codes in option 2.");
            return;
        }

        System.out.println("Enter which currency you would like to convert: ");
        String conversion = scanner.nextLine().toUpperCase();
        if(!validCodes.contains(conversion)) {
            System.out.println("Invalid currency code.\nCheck currency codes in option 2.");
            return;
        }

        System.out.println("Enter the value that needs to be converted: ");
        double monto;

        try {
            monto = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid value. Try again" + e);
            return;
        }

        double tasa = ConversorApp.getRate(currency, conversion);
        double resultado = monto * tasa;

        System.out.println("===============================================");
        System.out.printf("Rate: 1 %s = %.4f %s\n", currency, tasa, conversion);
        System.out.printf("Result: %.2f %s = %.2f %s\n\n", monto, currency, resultado, conversion);
    }

    private static void showCurrencies() throws IOException, InterruptedException {
        Map<String, Double> availableCurrencies = ConversorApp.getFilterCurrencies();
        System.out.println("Availables Currencies: ");
        availableCurrencies.forEach((code, value) -> System.out.println(code + ": " + value));
    }
}
