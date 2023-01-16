package com.ThParkers.TheParkers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class TheParkersApplication {
	public static int idUser=0;
	public static int idPlanta=0;
	public static void main(String[] args) throws IOException {
			String archivo;
			read_file leer = new read_file();
			String filePath = new File("").getAbsolutePath();
		try {
			archivo = leer.readFile(filePath + "/galletas.txt");
			String lines[] = archivo.split("\\r?\\n");
			if (lines.length == 2) {
				if (lines[0].startsWith("USER-ID:")) {
					String[] parts = lines[0].split("\\:");
					idUser = Integer.valueOf(parts[1]);

				} else {
					System.out.println("¡No hay ID de usuario para leer!");
					System.exit(-1);
				}
				if (lines[1].startsWith("PLANTA-ID:")) {
					String[] parts = lines[1].split("\\:");
					idPlanta = Integer.valueOf(parts[1]);

				} else {
					System.out.println("¡No hay ID de planta para leer!");
					System.exit(-1);
				}
			} else {
				System.out.println("¡El archivo de sesión está corrupto!");
			}
		}
		catch(Exception e) {
			System.out.println("¡No se ha podido hallar el archivo \"galletas.txt\"! Asegúrate de que esté en la raíz del proyecto (" + filePath + ").");
			System.exit(-1);
		}
		SpringApplication.run(TheParkersApplication.class, args);
	}

}
