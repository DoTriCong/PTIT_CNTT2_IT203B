package entity;

public class PhysicalProduct extends Product {
    private double weight;

    public PhysicalProduct(String id, String name, double price, double weight) {
        super(id, name, price);
        this.weight = weight;
    }

    public PhysicalProduct() {
        super();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public void displayInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice());
        System.out.println("Weight: " + weight + " kg");
    }

    @Override
    public String toString() {
        return "PhysicalProduct [id=" + getId() + ", name=" + getName() +
                ", price=" + getPrice() + ", weight=" + weight + " kg]";
    }
}