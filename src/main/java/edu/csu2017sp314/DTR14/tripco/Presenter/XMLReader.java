/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csu2017sp314.DTR14.tripco.Presenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class XMLReader {

    public String[] readSelectFile(String filename, ArrayList<File> files) throws FileNotFoundException{
		Scanner scan = new Scanner(new File(filename));
		int count = 1;
		boolean dest = false;
		boolean cont = scan.hasNextLine();
		ArrayList<String> subs = new ArrayList<String>();
		while(cont){
			String temp = scan.nextLine().trim();
			cont = scan.hasNextLine();
			switch(count){
				case 1: //Check opening XML tag
					if(temp.length() > 4 && 
						temp.substring(0, 5).equalsIgnoreCase("<?xml"))
							break;
					else return new String[0];
				case 2://Check opening selection tag
					if(temp.length() > 10 &&
						temp.substring(0, 10).equalsIgnoreCase("<selection"))
							break;
					else return new String[0];
				case 3://Check title tag
					if(temp.length() > 6 && 
						temp.substring(0, 6).equalsIgnoreCase("<title"))
							break;
					else return new String[0];
				case 4://Check opening filename tag
					if(temp.length() > 9 &&
						temp.substring(0, 9).equalsIgnoreCase("<filename")){
							temp = temp.substring(temp.indexOf('>')+1);
							temp = temp.substring(0, temp.indexOf('<'));
							if(files.isEmpty()) files.add(new File(temp));
							break;
						}
					else return new String[0];
				case 5://Check opening destinations tag
					if(temp.length() > 13 && 
						temp.substring(0, 13).equalsIgnoreCase("<destinations")){
							dest = true;
							break;
						}
					else return new String[0];
				default:{//Handle id and closing destinations/selection tag
					if(dest && temp.length() > 3 && 
						temp.substring(0, 3).equalsIgnoreCase("<id")){
							temp = temp.substring(temp.indexOf('>')+1);
							temp = temp.substring(0, temp.indexOf('<'));
							subs.add(temp.trim());
							break;
					} else if(temp.length() > 14 && 
						temp.substring(0, 14).equalsIgnoreCase("</destinations")){
							dest = false;
							break;
					}
					if(temp.length()>11){
						if(temp.substring(0, 11).equalsIgnoreCase("</selection")){
							cont = false;
							break;
						}
					}
				}
			}
			count++;
		}
		scan.close();
        return subs.toArray(new String[0]);
	}
}