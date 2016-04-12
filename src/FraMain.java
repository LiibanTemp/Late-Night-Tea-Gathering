import javax.swing.JFrame;

public class FraMain extends JFrame {

    public FraMain() {
        add(new PanBoard() {});
        setTitle("Late Night Tea Gathering");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(765, 480);
        this.setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}