package model;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    private static final String HISTORY_FILE =
            "history.dat";

    private List<HistoryEntry> history =
            new ArrayList<>();

    public HistoryManager() {
        load();
    }

    public void addEntry(HistoryEntry entry) {
        history.add(entry);
        save();
    }

    public List<HistoryEntry> getHistory() {
        return history;
    }

    public String getHistoryPath() {

        return Paths.get(HISTORY_FILE)
                .toAbsolutePath()
                .toString();
    }

    private void save() {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(HISTORY_FILE))) {

            output.writeObject(history);

        } catch (IOException e) {

            System.out.println(
                    "Ошибка сохранения истории");
        }
    }

    @SuppressWarnings("unchecked")
    private void load() {

        File file = new File(HISTORY_FILE);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(file))) {

            history =
                    (List<HistoryEntry>) input.readObject();

        } catch (Exception e) {

            history = new ArrayList<>();
        }
    }
}
