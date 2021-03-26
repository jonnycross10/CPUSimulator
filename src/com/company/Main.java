package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static class CPU {
		String byte1;
		public String fetch(){
			//fetches instruction from memory
			return byte1;
		}
		public void decode(){
			//decodes the fetched instruction
		}
		public void execute(){
			//execute code and increment program counter
		}
	}
	public static class Memory {
		int maxMem = 4096; // might be 16 x 4096
		String[] storage = new String[maxMem];
		public void initiate(String arg){
			try {
				int count = 0;
				String absPath = "C:\\Users\\cross\\IdeaProjects\\Sim\\resources\\";
				File myObj = new File(absPath+arg);
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					System.out.println(data);
					storage[count] = data;
					count++;
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
	CPU c = new CPU();
	c.byte1 = args[0];
	System.out.println(c.byte1);
	Memory m = new Memory();
	m.initiate(args[0]);
    }

}

