package com.company;

public class CPU {
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
