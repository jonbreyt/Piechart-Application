package histoGram;

import javafx.scene.canvas.GraphicsContext;

public class MyPolygon extends MyShape {
    private MyPoint center;
    private int side;
    private double radius;
    
    double x[];
    double y[];
    
    public MyPolygon(MyPoint p, double r, int s, MyColor c){
        super(p, c);
        side = s;
        radius = r;
        center = p;
        x = new double[side];
        y = new double[side];
        double ang = Math.PI * 2 / side;
        for(int i = 0; i < side; i++){
            x[i] = p.getX() + (radius*(-1 * Math.sin(i * ang)));
            y[i] = p.getY() + (radius*(-1 * Math.cos(i * ang)));
        }
    }
    
    public double getRadius(){ return radius; }
    public double getX(){ return center.getX(); }
    public double getY(){ return center.getY(); }
    public double getAngle(){ return (180 * (side -2)) / side; } //interior angle IN DEGREES
    public int getSide(){ return side; }
    @Override
    public double area(){ return perimeter() * radius * 0.5; } //apothem
    @Override
    public double perimeter(){ return side * 2 * radius * Math.sin(Math.PI/side); }
    public MyPoint getCenter(){ return center; }
    
    @Override
    public String toString(){
        return "My Polygon: \nPoint: (" + center.getY() + ", " + center.getY() 
                + ")\nSides: " + getSide() + "\nRadius: " + getRadius()
                + "\nAngle: " + getAngle() + "\nColor: " + super.getColor();
    }
    
    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(super.getColor().getJavaFXColor());
        gc.setStroke(super.getColor().getJavaFXColor());
        gc.setLineWidth(2);
        gc.strokePolygon(x, y, side);
        gc.fillPolygon(x, y, side);
    }
    
    @Override
    public MyRectangle getMyBoundingRectangle(){
        MyPoint topLeftCorner = new MyPoint(center.getX() - radius, center.getY() - radius);
        return new MyRectangle(topLeftCorner, 2 * radius, 2 * radius, MyColor.BEIGE);
    }
    
    @Override
    public boolean pointInMyShape(MyPoint p){
        if(Math.pow(p.getX() - center.getX(), 2) + Math.pow(p.getY() - center.getY(), 2) <= Math.pow(radius, 2)){
            return true;
        }
        return false;
       
    }
}


