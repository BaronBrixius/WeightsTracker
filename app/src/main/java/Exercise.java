public class Exercise {
    private String name;
    private double weight;
    private double unit;

    public Exercise(String name, double weight){
        this(name, weight, 5);
    }

    public Exercise(String name, double weight, double unit){
        setName(name);
        setWeight(weight);
        setUnit(unit);
    }

    public String setName(String name){
        return this.name = name;
    }

    public double increment(){
        return weight += unit;
    }

    public double decrement(){
        return weight -= unit;
    }

    public double setWeight(double weight){
        return this.weight = weight;
    }

    public double setUnit(double unit) {
        return this.unit = unit;
    }
}
