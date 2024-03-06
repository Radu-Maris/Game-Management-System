import view.MainView;
import controller.MainController;

public class Main {
    public static void main(String[] args) throws Exception{
        MainView mainView = new MainView();
        MainController mainController = new MainController(mainView);
        mainView.setVisible(true);
    }
}