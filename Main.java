
//@author Basile Laforge
//Passwort zum einloggen ist 360Consulting.

package parkhausmanager;

public class Main {

	static Parkhaus venceParkhaus =Parkhaus.baueParkhaus();
	
	
		
	public static void main(String[] args) {
		
		
		String eingabe=venceParkhaus.willkommensBildschirm();
		
		venceParkhaus.verarbeitung(eingabe);
	
	
	
	
	}	
	
	
	
	
	
	
	
}
