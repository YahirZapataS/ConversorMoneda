package com.conversormoneda.ConversorMoneda;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Conversor {

    private static final Scanner scanner = new Scanner(System.in);

    public static void userMenu() throws IOException, InterruptedException {
        while (true) {
            System.out.println("========== Conversor de Moneda ==========");
            System.out.println("1. Convertir Moneda \n2.Salir");
            System.out.println("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    realizarConversion();
                    break;
                case "2":
                System.out.println("Adios");
                return;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void realizarConversion() throws IOException, InterruptedException {

        Set<String> codigosValidos = ConversorApp.obtenerCodigosValidos();

        System.out.println("Ingrese la moneda a convertir (Ejemplo: USD, MXN): ");
        String moneda = scanner.nextLine().toUpperCase();
        if(!codigosValidos.contains(moneda)) {
            System.out.println("Código de moneda inválido.\n Consulta los códigos de moneda en 2.");
            return;
        }

        System.out.println("Ingrese a que moneda le gustaría convertir (Ejemplo: USD, MXN): ");
        String conversion = scanner.nextLine().toUpperCase();
        if(!codigosValidos.contains(conversion)) {
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

        double tasa = ConversorApp.obtenerTasa(moneda, conversion);
        double resultado = monto * tasa;

        System.out.printf("Tasa actual: 1 %s = %.4f %s\n", moneda, tasa, conversion);
        System.out.printf("Resultado: %.2f %s = %.2f %s\n\n", monto, moneda, resultado, conversion);
    }
}
