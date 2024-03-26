package Logic;

import Graphics.GameFrame;
import Graphics.Settings;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MyProject {
    public static int record;
    public static ArrayList<Map<String, String>> saved;
    private static File file;
    public MyProject() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Settings.saveBoolean = true;
        Settings.aimingBoolean = true;
        saved = new ArrayList<>();
        load();
        new GameFrame();
    }
    public static void save() throws IOException {
        file = new File("saved_games");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.append(record+"\n");
        for (int i = 0; i < saved.size(); i++) {
            fileWriter.append(saved.get(i).get("date") + "\n");
            fileWriter.append(saved.get(i).get("name") + "\n");
            fileWriter.append(saved.get(i).get("time") + "\n");
            fileWriter.append(saved.get(i).get("score") + "\n");
        }
        fileWriter.close();
    }
    private void load() throws IOException {
        file = new File("saved_games");
        if (!file.exists()) {
            file.createNewFile();
        }
        Scanner sc = new Scanner(file);
        int x = 0;
        Map<String, String> map = new HashMap<>();
        while (sc.hasNext()) {
            if (x == 0) {
                record = Integer.parseInt(sc.nextLine());
            }
            else if (x % 4 == 1) {
                map = new HashMap<>();
                map.put("date", sc.nextLine());
            }
            else if (x % 4 == 2) {
                map.put("name", sc.nextLine());
            }
            else if (x % 4 == 3) {
                map.put("time", sc.nextLine());
            }
            else {
                map.put("score", sc.nextLine());
                saved.add(map);
            }
            x++;
        }
    }

}