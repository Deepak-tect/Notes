public class Test {
    public static void main(String[] args) {
        GuiFactory guiFactory = new GuiImp();
        Factory factory = guiFactory.createFactory("Windows");
        Button btn = factory.createButton();
        TextBox textBox = factory.createTextBox();
        btn.pressButton();
        textBox.pressTextBox();
    }
    
}
