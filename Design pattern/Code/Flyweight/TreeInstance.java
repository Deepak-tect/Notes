public class TreeInstance {
    private TreeType type;
    private int x;
    private int y;

    public TreeInstance(TreeType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void display(){
        type.display(x, y);
    }
    
}
