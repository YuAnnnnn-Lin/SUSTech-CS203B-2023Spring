import java.util.*;

public class MazeGenerator {
    private int m;
    private int n;
    private int[][] maze;
    private double k;
    private int e;
    private int minLength;
    private final int attackFrequency;
    private final int askFrequency;
    private List<ARAStar.Node>path;
    private Maze map;

    public static void main(String[] args) {
        MazeGenerator mazeGenerator = new MazeGenerator(30,30,1.2,5,5);
        mazeGenerator.generateMaze();

    }
    public MazeGenerator(int n, int m,double k,int attackFrequency,int askFrequency) {
        this.m = m;
        this.n = n;
        this.k=k;
        this.attackFrequency=attackFrequency;
        this.askFrequency=askFrequency;
        this.maze = new int[this.n][this.m];
    }

    public void generateMaze() {
        // 初始化迷宫
        initializeMaze();
        // 随机生成迷宫路径
        generatePath(0, 0);
        //强行创造终点
        maze[n-1][m-1]=0;

        int min=Math.min(m,n);
        Random random=new Random();
        //随机选取一些点设为非障碍物
        for (int i=0;i<(m+n)/2;i++) {
            int x=random.nextInt(n);
            int y=random.nextInt(m);
            maze[x][y]=0;
        }

        //转换为Node表示
        boolean[][] ma = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maze[i][j] == 1) ma[i][j] = true;
            }
        }
        //初始化地图
        map = new Maze(ma);

        for (int i=1;i<=min-1;i++) {
            //尝试寻找最短路径
            getPath(0, 0, 1);
            //如果没有通路，则依次把终点左上方向斜线上的点取消障碍，直到成功
            if(path==null){
                map.getNode(n-1-i,m-1-i).obstacle=false;
                maze[n-1-i][m-1-i]=0;
            }
            else break;

        }

        minLength=path.size();
        e= (int)(minLength*k);

        int currentE=e;
        //发动攻击
        ArrayList<Integer> attackT=new ArrayList<>();
        ArrayList<Integer> attackX=new ArrayList<>();
        ArrayList<Integer> attackY=new ArrayList<>();

        while (currentE>askFrequency){
            //随机选择攻击时间
            int decrease=random.nextInt(attackFrequency);
            currentE -= decrease;

            //随机选择位置，如果5次都是障碍物就放弃本轮攻击
            for (int i=0;i<5;i++){
                int x=random.nextInt(n);
                int y=random.nextInt(m);

                //一刀切禁止在斜线上设置障碍，避免造成无路可走的情况
                if(x-y==n-m)continue;

                if(!map.isObstacle(x,y)){
                    //不许在最短路径上设置障碍
                    if(path.contains(map.getNode(x,y))){
                        continue;
                    }
                    attackT.add(currentE);
                    attackX.add(x);
                    attackY.add(y);
                    map.getNode(x,y).obstacle=true;
                    break;
                }
            }
            currentE-=attackFrequency;
        }

        //询问
        ArrayList<Integer> askT=new ArrayList<>();
        currentE=e;
        while (currentE> e-minLength){
            int decrease=random.nextInt(askFrequency);
            askT.add(currentE-decrease);
            currentE-=attackFrequency;
        }

        System.out.println(n+" "+m+" "+e);
        // 打印迷宫
        printMaze();
        //打印攻击情况
        System.out.println(attackT.size());
        for (int i=0;i<attackT.size();i++){
            System.out.println(attackT.get(i)+" "+attackX.get(i)+" "+attackY.get(i));
        }
        //打印询问情况
        System.out.println(askT.size());
        for (Integer integer : askT) {
            System.out.println(integer);
        }

    }

    private void initializeMaze() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maze[i][j] = 1; // 设置初始状态为有障碍物
            }
        }
    }

    private void generatePath(int x, int y) {
        // 设置当前位置为无障碍物
        maze[x][y] = 0;

        // 定义四个方向的随机顺序
        int[] directions = {1, 2, 3, 4};
        shuffleArray(directions);

        // 遍历四个方向
        for (int direction : directions) {
            switch (direction) {
                case 1: // 上
                    if (isValidMove(x, y - 2)) {
                        maze[x][y - 1] = 0; // 将上方位置设置为无障碍物
                        generatePath(x, y - 2);
                    }
                    break;
                case 2: // 下
                    if (isValidMove(x, y + 2)) {
                        maze[x][y + 1] = 0; // 将下方位置设置为无障碍物
                        generatePath(x, y + 2);
                    }
                    break;
                case 3: // 左
                    if (isValidMove(x - 2, y)) {
                        maze[x - 1][y] = 0; // 将左方位置设置为无障碍物
                        generatePath(x - 2, y);
                    }
                    break;
                case 4: // 右
                    if (isValidMove(x + 2, y)) {
                        maze[x + 1][y] = 0; // 将右方位置设置为无障碍物
                        generatePath(x + 2, y);
                    }
                    break;
            }
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m && maze[x][y] == 1;

    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void printMaze() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(maze[i][j]+" ");
            }
            System.out.println();
        }
    }

    public void getPath(int x, int y, double e) {
        path=ARAStar.findPath(map.getNode(x,y),map.getNode(n-1,m-1),e);
        ARAStar.deleteRecord(map.getNode(x,y));
    }
}