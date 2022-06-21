package wordlegruppe.wordle.game;

import wordlegruppe.wordle.App;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Oliver Schneider
 */
public class WordList {

    // Name und Pfad der Datei 
    private final URL enDict;
    private List<String> wordlist;

    public WordList() {
        enDict = App.class.getResource("en_dict.txt");
    }

    private List<String> readDict() {

        try {
            assert enDict != null;
            // alle bytes der datei lesen
            byte[] data = Files.readAllBytes(Path.of(enDict.toURI()));
            // die bytes als String interpretieren und nach Zeilenumbrüchen aufteilen
            // datei ist mit CRLF Zeilenumbrüchen gespeichert, deshalb \r\n
            String[] contents = new String(data, StandardCharsets.UTF_8).split("\r\n");

            return Arrays.asList(contents);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String randomWord() {
        String random_word;
        if(wordlist == null) {
            wordlist = readDict();
        }
        //Gib eine Zahl zwischen 0 und der maximalen Anzahl an Zeilen
        int random_number = (int) Math.round(Math.random() * wordlist.size());

        //Wählt ein zufälliges Wort
        random_word = wordlist.get(random_number);

        return random_word;
    }
    
    public boolean contains(String inputWord)
    {
        boolean inList = false;
        for (String s : getWordlist()) {
            // Check, ob das Wort in der List ist; beide werden klein gemacht, um sie besser zu vergleichen
            if (s.equalsIgnoreCase(inputWord)) {
                inList = true;
                break;
            }
        }
        
        return inList;
    }

    public List<String> getWordlist() {
        if(wordlist == null)
            wordlist = readDict();
        return wordlist;
    }
}
