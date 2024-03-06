package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import application.entities.Product;

public class Application {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Product> products = new ArrayList<>();
		
		System.out.println("Please enter the input file path: ");
		String sourceFileStr = sc.nextLine();
		
		File file = new File(sourceFileStr);
		String sourceFolder = file.getParent();
		
		boolean success = new File(sourceFolder+"\\out").mkdir();
		
		String targetFile = sourceFolder + "\\out\\summary.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))){
			
			String itemCsv = br.readLine();
			while (itemCsv != null) {
				String[] fields = itemCsv.split(",");
				
				String name = fields[0];
				Double price = Double.parseDouble(fields[1]);
				Integer quantity = Integer.parseInt(fields[2]);
				
				products.add(new Product(name, price, quantity));
				
				itemCsv = br.readLine();
			}
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile))){
				
				for (Product item:products) {
					bw.write(item.getName() + "," + String.format("%.2f", item.getTotal()));
					bw.newLine();
				}
				System.out.println(targetFile + " created!");
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
		} catch (IOException e) {
			System.out.println("Error has ocurred while writing file. Details: " + e.getMessage());
		} finally {
			if (sc != null) {
				sc.close();
			}
		}	
	}
}
