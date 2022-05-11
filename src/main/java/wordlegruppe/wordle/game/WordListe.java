package wordlegruppe.wordle.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Oliver Schneider
 */
public class WordListe {

    // Name und Pfad der Datei 
    private String fileName = "src\\main\\resources\\textfile\\en_dict.txt";

    // Erstellt ein ArrayList fuer die Woerter
    private ArrayList<String> liste = new ArrayList<>();

    public ArrayList<String> openFile() {

        try {
            // FileReader liest die Text-Datei, default: encoding.
            FileReader fileReader = new FileReader(fileName);
            // FileReader in BufferedReader setzen 
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            int i = 0;

            while ((line = bufferedReader.readLine()) != null) {
                liste.add(line);
                i++;
            }

            //Datei schliessen 
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
        }
        
        return liste;
    }
    
    public String randomWord() {
        String random_word;
        if(liste.isEmpty())openFile();    
        //Gib eine Zahl zwischen 0 und der Maximalen Anzahl an Zeilen
        int random_number = (int) Math.round(Math.random() * liste.size());

        //Waehlt ein Zufaelliges Wort 
        random_word = liste.get(random_number);

        return random_word;
    }
    
    public boolean checkForWord(String inputWord)
    {
        if(liste.isEmpty())openFile();
        boolean inList = false;
        
        for(int i = 0; i < liste.size(); i++)
        {
            // Check ob das Wort in der List ist; Beide werden klein gemacht um sie besser zu vergleichen
            if(liste.get(i).toLowerCase().equals(inputWord.toLowerCase())) inList = true;                     
        }
        
        return inList;
    }
}
