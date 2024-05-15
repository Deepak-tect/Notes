import java.util.HashMap;
import java.util.Map;

public class TreeFactory {
    private static final Map<String , TreeType> treeType = new HashMap<>();

    public static TreeType getTreeType(String name, String color, String texture){
        String key = name + "-" + color + "-" + texture;
        if(!treeType.containsKey(key)){
            TreeType type = new TreeType(name, color, texture);
            treeType.put(key, type);
        }
        return treeType.get(key);

    }
}
