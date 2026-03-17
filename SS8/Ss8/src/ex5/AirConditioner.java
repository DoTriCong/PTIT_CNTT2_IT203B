package ex5;

public class AirConditioner implements Observer {

    private int temperature = 25;

    public void setTemperature(int temp) {
        temperature = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + temp);
    }

    public int getTemperature() {
        return temperature;
    }

    @Override
    public void update(int temperature) {

        if (temperature > 30) {
            System.out.println("Điều hòa: Nhiệt độ = " + this.temperature + " (vẫn giữ)");
        }
    }
}