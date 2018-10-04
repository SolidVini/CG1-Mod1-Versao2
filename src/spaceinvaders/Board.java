//A logica proncipal do jogo esta aqui
package spaceinvaders;

/**
 *
 * @author Vincius
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable, Commons {

    private Dimension d;
    private ArrayList<Aliem> aliens;
    private Jogador jogador;
    private Tiro tiro;
    private Boss boss;

    private final int ALIEN_INIT_X = 150;
    private final int ALIEN_INIT_Y = 5;
    private int direction = -1;
    private int mortes = 0;
    private int tiros = 0;
    private int directionBoss = -1;

    public boolean jogoRolando = true;
    public boolean botaoRestart = false;
    private final String explImg = "src/images/explosion.png";
    private String message = "Fim de Jogo! - Aperte R";
    private boolean bossVivo = false;

    private Thread animator;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BORDA_LARGURA, BORDA_ALTURA);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    } 

    @Override
    public void addNotify() {

        super.addNotify();
        gameInit();
    }

    
    //Neste método foi criado 24 aliens.
    //Os aliens tem um tamanho de 12X12 px
    //Tem um espaço de 6 px entre os aliens
    //E tambem foi criado um jogador e um tiro
    public void gameInit() {

        aliens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                Aliem alien = new Aliem(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }

        jogador = new Jogador();
        tiro = new Tiro();
        boss = new Boss(ALIEN_INIT_X + 18, ALIEN_INIT_Y + 18);

        if (animator == null || !jogoRolando) {

            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {

        Iterator it = aliens.iterator();

        for (Aliem alien: aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.morre();
            }
        }
    }
    
    public void drawBoss(Graphics g) {

        if (boss.isVisible()) {

            g.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);
        }

        if (boss.isDying()) {

            boss.morre();
        }
    }

    public void drawPlayer(Graphics g) {

        if (jogador.isVisible()) {
            
            g.drawImage(jogador.getImage(), jogador.getX(), jogador.getY(), this);
        }

        if (jogador.isDying()) {

            jogador.morre();
            jogoRolando = false;
        }
    }

    public void drawShot(Graphics g) {

        if (tiro.isVisible()) {
            
            g.drawImage(tiro.getImage(), tiro.getX(), tiro.getY(), this);
        }
    }

    //Metodo para desenhar as bombas
    public void drawBombing(Graphics g) {

        for (Aliem a : aliens) {
            
            Aliem.Bomba b = a.getBomb();

            if (!b.isDestroyed()) {
                
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    //Metodo para desenhar os componentes
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (jogoRolando) {

            g.drawLine(0, CHAO, BORDA_LARGURA, CHAO);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BORDA_LARGURA, BORDA_ALTURA);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BORDA_LARGURA / 2 - 30, BORDA_LARGURA - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BORDA_LARGURA / 2 - 30, BORDA_LARGURA - 100, 50);

        Font small = new Font("Arial", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BORDA_LARGURA - metr.stringWidth(message)) / 2,
            BORDA_LARGURA / 2);  
    }

    public void animationCycle() {

        //Se destruir todos os aliens você ganha o jogo
        if (mortes == NUMERO_DE_ALIENS_PARA_DESTRUIR) {
                aliens.removeAll(aliens);
                aliens.add(boss);
        }
        
        if (tiros == HITS) {
              jogoRolando = false;
              message = "Você Ganhou! - Aperte R";
        }

        // player
        jogador.act();

        // shot
        if (tiro.isVisible()) {

            int shotX = tiro.getX();
            int shotY = tiro.getY();

            for (Aliem alien: aliens) {

                //Se o jogador der um tiro, o aliem é destruido
                //A flag de morte é ativada que é usada para mostrar a explosão
                //A variavel de mortes é incrementada e a Sprite de tiro é destruida.
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && tiro.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + ALIEM_LARGURA)
                            && shotY >= (alienY)
                            && shotY <= (alienY + ALIEM_ALTURA)) {
                        if(!alien.equals(boss)){
                            ImageIcon ii
                                = new ImageIcon(explImg);
                            alien.setImage(ii.getImage());
                        }
                        if(tiros == HITS){
                            ImageIcon ii
                                = new ImageIcon(explImg);
                            alien.setImage(ii.getImage());
                        }
                        
                        if(!alien.equals(boss))
                            alien.setDying(true);
                        if (tiros == HITS)
                            alien.setDying(true);
                        mortes++;
                        tiros++;
                        tiro.morre();
                    }
                }
            }

            int y = tiro.getY();
            y -= 4;

            if (y < 0) {
                tiro.morre();
            } else {
                tiro.setY(y);
            }
        }

        // aliens

        for (Aliem alien: aliens) {

            //Se os aliens chegarem na borda do Board, eles descem e mudam de direção.
            int x = alien.getX();

            if (x >= BORDA_LARGURA - BORDA_DIREITA && direction != -1) {

                direction = -1;
                Iterator i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Aliem a2 = (Aliem) i1.next();
                    a2.setY(a2.getY() + DESCER);
                }
            }

            if (x <= BORDA_ESQUERDA && direction != 1) {

                direction = 1;

                Iterator i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Aliem a = (Aliem) i2.next();
                    a.setY(a.getY() + DESCER);
                }
            }
        }

        //Este código mover os aliens
        //Se chegarem no chão, a invasão começa
        Iterator it = aliens.iterator();

        while (it.hasNext()) {
            
            Aliem alien = (Aliem) it.next();
            
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > CHAO - ALIEM_ALTURA) {
                    jogoRolando = false;
                    message = "Invasão! - Aperte R";
                }

                alien.act(direction);
            }
        }

        // bombs
        Random generator = new Random();

        for (Aliem alien: aliens) {

            //Este código que determina se o aliem vai jogar uma bomba.
            //O aliem não deve estar destruido e deve estar visivel
            //A flag "destroyed" deve ser setada
            //Se a primeira bomba do aliem cair ou a ultima bomba acertar o chão
            //Se as condições forem cumpridas, a bomba fica por conta do CHANCE, que deixa a queda das bombas de modo aleatório
            int shot = generator.nextInt(15);
            Aliem.Bomba b = alien.getBomb();

            if (shot == CHANCE && alien.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(alien.getX());
                b.setY(alien.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = jogador.getX();
            int playerY = jogador.getY();

            if (jogador.isVisible() && !b.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + JOGADOR_LARGURA)
                        && bombY >= (playerY)
                        && bombY <= (playerY + JOGADOR_ALTURA)) {
                    ImageIcon ii
                            = new ImageIcon(explImg);
                    jogador.setImage(ii.getImage());
                    jogador.setDying(true);
                    b.setDestroyed(true);
                }
            }

            //Se a bomba não estiver destruida, ela vai até a 1px do chão
            //Se acertar o chão, a flag "destroyd" é setada e o aliem pode atirar outra bomba
            if (!b.isDestroyed()) {
                
                b.setY(b.getY() + 1);
                
                if (b.getY() >= CHAO - BOMBA_ALTURA) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    @Override
   public void run() {

        long beforeTime, timeDiff, sleep;
        long aux=0;

        beforeTime = System.currentTimeMillis();

        while(true){
            while (jogoRolando) {
                repaint();
                animationCycle();

                timeDiff = (System.currentTimeMillis() - beforeTime) - aux;
                sleep = DELAY - timeDiff;

                if (sleep < 0) {
                    sleep = 2;
                }

                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }

                beforeTime = System.currentTimeMillis();
            }
            
            gameOver();
            if (!jogoRolando && botaoRestart){
                gameInit();
                aux = System.currentTimeMillis();
                mortes = 0;
                tiros = 0;
                jogoRolando = true;
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_R) {
                botaoRestart = false;
            }
            jogador.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            jogador.keyPressed(e);

            int x = jogador.getX();
            int y = jogador.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_R) {
                botaoRestart = true;
            }

            if (key == KeyEvent.VK_SPACE) {

                if (jogoRolando) {
                    if (!tiro.isVisible()) {
                        tiro = new Tiro(x, y);
                    }
                }
            }
        }
    }
}