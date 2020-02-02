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
        frame = new JFrame("MEGA Tic Tac Toe");
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
            drawGrids(g2d);
            drawGames(g2d);
            legalMove(g2d);
            repaint();
        } else {
            winScreen(g2d);
            repaint();
        }
    }

    private void legalMove(Graphics2D g2d) {
    }

    private void checkGrids() {
        int c;
        int r;
        int d;;
        for(int x = 0; x <= 2; x++) {
            for(int y = 0; y <=2 ; y++) {
                if(bigBoardOccupied[x][y] == false) {
                    c = checkColumns(x * 3, y * 3, false);
                    r = checkRows(x * 3, y * 3, false);
                    d = checkDiags(x * 3, y * 3, false);
                    if(c != 0) {bigBoard[x][y] = c; bigBoardOccupied[x][y] = true;}
                    if(r != 0) {bigBoard[x][y] = r; bigBoardOccupied[x][y] = true;}
                    if(d != 0) {bigBoard[x][y] = d; bigBoardOccupied[x][y] = true;}
                } else {
                    fillBox(x * 3, y * 3);
                }
            }
        }
	}
 
	private void checkGame() {
        if(p1Won == 0) {p1Won = checkColumns(0,0,true);}
        if(p1Won == 0) {p1Won = checkRows(0,0,true);}
        if(p1Won == 0) {p1Won = checkDiags(0,0,true);}
               
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
        for(int horiz = 1; horiz < height; horiz += gridSize * 3) {
            g2d.drawLine(0, horiz, width, horiz);
            g2d.drawLine(0, horiz+1, width, horiz+1);
            //g2d.drawLine(0, horiz-1, width, horiz-1);
        }
        for(int vert = 1; vert < width; vert += gridSize * 3) {
            g2d.drawLine(vert+1, 0, vert+1, height);
            g2d.drawLine(vert-1, 0, vert-1, height);
        }
    }

    public boolean winCon() {
        boolean gameDone = false;
        if(p1Won != 0) {gameDone = true;}
        return gameDone;
    }

    public void fillBox(int xBox, int yBox) {
        for(int x = 0; x <= 2; x++) {
            for(int y = 0; y <= 2; y++) {
                boardOccupied[x + xBox][y + yBox] = true;
            }
        }
    }

    public int checkRow(int xBox, int yBox, boolean isLarge){
        int[] row = new int[3];
        for(int x = 0; x <= 2; x++) {
            if(!isLarge){row[x] = board[xBox + x][yBox];}
            else{row[x] = bigBoard[xBox + x][yBox];}
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
            if(!isLarge) {column[y] = board[xBox][yBox + y];}
            else {column[y] = bigBoard[xBox][yBox + y];}
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
            if(!isLarge){slash[xy] = board[xBox + xy][yBox + xy];}
            else {slash[xy] = bigBoard[xBox + xy][yBox + xy];}
        } if (slash[0] != 0) {
            if (slash[0] == slash[1] && slash[1] == slash[2]) {return slash[0];}
        } 
        for(int xyo = 0; xyo <= 2; xyo++) {
            if(!isLarge){slash[xyo] = board[xBox + 2 - xyo][yBox + xyo];}
            else{slash[xyo] = bigBoard[xBox + 2 - xyo][yBox + xyo];}
        } if (slash[0] == 0) {return 0;} else {
            if (slash[0] == slash[1] && slash[1] == slash[2]) {return slash[0];}
            else {return 0;}
        }
    }

    public void winScreen(Graphics2D g2d) {
        g2d.setColor(new Color(0,0,0,2));
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
                    g2d.setColor(new Color(0,0,255,3));
                    g2d.fillRect(c * gridSize, r * gridSize, gridSize, gridSize);
                } else if(board[c][r] == 2) {
                    g2d.setColor(new Color(255,0,0,3));
                    g2d.fillRect(c * gridSize, r * gridSize, gridSize, gridSize);
                }
            }
        }
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                if(bigBoard[x][y] == 1) {
                    g2d.setColor(new Color(0,0,255,1));
                    g2d.fillRect(x * (gridSize * 3), y * (gridSize * 3), gridSize * 3, gridSize * 3);
                } else if(bigBoard[x][y] == 2) {
                    g2d.setColor(new Color(255,0,0,1));
                    g2d.fillRect(x * (gridSize * 3), y * (gridSize * 3), gridSize * 3, gridSize * 3);
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