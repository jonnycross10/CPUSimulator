package com.company;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
	public static class CPU {
		int instruction;
		int programCounter=0;


		public void initialize(String[] arg){
			/*


			 */
			System.out.println(arg.length);
			Memory m = new Memory();



			int instrLen = initiate(arg[0], m);
			Stack<Integer> S = new Stack<>();
			for(int i =0; i<instrLen;i++){
				instruction = fetch(m,i); // fetch the i'th instruction
				//System.out.println(instruction);


				S = decode(instruction, S, m);

			}
			System.out.println(S.size() + "" + S.peek());
		}

		public int initiate(String arg, Memory m){
			int count = 0;
			try {

				String absPath = "C:\\Users\\cross\\IdeaProjects\\Sim\\resources\\";
				File myObj = new File(absPath+arg);
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					System.out.print(count+": ");
					System.out.println(data);
					if(count !=0) {


						m.storage[count-1] = Integer.parseInt(data, 16);
					}
					count++;
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			return(count-1);
		}

		public String formatHex(String s){//make sure hex string is 4 bits
			while(s.length()<4){
				s = "0"+s;
			}
			return s;
		}

		public int fetch(Memory m, int i){
			//fetches instruction from memory

			return(m.storage[i]);
		}
		public Stack<Integer> decode(int arg, Stack<Integer> s, Memory m){
			//decodes the fetched instruction
			//System.out.println(arg.substring(0,1)); //test
			String hexString = Integer.toHexString(arg);
			hexString = formatHex(hexString);
			System.out.println(hexString);
			//In port
			if (hexString.equals("D000")){ //In CL argument goes to stack
				s.push(0);

			}
			//Push
			if(hexString.substring(0,2).equals("10")){ //if the instruction starts with 10

				s.push(m.storage[arg]);
			}

			return(s);
		}
		public void execute(){
			//execute code
			programCounter++;
		}
	}

	public static class Memory {
		int maxMem = 4096 * 16; // might be 16 x 4096
		Integer[] storage = new Integer[maxMem];

	}

	public static void main(String[] args) {
	CPU c = new CPU();
	c.initialize(args);
    }

}

