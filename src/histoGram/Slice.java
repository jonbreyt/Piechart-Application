package histoGram;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Slice {
    private MyPoint center;
    private double radius;
    private double startingAngle;
    private double angle;
    private double arcStartingAngle, arcCenterAngle, arcEndingAngle;
    private MyColor color;
    
    public Slice(MyPoint cp, double r, double sa, double a, MyColor c){
        center = cp;
        radius = r;
        startingAngle = sa;
        angle = a;
        color = c;
        arcStartingAngle = startingAngle;
        arcEndingAngle = arcStartingAngle + angle;
        this.arcCenterAngle = (arcStartingAngle + arcEndingAngle) / 2.0;       
    }
    
    public double getRadius() { return radius; }
    public double getStartingAngle() { return startingAngle; }
    public double getAngle() { return angle; }
    
    @Override
    public String toString() {
        return "Slice Properties:\n" + "Center: (" + center.getX() + ", " + center.getY() 
                + ")\n" + "Radius: " + getRadius() + "\nStarting Angle: " + getStartingAngle() 
                + "\nAngle: " + getAngle();
    }
    
    public void draw(GraphicsContext gc){
        gc.setFill(color.getJavaFXColor());
        gc.fillArc(center.getX() - radius, center.getY() - radius, 2 * radius, 
                2 * radius, startingAngle, angle, ArcType.ROUND);
    }
    
    public double getCentralAngle() { return arcCenterAngle; }
    public double angleInRadians() { return Math.toRadians(angle); }
    
}

