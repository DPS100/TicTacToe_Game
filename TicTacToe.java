import javax.swing.event.MouseInputListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TicTacToe extends JPanel implements MouseInputListener, Runnable {

    private static final long serialVersionUID = 1L;

    public int sizeFactor = 5;
    public int width = 99 * sizeFactor;
    public int height = 99 * sizeFactor;
    public int gridSize = 33 * sizeFactor;
    public int boxes = width / gridSize;
    public boolean mouseClicked = false;
    public boolean p1Turn = true;
    public int p1Won = 0;
    public int x;
    public int y;
    public int[][] board = new int[boxes][boxes];
    public boolean[][] boardOccupied = new boolean[boxes][boxes];
    public JFrame frame;

    public static void main(String[] args) {
        new TicTacToe();
    }

    public TicTacToe() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setContentPane(this);
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        frame.pack();
        setFocusTraversalKeysEnabled(false);
        frame.setLocationRelativeTo(null);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        doGame(g2d);
    }

    public void doGame(Graphics2D g2d) {
        if(winCon() == false){
            drawBoxes(g2d);
            drawGrid(g2d);
            repaint();
        } else {
            winScreen(g2d);
        }
    }

    public void drawGrid(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        for(int horiz = 0; horiz < height; horiz += gridSize) {
            g2d.drawLine(0, horiz, width, horiz);
        }
        for(int vert = 0; vert < width; vert += gridSize) {
            g2d.drawLine(vert, 0, vert, height);
        }
    }

    public boolean winCon() {
        boolean gameDone = false;
        if(p1Won != 0) {gameDone = true;}
        return gameDone;
    }

    public double checkRow(int row, int column){
        if (row == board[0].length) {
            return board[column][row];
        } else {
            return ((board[column][row] + checkRow(column + 1, row + 1)) / row);
        }
    }

    public boolean checkColumn(int column) {
        boolean isTrue = true;
        return isTrue;
    }

    public void winScreen(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,width,height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Player " + p1Won + " Wins!", width / 2, height / 2);
        
    }

    public void addBox() {
        if(mouseClicked == true) {
            if (boardOccupied[x][y] == false) {
                if (p1Turn == true) {
                    board[x][y] = 1;
                    p1Turn = false;
                } else if(p1Turn == false) {
                    board[x][y] = 2;
                    p1Turn = true;
                }
                boardOccupied[x][y] = true;
            }
            mouseClicked = false;
        }
    }

    public void drawBoxes(Graphics2D g2d) {
        addBox();
        for (int c = 0; c < board.length; c++) {
            for(int r = 0; r < board[c].length; r++) {
                if(board[c][r] == 1) {
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect(c * gridSize, r * gridSize, gridSize, gridSize);
                } else if(board[c][r] == 2) {
                    g2d.setColor(Color.RED);
                    g2d.fillRect(c * gridSize, r * gridSize, gridSize, gridSize);
                }
            }
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX() / gridSize;
        y = e.getY() / gridSize;
        //System.out.println(x + " " + y);
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}