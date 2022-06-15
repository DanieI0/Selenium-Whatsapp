import javax.swing.*;

class Main extends JFrame {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        this.setTitle("Whatsapp Bot");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setSize(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
        this.add(new ProgramPanel(0,0, Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT));
        this.setVisible(true);

    }
}
