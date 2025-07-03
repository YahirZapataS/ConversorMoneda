package com.conversormoneda.ConversorMoneda;

import java.io.IOException;
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
                case "1":
                    makeConversion();
                    break;
                case "2":
                    showCurrencies();
                    break;
                case "3":
                    System.out.println("Good Bye");
                    return;
                default:
                    System.out.println("Invalid option, Try again.");
            }
        }
    }

    private static void makeConversion() throws IOException, InterruptedException {

        Set<String> validCodes = ConversorApp.getValidCodes();

        System.out.println("Ingrese la moneda a convertir (Ejemplo: USD, MXN): ");
        String moneda = scanner.nextLine().toUpperCase();
        if(!validCodes.contains(moneda)) {
            System.out.println("Código de moneda inválido.\n Consulta los códigos de moneda en 2.");
            return;
        }

        System.out.println("Ingrese a que moneda le gustaría convertir (Ejemplo: USD, MXN): ");
        String conversion = scanner.nextLine().toUpperCase();
        if(!validCodes.contains(conversion)) {
            System.out.println("Código de moneda inválido.\\nConsulta los códigos de moneda en 2.");
            return;
        }

        System.out.println("Ingrese el monto a convertir: ");
        double monto;

        try {
            monto = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido");
            return;
        }

        double tasa = ConversorApp.getRate(moneda, conversion);
        double resultado = monto * tasa;

        System.out.printf("Tasa actual: 1 %s = %.4f %s\n", moneda, tasa, conversion);
        System.out.printf("Resultado: %.2f %s = %.2f %s\n\n", monto, moneda, resultado, conversion);
    }

    private static void showCurrencies() throws IOException, InterruptedException {
        Set<String> validCodes = ConversorApp.getValidCodes();
        System.out.println("Monedas disponibles: " + String.join(", ", validCodes));
    }
}
