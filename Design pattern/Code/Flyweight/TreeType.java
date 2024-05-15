public class TreeType implements Tree {

    private final String name;
    private final String color;
    private final String texture;

    public TreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    @Override
    public void display(int x, int y) {
        System.out.println("Displaying tree of type: " + name + " with color " + color + " and texture " + texture + " at (" + x + ", " + y + ")");
        
    }
    
}
