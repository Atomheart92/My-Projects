
//@author Basile Laforge

package parkhausmanager;

public class Fahrzeug {
	
	private String nummernschild;
	

	public Fahrzeug(String nummernschild) {
		this.nummernschild=nummernschild;
	}
	
	public String getNummernschild() {
		return nummernschild;
	}
	
	
	public static boolean check(String nummernschild) {
		boolean nichtErstellt=true;
		Fahrzeug [][] platzbesetzung=Main.venceParkhaus.getPlatzbesetzung();
		flag:
		for (int i=0;i<platzbesetzung.length;i++){
			for (int j=0;j<platzbesetzung[i].length;j++) {
				if (platzbesetzung[i][j].nummernschild.equals(nummernschild)) {
					
					nichtErstellt=false;
					break flag;
					}
				}
			}
		return nichtErstellt;
	}
	
	
	
	
}
