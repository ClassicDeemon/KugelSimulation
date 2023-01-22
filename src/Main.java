import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //Deklaration aller benoetigten Variablen
    private int counter = 0;
    private double durchmesserTank;
    private double gesamthoeheTank;
    private double fuellhoeheTank;
    private double dichteFluessigkeit;
    private double dichteKugel;
    private double durchmesserKugel;
    private double fuellstand;
    ArrayList<Double> durchmesserKugeln = new ArrayList<>();
    private double fuellstandTank;
    private double gesamtVolumenTank;
    //Die Main Methode, um das Programm zu starten und es wird die Startmethode aufgerufen
    public static void main(String[] args) {
        new Main().initializeProgram();
    }
    //Rahmenbedingungen des Gefaeßes werden hier deklariert
    private void initializeProgram() {
            //Der Scanner initialisieren, um Eingaben zu lesen
            Scanner scanner = new Scanner(System.in);
            //System.out: Ausgaben auf Konsole
            System.out.println("Simulation der Kugeln! Im Wasser befinden sich " + counter + " Kugeln.");
            System.out.println("Gib folgende Werte fuer den Tank ein ein!");
            try {
                System.out.println("Durchmesser des Tanks (in cm): ");
                durchmesserTank = Double.parseDouble(scanner.nextLine());
                System.out.println("Gesamthoehe des Tanks (in cm): ");
                gesamthoeheTank = Double.parseDouble(scanner.nextLine());
                System.out.println("Fuellhoehe des Tanks vor Einbringen der Kugeln (in cm)");
                fuellhoeheTank = Double.parseDouble(scanner.nextLine());
                System.out.println("Dichte der Fluessigkeit (Werte müssen zwischen 1000 und 1300 liegen): ");
                //Eingabeüberprüfung, ob Füllhöhe kleiner als Tankhöhe ist
                if(gesamthoeheTank <= fuellhoeheTank) {
                    fehler(0);
                }
                //Volumenberechnung der eingefuellten Fluessigkeit
                dichteFluessigkeit = Double.parseDouble(scanner.nextLine());
                if (dichteFluessigkeit < 1000 || dichteFluessigkeit > 1300) {
                    fehler(1);
                }
            } catch (Exception e) {
                fehler(0);
            }
            fuellstandTank = Math.PI * (((durchmesserTank / 2) * (durchmesserTank / 2)) * fuellhoeheTank);
            //Volumenberechnung des gesamten Gefaeßes
            gesamtVolumenTank = Math.PI * (((durchmesserTank / 2) * (durchmesserTank / 2)) * gesamthoeheTank);
            //Aufruf der naechsten Methode fuer Fortlauf des Programmes
            kugelManagement();
    }
    private void kugelManagement() {
            //Abfrage, ob eine neue Kugel hinzugefuegt werden soll
            Scanner scanner = new Scanner(System.in);
            System.out.println("Soll nun eine Kugel hinzugefuegt werden? (ja/nein)");
            //ueberpruefen, ob Nutzereingabe "ja" ist
            String userEingabe = scanner.nextLine();
            if (userEingabe.equalsIgnoreCase("ja")) {
                //wenn ja, Kugel hinzufuegen
                //Eigenschaften der Kugel festlegen
                System.out.println("Eigenschaften der Kugel werden jetzt eingegeben...");
                try {
                    System.out.println("Dichte der Kugel: ");
                    dichteKugel = Double.parseDouble(scanner.nextLine());
                    System.out.println("Durchmesser der Kugel: ");
                    durchmesserKugel = Double.parseDouble(scanner.nextLine());
                } catch (Exception e) {
                    fehler(2);
                }
                //Eingabe ueberpruefen, ob die Kugel in das Gefaeß passt
                if (durchmesserKugel > durchmesserTank) {
                    System.out.println("Die Kugel passt nicht in das Gefaeß.");
                    kugelManagement();
                }
                //ueberpruefen, ob die Kugel schwimmt
                if (schwimmtKugel()) {
                    //wenn ja, den Durchmesser Addieren

                    //Hilfsarray, in welchem alle Durchmesser gespeichert werden, um sie spaeter fuer die Summe genutzt werden
                    //jeder Durchmesser wird hinzugefuegt
                    durchmesserKugeln.add(durchmesserKugel);

                    //Zusammenrechnen aller Durchmesser
                    double summeDurchmesser = 0;
                    for (Double aDouble : durchmesserKugeln) {
                        summeDurchmesser = summeDurchmesser + aDouble;
                    }
                    System.out.println("Die Summe aller Durchmesser der schwimmenden Kugeln betraegt " + summeDurchmesser + " cm.");

                    //ueberpruefen, ob die Durchmesser der schwimmenden Kugeln in das Gefaeß passen
                    if (summeDurchmesser > durchmesserTank) {
                        System.out.println("Der Behaelter ist voll. Es werden keine weitere Kugeln mehr hinzugefuegt.");
                        System.out.println("Es befinden sich " + counter + " Kugeln im Gefaeß.");
                    } else {
                        System.out.println("Kugel wurde hinzugefuegt.");
                        fluessigkeitsVolumen();
                    }
                    //wenn nein, dann einfach ueberspringen und zum naechsten Schritt
                } else {
                    System.out.println("Die Kugel schwimmt nicht.");
                    fluessigkeitsVolumen();
                }

            } else if (!(userEingabe.equalsIgnoreCase("nein"))) {
                fehler(2);
            } else if (userEingabe.equalsIgnoreCase("nein")) {
                //wenn keine Kugel hinzugefuegt werden soll
                //Programm beenden.
                System.out.println("Programm wird beendet.");
            }
    }

    private void fluessigkeitsVolumen() {

            System.out.println("Die Kugel hat Wasser verdraengt... Das urspruengliche Fluessigkeitsvolumen lag bei " +
                    Math.round(fuellstandTank) + " Kubikzentimeter.");
            //5 Sekunden Pause eingebaut, um alles in Ruhe zu lesen
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //das Volumen des verdraengten Wassers berechnen
            fuellstand = Math.PI * (durchmesserKugel / 2) * (durchmesserKugel / 2) * fuellhoeheTank;
            System.out.println("Es wurden " + Math.round(fuellstand) + " Kubikzentimeter an Wasser verdraengt.");
            //Das verdraengte Wasser dem vorherigen Fuellstand hinzufuegen
            double fuellstandGesamt = fuellstand + fuellstandTank;

            //ueberpruefen, ob das Gefaeß ueberlaeuft
            if (fuellstandGesamt > gesamtVolumenTank) {
                //Wenn ja, Meldung ausgeben und Programm beenden
                System.out.println("Damit laeuft das Wasser ueber und der Behaelter ist voll.");
            } else {
                //Wenn nicht, Werte ausgeben und neue Kugel anbieten
                System.out.println("Der neue Fluessigkeitsvolumen betraegt " + Math.round(fuellstandGesamt));
                System.out.println("Es kann noch " + (Math.round(gesamtVolumenTank - fuellstandGesamt)) + " an Volumen hinzugefügt werden.");
                fuellstandTank = fuellstandGesamt;
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                kugelManagement();
            }

    }
    //Methode zum ueberpruefen, ob Kugel schwimmt oder nicht
    //true wird zurueckgegeben, wenn Kugel schwimmt
    //false, wenn sie untergeht
    public boolean schwimmtKugel() {
        try {
            boolean check;
            if (dichteKugel < dichteFluessigkeit) {
                System.out.println("Kugel schwimmt.");
                check = true;
            } else {
                System.out.println("Kugel schwimmt nicht.");
                check = false;
                fluessigkeitsVolumen();
            }
            return check;
        } catch (Exception e) {
            fehler(99);
            return false;
        }
    }

    //Fehlermanagement-Methode
    public void fehler(int i) {
        switch (i) {
            //Es werden hier verschiedene Fälle abgedeckt, um den User eine möglichst genaue Fehlermeldung zu bieten
            case 0:
                System.out.println("Eingabefehler! Schritt wird wiederholt.");
                initializeProgram();
                break;
            case 1:
                System.out.println("Dichte Wert muss zwischen 1000 und 1300 liegen. Eingabe muss wiederholt werden.");
                initializeProgram();
                break;
            case 2:
                System.out.println("Falsche Eingabe! Schritt wird wiederholt.");
                kugelManagement();
                break;
            case 99:
                System.out.println("Fehler! Programm startet neu...");
                initializeProgram();
                break;
        }
    }
}