import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MazeGUI extends JFrame {
    private static final int MAX_CELL_SIZE = 50;  // 最大格子大小
    private static final Color COLOR_ATK = Color.BLACK;  // 受攻击的障碍物颜色
    private static final Color COLOR_PLAYER = Color.BLUE;  // 主角颜色
    private static final Color COLOR_TREASURE = Color.RED;  // 宝藏颜色
    private static final Color COLOR_PATH = Color.WHITE;  // 可行走路径颜色
    private static final Color COLOR_OBSTACLE = Color.GRAY;  // 障碍颜色
    private static final Color COLOR_ROUTE = Color.GREEN;  // 路线颜色

    private int rows;
    private int columns;
    private int cellSize;
    private static double e;
    private JPanel mazePanel;
    private JLabel infoLabel;
    private static Maze maze;
    private static int n;
    private static int m;
    private static int[] attackT;
    private static int[] attackX;
    private static int[] attackY;
    private static int[] askT;

    private static int currentX = 0;
    private static int currentY = 0;
    private static int currentIndexOfAttackT = 0;
    private static int currentIndexOfAskT = 0;

    private static int p;
    private static int k;

    private static  ArrayList<Integer> route = new ArrayList<>();
    private static short[][]map;
    private static int step;

    public MazeGUI(Maze maze) {
        rows = maze.getN();
        columns = maze.getM();

        // 计算格子大小
        int maxCellSize = Math.min(MAX_CELL_SIZE, Math.min(
                Toolkit.getDefaultToolkit().getScreenSize().width / columns,
                (Toolkit.getDefaultToolkit().getScreenSize().height - 100) / rows
        ));
        cellSize = Math.max(1, maxCellSize);  // 至少为1

        setTitle("Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        mazePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(1));  // 设置线条宽度为1
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < columns; col++) {
                        int type=map[row][col];

                        int x = col * cellSize;
                        int y = row * cellSize;

                        if(row==n-1 && col==m-1){
                            g.setColor(COLOR_TREASURE);
                        }else if(type==-1){
                            g.setColor(COLOR_ATK);
                        }else if(type==0){
                            g.setColor(COLOR_PATH);
                        }else if (type==1){
                            g.setColor(COLOR_OBSTACLE);
                        }else if (type==2){
                            g.setColor(COLOR_PLAYER);
                        }else{
                            g.setColor(COLOR_ROUTE);
                        }

                        g.fillRect(x, y, cellSize, cellSize);
                        g.setColor(Color.LIGHT_GRAY);
                        g.drawRect(x, y, cellSize, cellSize);
                    }
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(columns * cellSize, rows * cellSize);
            }
        };

        JPanel infoPanel = new JPanel();

        infoLabel = new JLabel(String.format("初始时e=%d          ",(int)e));
        JButton button1 = new JButton("Next");

        infoPanel.add(infoLabel);
        infoPanel.add(button1);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
             if (currentIndexOfAskT<askT.length && e >= 1 && !(currentX == n- 1 && currentY == m-1)) {

                int[] path = run(true);//反复寻路直到询问

                //询问
                if (currentIndexOfAskT < k) {
                    convert(maze);
                    currentIndexOfAskT++;
                    if (path != null) {
                        for (int i = 2; i < path.length; i += 2) {
                            map[path[i]][path[i + 1]] = 3;
                        }
                    }
                    int size=route.size();
                    map[route.get(size-4)][route.get(size-3)] = 2;

                    mazePanel.repaint();
                    //更新infoLabel的文本
                    infoLabel.setText(String.format("此时e=%d，距离终点%d步",(int)e+1,step));
                }
             }else {
                 run(false);
                 convert(maze);
                 for (int i=0;i<route.size();i+=2){
                      map[route.get(i)][route.get(i+1)]=2;
                 }

                 //更新infoLabel的文本
                 infoLabel.setText(String.format("小妮可走了%d步到达终点 ",route.size()/2));
                 mazePanel.repaint();
             }
        }
        });


        setLayout(new BorderLayout());
        add(mazePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        pack();

        // 设置窗口最大尺寸不超过屏幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = screenSize.width - 50;
        int maxHeight = screenSize.height - 50;
        setMaximumSize(new Dimension(maxWidth, maxHeight));
        setLocationRelativeTo(null);
    }

    //多次迭代，直到被询问或走到终点
    private  int[] run(boolean stopWhenAsk){
        int []path=null;
        int stop=0;
        if(stopWhenAsk)stop=askT[currentIndexOfAskT];

        for (int s = (int) e; s >=stop; s--) {
            if(currentX==n-1 && currentY==m-1)break;
            if (currentIndexOfAttackT < p && e == attackT[currentIndexOfAttackT]) {
                int i = attackX[currentIndexOfAttackT];
                int j = attackY[currentIndexOfAttackT];
                if (!(i == currentX && j == currentY)) {
                    maze.setObstacle(i, j); //不能直接攻击小妮可所在位置
                }else{
                    maze.getPoint(i,j).isAttacked=true;//但是要标记出来
                }
                currentIndexOfAttackT++;

            }

            //运行ARA*
            path= maze.getPath(currentX, currentY, e);
            step=path.length/2;

            //小妮可前进一格
            currentX = path[2];
            currentY = path[3];
            route.add(currentX);
            route.add(currentY);

            e--;
        }
        return path;
    }

    public static void controller(int N, int M, int[] AttackT, int[] AttackX, int[] AttackY, int[] AskT, double elis, Maze ma){
        e=elis;
        maze=ma;
        n=N;
        m=M;

        map=new short[n][m];

        attackT=AttackT;
        attackX=AttackX;
        attackY=AttackY;
        askT=AskT;

        p=AttackT.length;
        k=AskT.length;

        route.add(0);
        route.add(0);

        convert(maze);

        SwingUtilities.invokeLater(() -> {
            MazeGUI mazeGUI = new MazeGUI(maze);
            mazeGUI.setVisible(true);
        });
    }

    private static void convert(Maze ma){
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                boolean obs= ma.getPoint(i,j).obstacle;
                if(obs){
                    if(ma.getPoint(i,j).isAttacked)map[i][j]=-1;
                    else map[i][j]=1;
                }else map[i][j]=0;
            }
        }
    }
}