import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystemComponent {

    private String name;
    private List<FileSystemComponent> children ;

    public Directory(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void add(FileSystemComponent fileSystemComponent){
        children.add(fileSystemComponent);
    }

    public void remove(FileSystemComponent fileSystemComponent){
        children.remove(fileSystemComponent);
    }

    @Override
    public void showDetails() {
        System.out.println("Directory: " + name);
        for(FileSystemComponent component : children){
            component.showDetails();
        }
    }
    
}
