package histoGram;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

public class HistogramAlphabet {
    public Map<String, Integer> characterFrequency = new HashMap<>();
    
    public HistogramAlphabet(){
        //empty histogram
    }
    
    public HistogramAlphabet(Map<String, Integer> frequency){
        characterFrequency.putAll(frequency); //add entries to histogram
    }
    
    public HistogramAlphabet(String s){
        String temp = s.replaceAll("[^a-zA-z]", "").toLowerCase();
        for (int i = 0; i < temp.length(); i++ ){
            String key = temp.charAt(i) + "";
            incrementFrequency(characterFrequency, key);
        }
    }
    
    public Map<String, Integer> getFrequency(){ return characterFrequency; }
    public Integer getCumulativeFrequency() {
        return characterFrequency.values().stream().reduce(0, Integer::sum);
    }
    
    //sorting in increasing order
    public Map<String, Integer> sortIncreasing(){
        return characterFrequency.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
                        (e1,e2) -> e2, LinkedHashMap::new));
    }
    
    //sorting in decreasing order
    public Map<String, Integer> sortDecreasing(){
        return characterFrequency.entrySet().stream().sorted(Collections.
                reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, 
                        Map.Entry::getValue, (e1,e2) -> e2, LinkedHashMap::new));       
    }
    
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("Frequency of Characters\n\n");
        for (String K : characterFrequency.keySet()){
            output.append(K).append(": ").append(characterFrequency.get(K)).append("\n");
        }
        return output.toString();
    }
    
    private static <K> void incrementFrequency(Map<K, Integer> map, K key){
        map.putIfAbsent(key, 0);
        map.put(key, map.get(key) + 1);
    }
    
   //Inner Class MyPieChart
    public class MyPieChart {
        private Map<String, Double> probability = new HashMap<String, Double>(); // for probabilities
        private Map<String, Slice> slices =  new HashMap<String, Slice>(); // for slices of pie chart
        
        int num;
        MyPoint center;
        double radius;
        double startingAngle;
        
        public MyPieChart(int num, MyPoint center, double radius, double startingAngle){
            this.num = num;
            this.center = center;
            this.radius = radius;
            this.startingAngle = Optional.of(startingAngle).orElse(0.0);  
            
            probability = getProbability();
            slices = createPieChart();
        }
        
        public Map<String, Double> getProbability(){
            double cumulativeFrequency = 1.0/getCumulativeFrequency();
            for(String key : characterFrequency.keySet()){
                probability.put(key, (double) characterFrequency.get(key) * cumulativeFrequency);
            }
            return probability.entrySet().stream().sorted(Collections.
                reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, 
                        Map.Entry::getValue, (e1,e2) -> e2, LinkedHashMap::new));
        }
        
        public double getSumOfProbability(){ return probability.values().stream().reduce(0.0, Double::sum); }
        
        public Map<String, Slice> createPieChart(){
            MyColor[] colors = MyColor.getMyColors();
            int randomColor = 0;
            for(MyColor c : MyColor.values()){
                colors[randomColor] = c;
            }
           
            double startAngle = startingAngle;
            int counter = 0;
            double probabilityCounter = 0;
            for (String key : probability.keySet()){
                if(counter < num){
                    double angle = 360.0 * probability.get(key);
                    slices.put(key, new Slice(center, radius, startAngle, angle, colors[randomColor]));
                    startAngle += angle;
                    counter++;
                    randomColor++;
                }
                else{
                    double angle = 360.0 * (1 - probabilityCounter);
                    slices.put("All other letters", new Slice(center, radius, startAngle, angle, colors[randomColor]));
                    break;
                }
                probabilityCounter += probability.get(key);
            }
            return slices;
        }
        
        public void draw(GraphicsContext gc){
            double probCounter;
            DecimalFormat df = new DecimalFormat("#.####");
            df.setRoundingMode(RoundingMode.CEILING);
            for(String key : slices.keySet()){
                probCounter = slices.get(key).angleInRadians() / (2*Math.PI);
                double x = 1.3 * radius * Math.cos(Math.toRadians(slices.get(key).getCentralAngle()));
                double y = 1.3 * radius * Math.sin(Math.toRadians(slices.get(key).getCentralAngle()));
                slices.get(key).draw(gc);
                String letter = key + "=" + df.format(probCounter);
                gc.setTextAlign(TextAlignment.CENTER);
                gc.strokeText(letter, center.getX() + x, center.getY() - y);
            }
        }
    }   
}
