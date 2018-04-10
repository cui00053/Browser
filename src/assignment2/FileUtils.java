/**
 * File name: FileUtils.java
 * Author: Chenxiao Cui, 040879113
 * Course: CST8284 - OOP
 * Assignment: 3
 * Date: 12/01/2018
 * Professor: David Houtmad
 * Purpose: The purpose of this class is to save, delete and read content from the file .
 * 
 */

package assignment2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The purpose of this class is to save, delete and read content from the file . *  
 * @author Chenxiao Cui, David Houtmad
 * @version 2.0 
 * @since  2018-01-12
 *
 */
public class FileUtils {	
	
	
	/**
	 * Save the content of ArrayList ar to the File f.
	 * Reference: https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
	 * @param f file
	 * @param ar WebEngine
	 */
	public static void saveFileContents(File f,ArrayList<String> ar) {		
		try(FileWriter fw = new FileWriter(f,true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw)    
		){			
			for(int i=0;i<ar.size();i++) {  //save distinctive content
				//ArrayList<String> content = getFileContentAsArrayList(f);
				//if(!content.contains(ar.get(i)))
				pw.write(ar.get(i)+"\n");
			}
					
			pw.close();	
			bw.close();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();				
		}
		
	}
	
	/**
	 * Delete the content equals to String str from the File f. 
	 * @param f file
	 * @param str String
	 */
	public static void deleteFileContents(File f,String str) {
		try{
			ArrayList<String> content= new ArrayList<>();
			for(Scanner input= new Scanner(f);input.hasNext();) {
				content.add(input.nextLine());
			}
			for(int i=0;i<content.size();i++) {
				String fileStr = content.get(i);
				if (fileStr.equals(str)) {
					content.remove(i);
				}
			}
			PrintWriter pw = new PrintWriter(f);
			pw.close();
			saveFileContents( f,content);
		}catch(FileNotFoundException e) {
			e.printStackTrace();	
		}
	}
	
	/**
	 * Read and save the content from the File f to the ArrayList  ar, return ar.
	 * @param f file
	 * @return ArrayList  ar
	 */
	public static ArrayList<String> getFileContentAsArrayList(File f){
		ArrayList<String> ar = new ArrayList<>();
		try{							
			for(Scanner input= new Scanner(f);input.hasNext();) {
				ar.add(input.nextLine());
			}				
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();				
		}		
		return ar;
	}
	
	/**
	 * Check if the file f is empty.
	 * @param f file
	 * @return boolean 
	 */
	public static boolean fileIsEmpty(File f) {
		if(getFileContentAsArrayList(f).size()==0) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Check if the file f exists.
	 * @param f file
	 * @return boolean
	 */
	public static boolean fileExists(File f) {
		return f.exists();
	}
	
	
	
}
