package controller;

import model.CalculatorModel;
import model.HistoryEntry;
import model.HistoryManager;
import view.ConsoleView;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CalculatorController {

    private final CalculatorModel model;
    private final ConsoleView view;
    private final HistoryManager history;

    public CalculatorController(
            CalculatorModel model,
            ConsoleView view,
            HistoryManager history) {

        this.model = model;
        this.view = view;
        this.history = history;
    }

    public void run() {

        while (true) {

            view.showMenu();

            String choice =
                    view.readLine(
                            "Выберите пункт: ");

            switch (choice) {

                case "1":
                    calculate();
                    break;

                case "2":
                    showHistory();
                    break;

                case "3":
                    exportAll();
                    break;

                case "4":
                    exportSelected();
                    break;

                case "5":
                    view.print("Завершение...");
                    return;

                default:
                    view.print("Неверная команда");
            }
        }
    }

    private void calculate() {

        try {

            String expression =
                    view.readLine(
                            "Введите выражение: ");

            double result =
                    model.calculate(expression);

            HistoryEntry entry =
                    new HistoryEntry(
                            expression,
                            result);

            history.addEntry(entry);

            view.print(
                    "Результат = " + result);

        } catch (Exception e) {

            view.print(
                    "Ошибка вычисления");
        }
    }

    private void showHistory() {

        List<HistoryEntry> list =
                history.getHistory();

        if (list.isEmpty()) {

            view.print("История пуста");
            return;
        }

        for (int i = 0; i < list.size(); i++) {

            view.print(
                    i + ". " + list.get(i));
        }
    }

    private void exportAll() {

        saveEntries(history.getHistory());
    }

    private void exportSelected() {

        showHistory();

        String indexes =
                view.readLine(
                        "Введите номера через запятую: ");

        List<HistoryEntry> selected =
                new ArrayList<>();

        try {

            for (String value :
                    indexes.split(",")) {

                int index =
                        Integer.parseInt(
                                value.trim());

                selected.add(
                        history.getHistory()
                                .get(index));
            }

            saveEntries(selected);

        } catch (Exception e) {

            view.print(
                    "Ошибка выбора записей");
        }
    }

    private void saveEntries(
            List<HistoryEntry> entries) {

        try {

            String input =
                    view.readLine(
                            "Введите путь или имя файла: ");

            if (input.isBlank()) {

                view.print(
                        "История хранится в:");

                view.print(
                        history.getHistoryPath());

                return;
            }

            Path target;

            File file = new File(input);

            if (file.isAbsolute()
                    && file.getName().contains(".")) {

                target = file.toPath();

            } else if (input.contains(".")) {

                target =
                        Paths.get(
                                System.getProperty("user.dir"),
                                input);

            } else {

                target =
                        Paths.get(
                                input,
                                "log.log");
            }

            try (PrintWriter writer =
                         new PrintWriter(
                                 target.toFile())) {

                for (HistoryEntry entry :
                        entries) {

                    writer.println(entry);
                }
            }

            view.print(
                    "Файл сохранён:");

            view.print(
                    target.toAbsolutePath()
                            .toString());

        } catch (Exception e) {

            view.print(
                    "Ошибка сохранения файла");
        }
    }
}
