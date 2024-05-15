public class Test {
    public static void main(String[] args) {
        
        File  file1 = new File("file1"); 
        File  file2 = new File("file2"); 
        File  file3 = new File("file3");
        
        Directory  dir1 = new Directory("dir1");
        Directory  dir2 = new Directory("dir2");
        Directory  dir3 = new Directory("dir3");
        dir2.add(file1);
        dir2.add(file2);
        dir2.add(dir3);
        dir3.add(file3);
        dir1.add(dir2);
        dir1.showDetails();

        

    }
}
