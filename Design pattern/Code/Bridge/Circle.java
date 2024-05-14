public class Circle implements Shape {
    private Drawable drawable;
    public Circle(Drawable drawable){
        this.drawable = drawable;
    }

    @Override
    public void drawShape() {
        this.drawable.draw();
    }
    
}
