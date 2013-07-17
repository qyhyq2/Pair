package myPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MakePair {
	private static final int NUM_OF_EACH_GENDER = 100;
	private static ArrayList<Person> male = new ArrayList<Person>(NUM_OF_EACH_GENDER);
	private static ArrayList<Person> female = new ArrayList<Person>(NUM_OF_EACH_GENDER);
	private static Person player;
	private static boolean IsPlayerMale; 
	static int[] voteBox = new int[101]; //to record the number of voter for each female
	
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		readFileToPerson("attachments\\male.txt",male);
		readFileToPerson("attachments\\female.txt",female);
		player = readFileToPlayer("attachments\\players.txt", 2);
		System.out.println(getResult());
//		System.out.println(player+"\t"+IsPlayerMale);
//		for(Person s :getOnePair()){
//			System.out.println(s);
//		}
	}
	
	/**
	 * add player to the matching and get the result
	 * @return Person ,the final result
	 */
	private static Person getResult(){
		if(IsPlayerMale){
			male.add(player);
		}
		else{
			female.add(player);
		}
		Person[] result = getOnePair();
		if(IsPlayerMale){
			while(result[0].getId()!= player.getId()){
				male.remove(result[0]);
				female.remove(result[1]);
				result = getOnePair();
			}
			return result[1];
		}
		else{
			while(result[1].getId()!= player.getId()){
				male.remove(result[0]);
				female.remove(result[1]);
				result = getOnePair();
			}
			return result[0];
		}
	}
	
	/**
	 * Read data from file of path,and set the data to player of specific number
	 * @param path
	 * @param p
	 */
	private static Person readFileToPlayer (String path,int number) {
		Person player = null;
		FileReader fr = null ;
		BufferedReader br = null;
		try {
			fr = new FileReader(new File(path));
			br = new BufferedReader(fr);
			String line ;
			String[] val;
			for(int i=0;i<NUM_OF_EACH_GENDER;i++){
				if(i == number-1){
					line =br.readLine();
					val = line.split(",");
					if(val[0].equals("0")){
						IsPlayerMale = false;
					}
					else if(val[0].equals("1")){
						IsPlayerMale = true;
					}
					player = new Person(-1,Integer.parseInt(val[1]),
							Integer.parseInt(val[2]),Integer.parseInt(val[3]),
							Integer.parseInt(val[4]),Integer.parseInt(val[5]),
							Integer.parseInt(val[6]));
				}
				else{
					br.readLine();
				}
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
		return player;
		
		
	}
	
	/**
	 * Read data from file which is in the path,and set the data to ArrayList<Person> p
	 * @param path
	 * @param p
	 */
	private static void readFileToPerson(String path,ArrayList<Person> p) {
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
	 * Create a Person by analyzing a String of a line
	 * @param str
	 * @return Person who set data from str
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
	 * @return Person[], index 0 is male,index 1 is female
	 */
	private static Person[] getOnePair(){
		//males vote one who has the highest score in exists females
		//meanwhile female can record the male who has the highest score among voters
		int voteeIndex = 0,score;
		int size = female.size();
		initializeVote(voteBox,0,size);
		for(Person voter:male){
			if(voter.getTarget()== null || (!female.contains(voter.getTarget()))){
				voteeIndex = getHighestScoreFemale(female,voter);
			}
			else{//if the highest score female is still existing,it's no need to calculate again
				voteeIndex = female.indexOf(voter.getTarget());
			}
			voteBox[voteeIndex]++;
			score = calculate(voter, female.get(voteeIndex));
			if(score>female.get(voteeIndex).getTargetScore()){
				female.get(voteeIndex).setTarget(voter);
				female.get(voteeIndex).setTargetScore(score);
			}
		}
		
		//find the female who is most popular
		int index = 0;
		for(int i=0;i<size;i++){
			if(compareVote(i,index,voteBox,female)>0){
				index = i;
			}
		}
		
		//return the pair
		return new Person[]{female.get(index).getTarget(),female.get(index)};
	}
	
	/**
	 * Compare two female's voteBox to find the winner according to the rule that 
	 * the one who has higher summary of attributions and smaller id will win
	 * @param index1
	 * @param index2
	 * @param voteBox
	 * @param female
	 * @return
	 */
	private static int compareVote(int index1,int index2,int[] voteBox,ArrayList<Person> female){
		Person f1 = female.get(index1);
		Person f2 = female.get(index2);
		if(voteBox[index1]>voteBox[index2]){
			return 1;
		}
		else if(voteBox[index1]<voteBox[index2]){
			return -1;
		}
		else if(f1.getLooks()+f1.getCharacter()+f1.getWealth()
				>f2.getLooks()+f2.getCharacter()+f2.getWealth()){
			return 1;
		}
		else if(f1.getLooks()+f1.getCharacter()+f1.getWealth()
				<f2.getLooks()+f2.getCharacter()+f2.getWealth()){
			return -1;
		}
		else if(f1.getId()<f2.getId()){
			return 1;
		}
		else{
			return -1;
		}
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
