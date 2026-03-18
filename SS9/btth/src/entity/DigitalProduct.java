package entity;

public class DigitalProduct extends Product {
    private double size;

    public DigitalProduct(String id, String name, double price, double size) {
        super(id, name, price);
        this.size = size;
    }

    public DigitalProduct() {
        super();
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public void displayInfo() {
        System.out.println("Digital Product:");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice());
        System.out.println("Size: " + size + " MB");
    }

    @Override
    public String toString() {
        return "DigitalProduct [id=" + getId() + ", name=" + getName() +
                ", price=" + getPrice() + ", size=" + size + " MB]";
    }
}
