package myPackage;

public class main {
	public static void main(String[] args) {
		Interface i = new Interface();
		WindowDestroyer myListener = new WindowDestroyer();
		i.addWindowListener(myListener);
		i.show();
	}
}
