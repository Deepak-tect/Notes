public class GuiImp implements GuiFactory {

    @Override
    public Factory createFactory(String fact) {
        if(fact == "Windows") return new WindowsFactory();
        else if(fact == "Mac") return new MacFactory();
        else return new MacFactory();
    }
    
}
