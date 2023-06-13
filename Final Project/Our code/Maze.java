import java.util.List;

public class Maze {
    private int n;
    private int m;

    private ARAStar.Node[][] Map;
    public Maze(boolean[][] map) {
        this.n = map.length;
        this.m = map[0].length;
        Map=new ARAStar.Node[n][m];

        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                Map[i][j]=new ARAStar.Node(i,j,map[i][j]);
            }
        }
        ARAStar.Node.setMaze(this);
    }

    public void setObstacle(int x, int y){
        Map[x][y].obstacle=true;
        Map[x][y].isAttacked=true;
        ARAStar.deleteRecord(Map[x][y]);  //受到攻击后特殊处理
    }

    public ARAStar.Node getNode(int x, int y){
        if(x<0 || x>n-1 || y<0 || y>m-1 ||isObstacle(x,y))return null;
        else return Map[x][y];
    }

    public ARAStar.Node getPoint(int x, int y){
        if(x<0 || x>n-1 || y<0 || y>m-1)return null;
        else return Map[x][y];
    }

    public boolean isObstacle(int x,int y){
        return Map[x][y].obstacle;
    }

    //寻路
    public int[] getPath(int currentX,int currentY,double e){
        List<ARAStar.Node> Path=ARAStar.findPath(Map[currentX][currentY],Map[n-1][m-1],e);

        if(Path==null)return null;

        int l= 2*Path.size();
        int []re=new int[l];
        for (int i=0;i<l;i+=2){
            re[i]=Path.get(i/2).x;
            re[i+1]=Path.get(i/2).y;
        }
        return re;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }
}