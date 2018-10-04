//Esta classe vai ser a main da aplicação
package spaceinvaders;
/**
 *
 * @author Vinicius
 */
import java.awt.EventQueue;
import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Commons {

    public SpaceInvaders() {

        initUI();
    }

    private void initUI() {

        add(new Board());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BORDA_LARGURA, BORDA_ALTURA);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SpaceInvaders ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}
