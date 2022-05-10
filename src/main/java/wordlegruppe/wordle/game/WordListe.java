package wordlegruppe.wordle.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;

/**
 *
 * @author Oliver Schneider
 */
public class WordListe {

    // Name und Pfad der Datei 
    private String fileName = "src\\main\\java\\wordlegruppe\\wordle\\game\\en_dict.txt";

    // Erstellt ein Array fuer die Woerter
    private String[] liste = new String[count_lines()];

    public String[] open_file() {

        try {
            // FileReader liest die Text-Datei, default: encoding.
            FileReader fileReader = new FileReader(fileName);
            // FileReader in BufferedReader setzen 
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            int i = 0;

            while ((line = bufferedReader.readLine()) != null) {
                liste[i] = line;
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
    
    private int count_lines() {

        int lines = 0;

        try ( BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public String random_word() {
        String random_word;
        open_file();
        //Gib eine Zahl zwischen 0 und der Maximalen Anzahl an Zeilen
        int random_number = (int) Math.round(Math.random() * count_lines());

        //Waehlt ein Zufaelliges Wort 
        random_word = liste[random_number];

        return random_word;
    }
    
    public boolean check_for_word(String inputWord)
    {
        open_file();
        boolean inList = false;
        
        for(int i = 0; i < count_lines(); i++)
        {
            // Check ob das Wort in der List ist; Beide werden klein gemacht um sie besser zu vergleichen
            if(liste[i].toLowerCase().equals(inputWord.toLowerCase())) inList = true;                     
        }
        
        return inList;
    }
}
