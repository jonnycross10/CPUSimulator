package com.company;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
	public static class CPU {
		String instruction;
		int programCounter=0;

		public void initialize(String arg){
			// loop through fetch decode and execute
			Memory m = new Memory();
			m.initiate(arg);
			Stack<String> S = new Stack<>();
			for(int i =0; i<27;i++){
				instruction = fetch(m,i); // fetch the i'th instruction
				//System.out.println(instruction);


				S = decode(instruction, S);

			}
			System.out.println(S.peek());
		}

		public String fetch(Memory m, int i){
			//fetches instruction from memory

			return(m.storage[i]);
		}
		public Stack<String> decode(String arg, Stack<String> s){
			//decodes the fetched instruction
			if (arg.equals("D000")){ //In CL argument goes to stack
				s.push("IN");

			}

			return(s);
		}
		public void execute(){
			//execute code
			programCounter++;
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
					//System.out.println(data);
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
	c.initialize(args[0]);
    }

}

