package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Memory {
    int maxMem = 4096; // might be 16 x 4096
    String[] storage = new String[maxMem];
    public void initiate(){
        try {
            int count = 0;
            String absPath = "C:\\Users\\cross\\IdeaProjects\\Sim\\resources\\MC.txt";
            File myObj = new File(absPath);
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
