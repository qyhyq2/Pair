package myPackage;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test of MakePair using data from specific file
 * the player is gained from the line 1 of file player.txt
 * @author qyh
 *
 */
public class MakePairTest {
	final int LINE_NUMBER = 1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MakePair.usingFileData(LINE_NUMBER);
	}
	
	/**
	 * Test the method getOnePair():
	 *  Get one matching pair according to the specific rule
	 *  the method return Person[], index 0 is male,index 1 is female
	 */
	@Test
	public void testGetOnePair(){
		Person[] pair = MakePair.getOnePair();
		assertTrue("12,85,76,97,32,34,34".equals(pair[0].toString()));
		assertTrue("7,85,98,90,72,17,11".equals(pair[1].toString()));
		
	}
	
	/**
	 * Test the method compareVote():
	 * Compare two female's voteBox to find the winner according to the rule that 
	 * the one who has higher summary of attributions and smaller id will win when 
	 * having the same votes
	 */
	@Test
	public void testCompVote(){
		int voteBox[] = new int[100];
		Person p0 = new Person(1,98,98,98,10,20,70);
		Person p1 = new Person(2,12,12,12,10,20,70);
		Person p2 = new Person(3,98,90,90,10,20,70);
		Person p3 = new Person(4,98,98,98,10,20,70);
		ArrayList<Person> female = new ArrayList<Person>();
		female.add(p0);
		female.add(p1);
		female.add(p2);
		female.add(p3);
		voteBox[0] = 10;
		voteBox[1] = 12;
		voteBox[2] = 10;
		voteBox[3] = 10;
		assertTrue(MakePair.compareVote(0, 1, voteBox,female)==-1);
		assertTrue(MakePair.compareVote(0, 2, voteBox,female)==1);
		assertTrue(MakePair.compareVote(0, 3, voteBox,female)==1);		
	}
	
	/**
	 * Test the final matcher
	 */
	@Test
	public void testMatcher(){
		String matcherAttr = MakePair.getMatcher().toString();
		int index = matcherAttr.indexOf(",");
		assertTrue("6,18,82,87,3,10".equals(matcherAttr.substring(index+1)));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		MakePair.clearData();
	}

}
