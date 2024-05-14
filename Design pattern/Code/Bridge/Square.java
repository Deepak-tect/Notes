public class Square implements Shape {

    private Drawable drawable;

    public Square(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void drawShape() {
        this.drawable.draw();
    }
    
}
