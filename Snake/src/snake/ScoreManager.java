/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;
import java.io.*;
import java.util.*;

/**
 *
 * @author Jonathan Moreno
 */

public class ScoreManager {
    private final File dir  = new File(System.getProperty("user.home"), ".snake-scores");
    private final File file = new File(dir, "highscores.txt");

    public static class Entry implements Comparable<Entry> {
        public final String name; public final int score;
        public Entry(String name, int score){ this.name=name; this.score=score; }
        @Override public int compareTo(Entry o){ return Integer.compare(o.score, this.score); }
        @Override public String toString(){ return name + "," + score; }
    }

    public List<Entry> load() {
        List<Entry> list = new LinkedList<>();
        if (!file.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine())!=null){
                String[] parts = line.split(",", 2);
                if (parts.length==2) {
                    try { list.add(new Entry(parts[0].trim(), Integer.parseInt(parts[1].trim()))); }
                    catch(NumberFormatException ignored){}
                }
            }
        } catch(IOException ignored){}
        Collections.sort(list);
        return list;
    }

    public void saveScore(String name, int score) throws IOException {
        List<Entry> list = load();
        list.add(new Entry(name==null||name.isBlank()? "Player":name, score));
        Collections.sort(list);
        if (list.size()>10) list = new LinkedList<>(list.subList(0,10));
        if (!dir.exists()) dir.mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Entry e: list) { bw.write(e.toString()); bw.newLine(); }
        }
    }
}