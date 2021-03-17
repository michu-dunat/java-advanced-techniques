package com.md5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Scanner;

public class ChangesDetector {

public static void main(String[] args) {
	
	}

	private static String parsePath(Path absolutePath) {
		String parsedPath = absolutePath.toString();
		parsedPath = parsedPath.replace("\\", "");
		parsedPath = parsedPath.replace(":", "");
		parsedPath = parsedPath.toLowerCase();
		return parsedPath;
	}

	private static Set<Path> getFilesInDirectory(Path pathToDirectory) throws IOException {
	    Set<Path> allFilesInDirectory = Files.list(pathToDirectory)
	          .filter(Files::isRegularFile)
	          .collect(Collectors.toSet());
	    
	    return allFilesInDirectory;
	}
	
	private static String parseBytes(byte[] digest) {
		
		StringBuilder stringBuilder = new StringBuilder(digest.length * 2);
	
		for (byte b : digest) 
			stringBuilder.append(String.format("%02x", b));
		
		return stringBuilder.toString();
	}
	
	public static HashMap<String, String> generateMd5ForFiles(Path absolutePath) throws NoSuchAlgorithmException, IOException{
		
		Set<Path> files = getFilesInDirectory(absolutePath);
		
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		HashMap<String, String> filesAndTheirsMd5 = new HashMap<>();
		
		for (Path file : files) {
			InputStream inputStream = Files.newInputStream(file);
			byte[] digest = messageDigest.digest(inputStream.readAllBytes());
			String md5String = parseBytes(digest);
			filesAndTheirsMd5.put(file.getFileName().toString(), md5String);
		}
		
		return filesAndTheirsMd5;
	}

	public static void saveSnapshot(Path absolutePath) throws IOException, NoSuchAlgorithmException {
		
		HashMap<String, String> map = generateMd5ForFiles(absolutePath);
		
		String pathString = parsePath(absolutePath);
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathString + ".txt"));
		
		for(Map.Entry<String, String> entry : map.entrySet()){
			bufferedWriter.write(entry.getKey() + " = " + entry.getValue());
			bufferedWriter.newLine();
		}
		
		bufferedWriter.close();
	}
	
	public static HashMap<String, String> readSnapshot(Path absolutePath) throws IOException {
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		String pathString = parsePath(absolutePath);
		
		File input = new File(pathString + ".txt");
		if (!input.exists()) {
			return null;
		}
		Scanner scanner = new Scanner(input);
		
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] fileNameAndMd5 = data.split(" = ");	
			map.put(fileNameAndMd5[0], fileNameAndMd5[1]);
        }
		
		scanner.close();
		return map;
	}
	
	public static void detectChanges(HashMap<String, String> snapshotState, HashMap<String, String> currentState) {
        int amountOfFilesAddedOrRemoved;
        if (snapshotState.size() == currentState.size()) {
            System.out.println("No files were added/removed.");
        } else if (snapshotState.size() > currentState.size()) {
            amountOfFilesAddedOrRemoved = snapshotState.size() - currentState.size();
            if (amountOfFilesAddedOrRemoved == 1) {
				System.out.println("1 file was removed");
			} else {
				System.out.println(amountOfFilesAddedOrRemoved + " files were removed.");
			}
        } else {
            amountOfFilesAddedOrRemoved = currentState.size() - snapshotState.size();
            if (amountOfFilesAddedOrRemoved == 1) {
				System.out.println("1 file was added");
			} else {
				System.out.println(amountOfFilesAddedOrRemoved + " files were added.");
			}
		}
        
        
        for (Map.Entry<String, String> entry : currentState.entrySet()) {
            String snapshotMd5 = snapshotState.get(entry.getKey());
            String currentMd5 = entry.getValue();
            if (!currentMd5.equals(snapshotMd5)) {
                System.out.println(entry.getKey() + " was changed.");
            }
        }
    }
}