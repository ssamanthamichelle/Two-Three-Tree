//Samantha Michelle Garcia

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.Scanner;

public class Solution extends TwoThree {
	 
	public static void main(String[] args) throws Exception {
		   TwoThreeTree myTree = new TwoThreeTree();    //create new tree
		   
		   //creating BufferedWriter
		    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);
		   
		    Scanner input = new Scanner(System.in);

		    int size = Integer.parseInt(input.nextLine());

		    String line = "";
		    String [] split = new String[2];

		    line = input.nextLine();
		    
		    for (int i = 0; i < size; i++)
		    {
		        split = line.split(" ");
		        
		        TwoThree.insert(split[0], Integer.parseInt(split[1]), myTree);
		        
		        line = input.nextLine();
		    } 
		    
		    //after this loop is finished,
		    //line will be the next line which is the number of queries

		    int numQuery = Integer.parseInt(line);
		    
		    String query = "";
		    

		    String [] qPlanets = new String[2];    //to store queried planets
		    
		    String temp = ""; //will use in swapping order maybe
		    
		    
		    for (int j = 0; j < numQuery; j++) //for each query
		    {
		    	
		    	query = input.nextLine();
		        qPlanets = query.split(" ");

		        //compare planets and swap order if necessary
		        // .compareTo returns > 0, planets are not in order, need to swap
		        // .compareTo returns <= 0, planets are in order, don't need to swap
		        
		        if (qPlanets[0].compareTo(qPlanets[1]) > 0)    //planets need to be swapped
		        {    temp = qPlanets[0];
		            qPlanets[0] = qPlanets[1];
		            qPlanets[1] = temp;
		        }    
		        
		        TwoThree.PrintRange(myTree.root, qPlanets[0], qPlanets[1], myTree.height, output);
		    
		    }
		    
		    input.close();
		    output.flush(); 
		    output.close();
		   
	}//main
	
	
}//Solution



