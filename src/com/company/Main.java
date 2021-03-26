package com.company;

public class Main {

    public static void main(String[] args) {
	CPU c = new CPU();
	c.byte1 = args[0];
	System.out.println(c.byte1);
	Memory m = new Memory();
	m.initiate();
    }

}
