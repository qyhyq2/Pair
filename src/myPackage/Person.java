package myPackage;
/**
 * @class Person
 * @description 
 * @author qyh
 *
*/
public class Person {
	private int id;
	private int looks;
	private int wealth;
	private int character;
	private int expectLooks;
	private int expectCharacter;
	private int expectWealth;
	
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id +","+this.looks+","+this.character+","
				+this.wealth+","+this.expectLooks+","
				+this.expectCharacter+","+this.expectWealth;
	}
	
	
	
	
}
