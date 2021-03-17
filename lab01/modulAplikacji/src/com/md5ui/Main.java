package com.md5ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

import com.md5.ChangesDetector;

public class Main {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		System.setProperty("java.security.policy", "./java.policy");
		System.setSecurityManager(new SecurityManager());
		
		displayMainMenu();
	}
	
	private static void displayMainMenu() throws NoSuchAlgorithmException, IOException {
		Scanner scanner = new Scanner(System.in);
		String inputString;
		int inputInt;
		System.out.println("Micha³ Dunat 248862 Java MD5");
		do {
			System.out.println("\nWybierz akcje:");
			System.out.println("1. Wykonaj snapshot wybranego folderu.");
			System.out.println("2. Wykryj czy wprowadzono zmiany w wybranym folderze od ostatniego snapshota.");
			System.out.println("0. Wyjœcie z programu.");
			inputString = scanner.nextLine();
			inputInt = Integer.parseInt(inputString);
			Path path;
			if (inputInt == 1) {
				path = inputPath(scanner);
				if (Files.exists(path)) {
					ChangesDetector.saveSnapshot(path);
				} else {
					System.out.println("Wprowadzono b³êdn¹ œcie¿kê.");
				}
			} else if (inputInt == 2) {
				path = inputPath(scanner);
				if (Files.exists(path)) {
					HashMap<String, String> snapshot = ChangesDetector.readSnapshot(path);
					if (snapshot == null) {
						System.out.println("Dla podanego folderu nie utworzono wczeœniej snapshota.");
						continue;
					}
					HashMap<String, String> current = ChangesDetector.generateMd5ForFiles(path);
					ChangesDetector.detectChanges(snapshot, current);
				} else {
					System.out.println("Wprowadzono b³êdn¹ œcie¿kê.");
				}
			}
		} while (inputInt != 0);
		scanner.close();
		System.out.println("Do widzenia!");
		
	}

	private static Path inputPath(Scanner reader) {
		System.out.println("\nWprowadŸ œcie¿kê:");
		String input = reader.nextLine();
		Path path = Paths.get(input);
		return path;
	}
	
}
