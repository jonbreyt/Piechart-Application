package histoGram;

import javafx.scene.canvas.GraphicsContext;

public class MyOval extends MyShape{
    private MyPoint point; //top left points
    private double height, width; //height and width of oval
    
    //Constructor
    public MyOval(MyPoint p, double height, double width, MyColor color){
        super(p, color);
        point = p;
        this.height = height;
        this.width = width;

    }

    //Accessor Methods
    public double getX(){ return point.getX(); }
    public double getY(){ return point.getY(); }
    public double getA(){ return (width / 2); } // x abscissa
    public double getB(){ return (height / 2); } // y abscissa
    public double getHeight(){ return height; }
    public double getWidth(){ return width; } 
    public MyPoint getPoint(){ return point; }


    @Override
    public double area(){ return Math.PI * getA() * getB(); }

    @Override
    public double perimeter(){ 
        double a = height / 2;
        double b = width / 2;
        return 2 * Math.PI * Math.sqrt((Math.pow(a, 2)) + (Math.pow(b, 2)) / 2);

    }

    @Override
    public String toString(){
        String description = "MyOval: \nReference Point: (" + point.getX() + "," + point.getY() + ")\nArea: " + area() + 
                "\nPerimeter: " + perimeter() + "\nHeight: " + height + "\nWidth: " + width + "\nX Abscissa: " + getA() +
                "\nY Abscissa: " + getB() + "\nColor: " + getColor() + "\n";
        return description;

    }

    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(super.getColor().getJavaFXColor());
        gc.setStroke(super.getColor().getJavaFXColor());
        gc.strokeOval(super.getX(), super.getY(), width, height);
        gc.fillOval(super.getX(), super.getY(), width, height);

    }
    
    @Override
    public MyRectangle getMyBoundingRectangle(){
        return new MyRectangle(point, height, width, MyColor.GREY);
    }
    
    @Override
    public boolean pointInMyShape(MyPoint p){
        double firstPartOfEquation = Math.pow(getPoint().getX() - p.getX(), 2) / Math.pow(getA() + point.getX(), 2);
        double secondPartOfEquation = Math.pow(getPoint().getY() - p.getY(), 2) / Math.pow(getB() + point.getY(), 2);
        if(firstPartOfEquation + secondPartOfEquation <= 1){
            return true;
        }
        return false;
    }
}

