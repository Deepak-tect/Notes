import java.util.*;

public class Forest {
    private List<TreeInstance> trees = new ArrayList<>();

    public void plantTree(String name , String color , String texture , int x , int y){
        TreeType type = TreeFactory.getTreeType(name, color, texture);
        TreeInstance tree = new TreeInstance(type, x, y);
        trees.add(tree);
    }
    public void display(){
        for(TreeInstance tree : trees){
            tree.display();
        } 
    }
}
