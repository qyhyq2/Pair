package myPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MakePair {
	private static final int NUM_OF_EACH_GENDER = 100;
	static Person[] male = new Person[NUM_OF_EACH_GENDER];
	static Person[] female = new Person[NUM_OF_EACH_GENDER];
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		readfile("attachments\\male.txt",male);
		readfile("attachments\\female.txt",female);
		
		
		for(Person s :female){
			System.out.println(s);
		}
	}

	private static void readfile(String path,Person[] p) {
		FileReader fr = null ;
		BufferedReader br = null;
		try {
			fr = new FileReader(new File(path));
			br = new BufferedReader(fr);
			String line ;
			for(int i=0;i<NUM_OF_EACH_GENDER;i++){
				line = br.readLine();
				p[i] = createPerson(line);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	private static Person createPerson(String str){
		String[] val = str.split(",");
		return (new Person(Integer.parseInt(val[0]),Integer.parseInt(val[1]),
							Integer.parseInt(val[2]),Integer.parseInt(val[3]),
							Integer.parseInt(val[4]),Integer.parseInt(val[5]),
							Integer.parseInt(val[6])));
	}
	
	/**
	 * 
	 * @param targetPerson
	 * @param sourcePerson
	 * @return the score that the targetPerson to sourcePerson
	 */
	private static int calculate(Person targetPerson,Person sourcePerson){
		return (sourcePerson.getExpectLooks()*targetPerson.getLooks() 
				+ sourcePerson.getExpectCharacter()*targetPerson.getCharacter()
				+ sourcePerson.getExpectWealth()*targetPerson.getWealth());
	}
	
	
	private static void makePair(){
		//males vote one who has highest score in exists female(for:male(for:female))
		
		//sort the vote and find 
		
		//the female of most votes selects highest score one in the males who vote her 
		
		//return the pair
	}

}
