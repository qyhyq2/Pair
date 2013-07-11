package myPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MakePair {
	private static final int NUM_OF_EACH_GENDER = 100;
	static ArrayList<Person> male = new ArrayList<Person>(NUM_OF_EACH_GENDER);
	static ArrayList<Person> female = new ArrayList<Person>(NUM_OF_EACH_GENDER);
	static int[] voteBox = new int[100]; //to record the number of voter for each female
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		readfile("attachments\\male.txt",male);
		readfile("attachments\\female.txt",female);
		
		
		for(Person s :getOnePair()){
			System.out.println(s);
		}
	}
	
	/**
	 * Read data from file which is in the path,and set the data to ArrayList<Person> p
	 * @param path
	 * @param p
	 */
	private static void readfile(String path,ArrayList<Person> p) {
		FileReader fr = null ;
		BufferedReader br = null;
		try {
			fr = new FileReader(new File(path));
			br = new BufferedReader(fr);
			String line ;
			for(int i=0;i<NUM_OF_EACH_GENDER;i++){
				line = br.readLine();
				p.add(createPerson(line));
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
	
	/**
	 * Create a Person by analyzing one line String
	 * @param str
	 * @return Person set data from str
	 */
	private static Person createPerson(String str){
		String[] val = str.split(",");
		return (new Person(Integer.parseInt(val[0]),Integer.parseInt(val[1]),
							Integer.parseInt(val[2]),Integer.parseInt(val[3]),
							Integer.parseInt(val[4]),Integer.parseInt(val[5]),
							Integer.parseInt(val[6])));
	}
	
	/**
	 * Calculate the the score of targetPerson to sourcePerson,
	 * adding NUM_OF_EACH_GENDER-targetPerson.getId() to guaranteer that the play 
	 * who has smaller id is prior to bigger one while they have same score
	 * @param targetPerson
	 * @param sourcePerson
	 * @return the score of targetPerson to sourcePerson
	 */
	private static int calculate(Person targetPerson,Person sourcePerson){
		return (sourcePerson.getExpectLooks()*targetPerson.getLooks() 
				+ sourcePerson.getExpectCharacter()*targetPerson.getCharacter()
				+ sourcePerson.getExpectWealth()*targetPerson.getWealth()
				+(NUM_OF_EACH_GENDER-targetPerson.getId()));
	}
	
	/**
	 * Get one pair according to the specific rule
	 * @return
	 */
	private static Person[] getOnePair(){
		//males vote one who has the highest score in exists females
		//meanwhile female can record the male who has the highest score from voters
		int voteeIndex,score;
		int size = female.size();
		initializeVote(voteBox,0,size);
		for(Person voter:male){
			voteeIndex = getHighestScoreFemale(female,voter);
			voteBox[voteeIndex]++;
			score = calculate(voter, female.get(voteeIndex));
			if(score>female.get(voteeIndex).getTargetScore()){
				female.get(voteeIndex).setTarget(voter);
				female.get(voteeIndex).setTargetScore(score);
			}
		}
		
		//find the female who gets most votes
		int max = 0,index = 0;
		for(int i=0;i<size;i++){
			if(voteBox[i]>max){
				max = voteBox[i];
				index = i;
			}
		}
		
		//return the pair
		return new Person[]{female.get(index).getTarget(),female.get(index)};
	}
	
	/**
	 * For specific male,get a female who is mostly fitted his expectation in remaining females 
	 * @param remainingFemale
	 * @param male
	 * @return the index of female in ArrayList
	 */
	private static int getHighestScoreFemale(ArrayList<Person> remainingFemale,Person male){
		int maxScore = 0,score;
		int targetIndex = -1;
		for(int i=0;i<remainingFemale.size();i++){
			score = calculate(female.get(i),male);
			if(score>maxScore){
				targetIndex = i;
				maxScore = score;
			}			
		}
		return targetIndex;
	}
	
	/**
	 * Use  value to initialize array a whose length is size 
	 * @param a
	 * @param value
	 * @param size
	 */
	private static void initializeVote(int[] a,int value,int size){
		for(int i=0;i<size;i++){
			a[i] = value;
		}
	}
}
