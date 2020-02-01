import javax.swing.event.MouseInputListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MegaTic extends JPanel implements MouseInputListener, Runnable {

    private static final long serialVersionUID = 1L;

    public int sizeFactor = 75;
    public int width = 9 * sizeFactor;
    public int height = 9 * sizeFactor;
    public int gridSize = 1 * sizeFactor;
    public int boxes = width / gridSize;
    public boolean mouseClicked = false;
    public boolean p1Turn = true;
    public int p1Won = 0;
    public int x;
    public int y;
    public int[][] board = new int[boxes][boxes];
    public boolean[][] boardOccupied = new boolean[boxes][boxes];
    public int[][] bigBoard = new int[boxes/3][boxes/3];
    public boolean[][] bigBoardOccupied = new boolean[boxes/3][boxes/3];
    public JFrame frame;

    public static void main(String[] args) {
        new MegaTic();
    }

    public MegaTic() {
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
            checkGame();
            checkGrids();
            drawBoxes(g2d);
            drawGames(g2d);
            drawGrids(g2d);
            legalMove(g2d);
            repaint();
        } else {
            winScreen(g2d);
        }
    }

    private void legalMove(Graphics2D g2d) {
    }

    private void checkGrids() {

        for(int x = 0; x <= 2; x++) {
            for(int y = 0; y <=2 ; y++) {
                if(checkColumns(x * 3, y * 3, false) != 0) {p1Won = checkColumns(x * 3, y * 3, false);}
                if(checkRows(x * 3, y * 3, false) != 0) {p1Won = checkRows(x * 3, y * 3, false);}
                if(checkDiags(x * 3, y * 3, false) != 0) {p1Won = checkDiags(x * 3, y * 3, false);}
            }
        }
	}
 
	private void checkGame() {
        
	}
 
	public void drawGrids(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        for(int horiz = 0; horiz < height; horiz += gridSize) {
            g2d.drawLine(0, horiz, width, horiz);
        }
        for(int vert = 0; vert < width; vert += gridSize) {
            g2d.drawLine(vert, 0, vert, height);
        }
    }

    private void drawGames(Graphics2D g2d) {
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

    public int checkRow(int xBox, int yBox, boolean isLarge){
        int[] row = new int[3];
        for(int x = 0; x <= 2; x++) {
            row[x] = board[xBox + x][yBox];
        } if(row[0] == 0) {return 0;} else {
            if(row[0] == row[1] && row[1] == row[2]) {return row[0];}
            else {return 0;}
        }

    }
 
    public int checkRows(int xBox, int yBox, boolean isLarge){
        int whoWon = 0;
        for(int y = 0; y <= 2; y++) {
            whoWon = checkRow(xBox, yBox + y, isLarge);
            if(whoWon != 0){return whoWon;}
        }
        return 0;
    }

    public int checkColumn(int xBox, int yBox, boolean isLarge) {
        int[] column = new int[3];
        for(int y = 0; y <= 2; y++) {
            column[y] = board[xBox][yBox + y];
        } if(column[0] == 0) {return 0;} else {
            if(column[0] == column[1] && column[1] == column[2]) {return column[0];}
            else {return 0;}
        }
    }

    public int checkColumns(int xBox, int yBox, boolean isLarge) {
        int whoWon = 0;
        for(int x = 0; x <= 2; x++) {
            whoWon = checkColumn(xBox + x, yBox, isLarge);
            if(whoWon != 0){return whoWon;}
        }
        return 0;
    }

    public int checkDiags(int xBox, int yBox, boolean isLarge) {
        int[] slash = new int[3];
        for(int xy = 0; xy <= 2; xy++) {
            slash[xy] = board[xBox + xy][yBox + xy];
        } if (slash[0] != 0) {
            if (slash[0] == slash[1] && slash[1] == slash[2]) {return slash[0];}
        } 
        for(int xyo = 0; xyo <= 2; xyo++) {
            slash[xyo] = board[xBox + 2 - xyo][yBox + xyo];
        } if (slash[0] == 0) {return 0;} else {
            if (slash[0] == slash[1] && slash[1] == slash[2]) {return slash[0];}
            else {return 0;}
        }
    }

    public void winScreen(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,width,height);
        if (p1Won == 1) {g2d.setColor(Color.BLUE);} else {g2d.setColor(Color.RED);}
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
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}