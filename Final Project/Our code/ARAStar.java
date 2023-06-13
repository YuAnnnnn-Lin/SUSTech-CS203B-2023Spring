import java.util.*;

public class ARAStar {
    private static PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(node -> node.f));
    private static List<Node> closed = new ArrayList<>();
    private static List<Node> incons = new ArrayList<>();
    private static double epsilon;
    private static Node start;
    private static Node goal;
    public static boolean firstTime=true;

    private static List<Node> improvePath() {
        Node current = open.peek();
        if(current==null)return null;

        while (fValue(goal)> fValue(current)) {
            // 从开放列表中取出f值最小的节点
            current = open.poll();
            // 将当前节点加入闭合列表
            closed.add(current);

            // 遍历当前节点的相邻节点
            for (Node neighbor : getNeighbors(current)) {
                // 如果相邻节点已经在闭合列表中，则跳过
                if (closed.contains(neighbor) ) {
                    continue;
                }

                // 如果相邻节点不在开放列表中，则将其加入开放列表
                if (!open.contains(neighbor)) {
                    neighbor.g = Double.POSITIVE_INFINITY;
                }

                // 计算从起点到相邻节点的新路径长度
                double tentative_g = current.g + heuristic(current, neighbor);

                // 如果新路径长度比原来路径长度更小，则更新相邻节点的信息
                if (tentative_g < neighbor.g) {
                    neighbor.g=tentative_g;
                    neighbor.parent = current;

                    if (!closed.contains(neighbor)) {
                        neighbor.h=heuristic(neighbor,goal);
                        fValue(neighbor);
                        open.add(neighbor);
                    }else{
                        incons.add(neighbor);
                    }
                }
            }
        }

        List<Node> path = new ArrayList<>();
        if(current!=goal)path.add(goal);

        while (current !=null) {
            path.add(current);
            current = current.parent;
            if(current==start)break;
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }


    public static List<Node> findPath(Node s, Node g, double e) {
        start=s;
        goal =g;
        epsilon=e;
        if(firstTime){//第一次迭代与后面的迭代是有区别的，详见论文
            goal.g=Double.POSITIVE_INFINITY;

            start.g = 0;
            start.h=heuristic(start,goal);
            open.add(start);

            firstTime=false;
            return improvePath();
        }else{
            for (Node n : incons) {
                open.add(n);
            }

            for (Node n : open) {
                n.h = heuristic(n, goal);
                fValue(n);
            }
            closed.clear();
            return improvePath();
        }
    }

    private static double fValue(Node node){
        return node.f = node.g + node.h * epsilon;
    }

    //设置障碍后，将节点从列表中删除
    public static void deleteRecord(Node node){
        open.clear();
        closed.clear();
        incons.clear();
        firstTime=true;
    }

    // 定义启发函数(对角距离)
    private static double heuristic(Node a, Node b) {
        int dx= abs(a.x-b.x);
        int dy= abs(a.y-b.y);
        return dx+dy+(Math.sqrt(2)-2)*min(dx,dy);
    }

    // 定义辅助函数，用于获取相邻节点
    private static List<Node> getNeighbors(Node node) {
      // 获取节点node的所有相邻节点，并返回一个List集合
        int x= node.x;
        int y= node.y;
        int []dx={1,0,-1,0,1,1,-1,-1};
        int []dy={0,1,0,-1,1,-1,1,-1};
        List<Node> neighbors = new ArrayList<>();
        Maze maze= Node.maze;

        for (int i=0;i<8;i++){
            Node nei=maze.getNode(x+dx[i],y+dy[i]);
            if(nei!=null && !nei.obstacle)neighbors.add(nei);
        }
        return neighbors;
    }

    public static class Node {
        int x, y;
        double f, g, h;
        boolean obstacle;
        boolean isAttacked;
        private Node parent;
        private static Maze maze;

        public Node(int x, int y,boolean obstacle) {
            this.x = x;
            this.y = y;
            this.obstacle=obstacle;

        }
        public static void setMaze(Maze ma){
           maze=ma;
        }
    }
    public static int abs(int n) {
        int mask = n >> 31;
        return (n ^ mask) - mask;
    }
    public static int min(int a, int b) {
        return b ^ ((a ^ b) & -(a < b ? 1 : 0));
    }
}