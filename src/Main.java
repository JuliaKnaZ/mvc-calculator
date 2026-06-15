import controller.CalculatorController;
import model.CalculatorModel;
import model.HistoryManager;
import view.ConsoleView;

public class Main {

    public static void main(String[] args) {

        CalculatorModel model = new CalculatorModel();
        ConsoleView view = new ConsoleView();
        HistoryManager historyManager = new HistoryManager();

        CalculatorController controller =
                new CalculatorController(
                        model,
                        view,
                        historyManager);

        controller.run();
    }
}
