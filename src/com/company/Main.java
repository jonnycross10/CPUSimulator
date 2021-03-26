package com.company;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
	CPU c = new CPU();
	c.byte1 = args[0];
	System.out.println(c.byte1);

		try {
			File myObj = new File("C:\\Users\\cross\\IdeaProjects\\Sim\\resources\\MC.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

    }

}
