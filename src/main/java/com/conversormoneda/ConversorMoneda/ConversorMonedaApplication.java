package com.conversormoneda.ConversorMoneda;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConversorMonedaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConversorMonedaApplication.class, args);
		
        try {
            Conversor.userMenu();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error en ejecución: " + e.getMessage());
        }
    }
}