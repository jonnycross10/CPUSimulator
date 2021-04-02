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

			Memory m = new Memory();
			String option = "";
			String fileName = "";
			int inputInt = 0;

			//format arguments
			if(arg.length == 1){
				fileName = arg[0];
			}
			else if(arg.length == 2){
				if(arg[0].substring(0,1)=="-"){
					option = arg[0];
					fileName = arg[1];
				}
				else{
					fileName = arg[0];
					inputInt = Integer.parseInt(arg[1]);
				}
			}
			else{ //case of 3 arguments
				option = arg[0];
				fileName = arg[1];
				inputInt = Integer.parseInt(arg[2]);
			}

			int instrLen = initiate(fileName, m);
			Stack<Integer> S = new Stack<>();
			for(int i =0; i<instrLen;i++){
				instruction = fetch(m,i); // fetch the i'th instruction
				//System.out.println(instruction);


				S = decode(instruction, S, m, inputInt);
				programCounter++;

				if(option.equals("-t")) {
					//print top of stack
					try {
						System.out.println("Stack top: " + S.peek());
					} catch (EmptyStackException e) {
						System.out.println("Stack empty");
					}
				}



			}
			//System.out.println("Stack size: " + S.size());
			//System.out.println("Stack top: " + S.peek());
		}

		public int initiate(String arg, Memory m){
			int count = 0;
			try {

				String absPath = "C:\\Users\\cross\\IdeaProjects\\Sim\\resources\\";
				File myObj = new File(absPath+arg);
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
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
			return s.toUpperCase();
		}

		public int fetch(Memory m, int i){
			//fetches instruction from memory

			return(m.storage[i]);
		}


		//decode and execute instructions
		public Stack<Integer> decode(int arg, Stack<Integer> s, Memory m, int in){
			int input = in;
			int output =-1;

			//System.out.println(arg.substring(0,1)); //test
			String hexString = Integer.toHexString(arg);
			hexString = formatHex(hexString);
			//System.out.println(hexString);

			int twoBit = Integer.parseInt(hexString.substring(2,4),16);

			//Push I
			if(hexString.substring(0,2).equals("10")){ //if the instruction starts with 10
				//System.out.println(i);
				s.push(twoBit);
			}
			//Push A
			if(hexString.substring(0,2).equals("20")){

				s.push(m.storage[twoBit]);
			}

			//Pop and store in A
			//implement after IN is finished
			if(hexString.substring(0,2).equals("30")){
				m.storage[twoBit] = s.pop();
			}

			//JMP A
			if(hexString.substring(0,2).equals("40")) {

				programCounter = twoBit;
			}

			//JZ A
			if(hexString.substring(0,2).equals("50")){
				if(s.peek()==0) {
					programCounter = twoBit;
				}
			}

			//JNZ A
			if(hexString.substring(0,2).equals("60")){
				if(s.peek()!=0) {
					programCounter = twoBit;
				}
			}

			//In port
			if (hexString.equals("D000")){ //In CL argument goes to stack
				//System.out.println(input);
				s.push(input);
				//push in[port] to stack
			}

			//Out Port
			if (hexString.substring(0,2).equals("E0")){
				int a = s.pop();
				output = a;
				//pop stack onto port

			}

			//Add
			if (hexString.equals("F000")){
				int a = s.pop();
				int b = s.pop();
				s.push(a+b);
			}

			//Subtract
			if (hexString.equals("F001")){
				int a = s.pop();
				int b = s.pop();
				s.push(b-a);
			}

			//Multiply
			if (hexString.equals("F002")){
				int a = s.pop();
				int b = s.pop();
				s.push(a*b);
			}

			//Modulus
			if (hexString.equals("F003")){
				int a = s.pop();
				int b = s.pop();
				s.push(b%a);
			}

			//SHL shift left
			if (hexString.equals("F004")){
				int a = s.pop();
				int b = s.pop();
				int shift = b<<a;
				s.push(shift);
			}

			//SHR shift right
			if (hexString.equals("F005")){
				int a = s.pop();
				int b = s.pop();
				int shift = b>>a;
				s.push(shift);
			}

			//BAND B and A
			if (hexString.equals("F00A")){
				int a = s.pop();
				int b = s.pop();
				int band = b&a;
				s.push(band);
			}

			//BOR B or A
			if (hexString.equals("F00B")){
				int a = s.pop();
				int b = s.pop();
				int shft = b|a;
				s.push(shft);
			}

			//BXOR
			if (hexString.equals("F00C")){
				int a = s.pop();
				int b = s.pop();
				int shft = b^a;
				s.push(shft);
			}

			//EQ
			if (hexString.equals("F010")){
				int a = s.pop();
				int b = s.pop();
				//if equal then push 1 if not then 0
				if(b == a){
					s.push(1);
				}
				else{
					s.push(0);
				}
			}

			//NE
			if (hexString.equals("F011")){
				int a = s.pop();
				int b = s.pop();
				//if equal then push 1 if not then 0
				if(b != a){
					s.push(1);
				}
				else{
					s.push(0);
				}
			}

			//GE
			if (hexString.equals("F012")){
				int a = s.pop();
				int b = s.pop();
				//if equal then push 1 if not then 0
				if(b >= a){
					s.push(1);
				}
				else{
					s.push(0);
				}
			}

			//LE
			if (hexString.equals("F013")){
				int a = s.pop();
				int b = s.pop();
				//if equal then push 1 if not then 0
				if(b == a){
					s.push(1);
				}
				else{
					s.push(0);
				}
			}

			//GT
			if (hexString.equals("F014")){
				int a = s.pop();
				int b = s.pop();
				//if equal then push 1 if not then 0
				if(b > a){
					s.push(1);
				}
				else{
					s.push(0);
				}
			}

			//LT
			if (hexString.equals("F015")){
				int a = s.pop();
				int b = s.pop();
				//if equal then push 1 if not then 0
				if(b == a){
					s.push(1);
				}
				else{
					s.push(0);
				}
			}

			//NEG
			if (hexString.equals("F006")){
				int a = s.pop();
				a *= -1;
				s.push(a);
			}

			//BNOT Bitwise inversion
			if (hexString.equals("F00D")){
				int a = s.pop();
				s.push(~a);
			}

			if(output != -1){
				System.out.println("Output is: " +output);
			}

				return(s);
		}

	}

	public static class Memory {
		int maxMem = 4096 * 16;
		Integer[] storage = new Integer[maxMem];

	}

	public static void main(String[] args) {
	CPU c = new CPU();
	c.initialize(args);
    }

}

