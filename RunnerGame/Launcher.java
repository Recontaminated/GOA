public class Launcher {
//    entrypoint for runner game create new window class and attach to thread
    public static void main(String[] args) {
    GameWindow window = new GameWindow();
    Thread t1 = new Thread(window);
    t1.start();
    }
}
