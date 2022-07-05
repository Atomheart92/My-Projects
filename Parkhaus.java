
//@author Basile Laforge

package parkhausmanager;
import javax.swing.JOptionPane;

public class Parkhaus {
		private int parkEtagen;
		private int parkplaetzeProEtage;
		private Fahrzeug platzbesetzung [][];
	
	public Parkhaus(int parkEtagen, int parkplaetzeProEtage) {
		this.parkEtagen=parkEtagen;
		this.parkplaetzeProEtage=parkplaetzeProEtage;
		platzbesetzung=new Fahrzeug[parkEtagen][parkplaetzeProEtage];
		for (int i=0; i<parkEtagen;i++) {
			for (int j=0; j<parkplaetzeProEtage;j++) {
				
//leeres "Fahrzeug". Wird zur Anzeige eines freien Parkplatz verwendet. 	
				
				platzbesetzung[i][j]=new Fahrzeug("");
				
			}						
		}
	}
	
// Alle nötigen getter und setter
	
	public Fahrzeug[][] getPlatzbesetzung() {
	    Fahrzeug[][] p = platzbesetzung;
		return p;
	}

	public void setPlatzbesetzung(Fahrzeug[][] platzbesetzung) {
		this.platzbesetzung = platzbesetzung;
	}

	public int getParkEtagen() {
		return parkEtagen;
	}
	
	
	public int getParkplaetzeProEtage() {
		return parkplaetzeProEtage;
	}
		
// Hier wird das Parkhaus erstellt.
	
	public static Parkhaus baueParkhaus() {
	
		int parkEtagen=0;
		int parkplaetzeProEtage=0;
		boolean flag=true;
		while(flag) {
		try {
			parkEtagen=Integer.parseInt(JOptionPane.showInputDialog("Geben sie die Anzahl der Etagen ein:"));
			if (parkEtagen>0) {flag=false;}
			}catch(NumberFormatException e) {
				System.out.println("Falsche Eingabe. \nBitte geben Sie eine Zahl ein.");
			}
		}
		
		flag=true;
		while(flag) {
		try {
			parkplaetzeProEtage=Integer.parseInt(JOptionPane.showInputDialog("Geben sie die Anzahl der Parkplätze pro Etage ein:"));
			if (parkplaetzeProEtage>0) {flag=false;}
			}catch(NumberFormatException e) {
				System.out.println("Falsche Eingabe. \nBitte geben Sie eine Zahl ein.");
			}
		}		 
		return new Parkhaus(parkEtagen,parkplaetzeProEtage);				
	}
	
//willkommensbildschirm beim betreten oder verlassen des Parkhauses, wo ich die Optionen auswählen kann
	
	String willkommensBildschirm() {
	
		boolean flag=true;	
		String eingabe="";
		
		while (flag) {	
			eingabe=
					JOptionPane.showInputDialog(	"Willkommen Im Parkhaus der Stadt Vence.\n"+
													"Bitte wählen sie eine Option indem sie die Zahl eintippen.\n"+
													"1 -> Fahrzeug parken\n"+
													"2 -> Parkhaus verlassen\n"+
													"3 -> Einloggen (nur für Mitarbeiter)");
				if (eingabe==null) continue;
				if(eingabe.equals("1")||eingabe.equals("2")||eingabe.equals("3"))
					flag=false;
				else
					System.out.println("Falsche Eingabe. Bitte wählen sie eine der gültigen Optionen");
				}
		return eingabe;		
		}
		
//hier wird die eingabe verarbeitet und die gewünschte Methode aufgerufen	
	
	void verarbeitung(String eingabe) {
		switch(eingabe) {
		case "1":
			parken();
			break;
		case "2":
			austragen();
			break;
		case "3":
			einloggen();
			break;
		}		
	}	
	
//hier wird die prüfung des Nummernschild und das parken ausgeführt.
	
	void parken() {
		String nummernschild="";
		boolean flag =true;
		while(flag) {
								
		nummernschild=
		JOptionPane.showInputDialog("Sie haben Fahrzeug parken ausgewählt.\n"+
									"Bitte geben sie ihr Nummernschild ein:");
			if (nummernschild==null)verarbeitung(willkommensBildschirm());
			if (nummernschild.equals("")) {
				System.out.println("Falsche Eingabe.");
				continue;
				}
			if(Fahrzeug.check(nummernschild)) {
				System.out.println("test");
				parkplatzAuswahl(nummernschild);
				flag=false;
				}
			else
				doppeltesNummernschild(nummernschild);
				}	
		}	

//Hier wird der erste verfügbare Parkplatz ausgewählt, eingetragen und dem Kunden übermittelt
//oder bei voller Auslastung der Kunde abgewiesen.
	
	void parkplatzAuswahl(String nummernschild) {
				
		boolean flag=true;
		point:
		for (int i=0;i<Main.venceParkhaus.getPlatzbesetzung().length;i++){
			for (int j=0;j<Main.venceParkhaus.getPlatzbesetzung()[i].length;j++) {
				if (Main.venceParkhaus.getPlatzbesetzung()[i][j].getNummernschild()=="") {

//eintragung des Fahrzeugs auf der ersten freien stelle
					platzbesetzung=Main.venceParkhaus.getPlatzbesetzung();
					platzbesetzung[i][j]=new Fahrzeug(nummernschild);
					Main.venceParkhaus.setPlatzbesetzung(platzbesetzung);
					
//Übergabe des Parkplatz+Etage an den Kunden
					
					System.out.println("Ihr Zugewiesener Parkplatz ist die Nummer:\n"+(j+1)+"\nauf der\n"+(i+1) +".) Etage ");
					
					flag=false;
					break point;
					}			
				}
			}
		if (flag)
			System.out.println("Leider ist der Parkplatz aktuell voll. \nKommen sie Bitte zu einem anderen Zeitpunt.");
		verarbeitung(willkommensBildschirm());
		
	}
	
//Hier wird der Fall des doppelten Nummernschildes bearbeitet.
	
	void doppeltesNummernschild(String nummernschild) {
		String eingabe=JOptionPane.showInputDialog(	"Das Auto mit dem Nummernschild:\n"+nummernschild+"\nexistiert bereits. \n"
		 										+	"Für eine erneute Eingabe drücken sie die -> 1\n"
		 										+ 	"Falls ein Fehler unsererseits vorliegt kontaktieren sie einen Mitarbeiter.\n"
		 										+ 	"Drücken sie eine beliebige andere Taste um zum Startbildschirm zurück zu kommen.");
	
		if (eingabe==null)verarbeitung(willkommensBildschirm());
		if (eingabe.equals("1"))
			parken();
		else
			verarbeitung(willkommensBildschirm());
	}
	
//hier werden die Fahrzeuge ausgetragen wenn sie das Parkhaus verlassen wollen.
	
	void austragen() {
		
		String nummernschild="";
		String erfolg;
				
		while(true) {			
		Fahrzeug [][] platzBesetzung=Main.venceParkhaus.getPlatzbesetzung();						
		nummernschild=
		JOptionPane.showInputDialog("Sie haben Parkhaus verlassen ausgewählt.\n"
			+ 						"Bitte geben sie das Nummernschild ihres Fahrzeugs ein:"	);		
		if (nummernschild==null)verarbeitung(willkommensBildschirm());		
		erfolg="0";			
		point:
			for (int i=0;i<platzBesetzung.length;i++){
				for (int j=0;j<platzBesetzung[i].length;j++) {
					if (platzBesetzung[i][j].getNummernschild().equals(nummernschild)) {
						System.out.println("Vielen Dank für den Besuch.\n");
						System.out.println("Wir wünschen Ihnen eine sichere Fahrt und auf Wiedersehen.");
						platzbesetzung[i][j]=new Fahrzeug("");
						Main.venceParkhaus.setPlatzbesetzung(platzBesetzung);
						erfolg="1";
						break point;
					}
				}	
			}
		
		if (erfolg.equals("1")) {
			verarbeitung(willkommensBildschirm());
			break;
			}
		
		if (erfolg.equals("0")) {
			erfolg=JOptionPane.showInputDialog(	"Leider haben wir das Fahrzeug mit dem Nummernschild:\n"+nummernschild+"\nnicht in unserem Parkhaus.\n"
					+ 							"Um das Nummernschild neu einzugeben drücken sie bitte die -> 1 \n"
					+ 							"Um zum Startbildschirm zurückzukehren drücken sie eine andere beliebige Taste.");
		}
		if (erfolg==null)verarbeitung(willkommensBildschirm());
		if (erfolg.equals("1"))
			continue;
		else {
			verarbeitung(willkommensBildschirm());
			break;
			}
		}
	 
	}	

//hier können sich Mitarbeiter einloggen um auf die erweiterten Optionen zugreifen zu können	
//Passwort ist 360Consulting	
	private void einloggen() {
		
		String eingabe=
		JOptionPane.showInputDialog("Passwort:");
		
//Abfrage und überprüfung eines Passwortes um unerlaubten Zugriff zu verhindern.
		
		if (eingabe==null)verarbeitung(willkommensBildschirm());
		if (eingabe.equals("360Consulting")) {
			
			eingabe = JOptionPane.showInputDialog( 	"Für eine Nummernschildabfrage drücken sie bitte die -> 1\n"
					+								"Für die Anzahl der noch freien Plätze drücken sie die -> 2\n"
					+ 								"Für den Willkommensbildschirm drücken sie eine andere beliebige Taste");
		if (eingabe==null)verarbeitung(willkommensBildschirm());	
//Nach erfolgreicher Verifizierung findet sich hier das Menu der erweiterten Optionen	
			
			if (eingabe.equals("1"))
					nummernschildAbfrage();
			if (eingabe.equals("2"))
					freiePlaetze();										
		}			
			verarbeitung(willkommensBildschirm());
	}	
	
//hier findet die Nummernschildabfrage statt	
	
	private void nummernschildAbfrage() {
		Fahrzeug [][] platzBesetzung=Main.venceParkhaus.getPlatzbesetzung();
		String nummernschild=
				JOptionPane.showInputDialog("Bitte geben sie das Nummernschild des gesuchten Fahrzeugs ein.");
		if (nummernschild==null)verarbeitung(willkommensBildschirm());
		if (nummernschild.equals("")) {
			System.out.println("Ungültige Suche. Es gibt kein leeres Nummernschild");
			verarbeitung(willkommensBildschirm());
		}

//hier überprüfe ich alle Fahrzeuge im Parkhaus mit dem abgefragtne Nummernschild.		
		boolean flag=true;
		point:
		for (int i=0;i<platzBesetzung.length;i++){
			for (int j=0;j<platzBesetzung[i].length;j++) {
				
				if (platzBesetzung[i][j].getNummernschild().equals(nummernschild)) {
					System.out.println("Das Fahrzeug mit dem Nummernschild:\n"+nummernschild+"\nbefindet sich auf dem Parkplatz "+(j+1)+" auf der "+(i+1)+".) Etage ");
					flag=false;
					break point;
				}
			}
		}
		if (flag) System.out.println("Das Fahrzeug mit dem Nummernschild:\n"+nummernschild+"\nbefindet sich nicht auf diesen Parkplatz.");
		verarbeitung(willkommensBildschirm());
	}
	
//hier wird ausgerrechnet wiviele freie Plätze noch übrig sind indem ich ein counter hochzählen lasse	
	private void freiePlaetze(){
		
		Fahrzeug [][] platzBesetzung=Main.venceParkhaus.getPlatzbesetzung();
		int counter = 0;
		for (int i=0;i<platzBesetzung.length;i++){
			for (int j=0;j<platzBesetzung[i].length;j++) {
				if (platzBesetzung[i][j].getNummernschild().equals("")) {
					counter++;
					}
				}
			}
		System.out.println("Es sind noch: "+counter+" Plätze im Parkhaus der Stadt Vence frei.");
		verarbeitung(willkommensBildschirm());
	}
	
}	
	
	

