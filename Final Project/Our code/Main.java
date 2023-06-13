import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        double e = scanner.nextInt();

        boolean[][] map = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (scanner.nextInt() == 1) map[i][j] = true;
            }
        }
        //初始化地图
        Maze maze = new Maze(map);

        int p = scanner.nextInt();
        int[] attackTime = new int[p]; //假定输入时从大到小排好序
        int[] attackX = new int[p];
        int[] attackY = new int[p];
        for (int i = 0; i < p; i++) {
            attackTime[i] = scanner.nextInt();
            attackX[i] = scanner.nextInt();
            attackY[i] = scanner.nextInt();
        }

        int k = scanner.nextInt();
        int[] ask = new int[k];
        for (int i = 0; i < k; i++) {
            ask[i] = scanner.nextInt();  //假定输入时从大到小排好序
        }

        boolean gui = true;//默认GUI运行
        if(args.length>0){
            if(args[0].equals("terminal"))gui=false;
        }

        if (gui) {
             MazeGUI.controller(n,m,attackTime,attackX,attackY,ask,e,maze);
        } else {
            int currentX = 0;
            int currentY = 0;

            int currentIndexOfAttackT = 0;
            int currentIndexOfAskT = 0;

            ArrayList<Integer> route = new ArrayList<>();
            route.add(0);
            route.add(0);


            while (e >= 1 && !(currentX == n-1 && currentY == m-1)) {
                //攻击
                if (currentIndexOfAttackT < p && e == attackTime[currentIndexOfAttackT]) {
                    int i = attackX[currentIndexOfAttackT];
                    int j = attackY[currentIndexOfAttackT];
                    if (!(i == currentX && j == currentY)) maze.setObstacle(i, j); //不能直接攻击小妮可所在位置
                    currentIndexOfAttackT++;
                }

                //运行ARA*

                int[] path = maze.getPath(currentX, currentY, e);

                //询问
                if (currentIndexOfAskT < k && e == ask[currentIndexOfAskT]) {
                    printArray(path);
                    currentIndexOfAskT++;
                }
                e--;

                //小妮可前进一格
                currentX = path[2];
                currentY = path[3];

                route.add(currentX);
                route.add(currentY);
            }
            printArrayList(route);
        }

    }

    public static void printArray(int[] array) {
        int l = array.length;
        System.out.println(l / 2);
        for (int i = 0; i < l; i++) {
            System.out.print(array[i]);
            if (i < l - 1) System.out.print(" ");
        }
        System.out.println();
    }

    public static void printArrayList(ArrayList<Integer> route) {
        int l = route.size();
        System.out.println(l / 2);
        for (int i = 0; i < l; i++) {
            System.out.print(route.get(i));
            if (i < l - 1) System.out.print(" ");
        }
        System.out.println();
    }
}
