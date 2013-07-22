package myPackage;
/**
 * @class Person
 * @description Each person has his id,looks,character,wealth,exceptLooks,
 * 				expectCharacter,expectWealth.etc
 * @author qyh
 *
*/
public class Person {
	private int id;
	private int looks;
	private int character;
	private int wealth;
	private int expectLooks;
	private int expectCharacter;
	private int expectWealth;
	private Person target = null;
	private int targetScore = 0;
	
	/**
	 * Constructor
	 * @param id
	 * @param looks
	 * @param character
	 * @param wealth
	 * @param expectLooks
	 * @param expectCharacter
	 * @param expectWealth
	 */
	public Person(int id,int looks,int character,int wealth,
			int expectLooks,int expectCharacter,int expectWealth){
		this.id = id;
		this.looks = looks;
		this.wealth = wealth;
		this.character = character;
		this.expectLooks = expectLooks;
		this.expectWealth = expectWealth;
		this.expectCharacter = expectCharacter;
		
	}

	public int getId() {
		return id;
	}

	public int getLooks() {
		return looks;
	}

	public int getWealth() {
		return wealth;
	}

	public int getCharacter() {
		return character;
	}

	public int getExpectLooks() {
		return expectLooks;
	}

	public int getExpectWealth() {
		return expectWealth;
	}

	public int getExpectCharacter() {
		return expectCharacter;
	}

	public Person getTarget() {
		return target;
	}

	public void setTarget(Person target) {
		this.target = target;
	}

	public int getTargetScore() {
		return targetScore;
	}

	public void setTargetScore(int targetScore) {
		this.targetScore = targetScore;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id +","+this.looks+","+this.character+","
				+this.wealth+","+this.expectLooks+","
				+this.expectCharacter+","+this.expectWealth;
	}
	
	
	
	
}
