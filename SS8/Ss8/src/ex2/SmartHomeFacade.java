package ex2;

public class SmartHomeFacade {

    private Light light;
    private Fan fan;
    private AirConditioner ac;
    private TemperatureSensor sensor;

    public SmartHomeFacade(TemperatureSensor sensor) {
        this.light = new Light();
        this.fan = new Fan();
        this.ac = new AirConditioner();
        this.sensor = sensor;
    }

    // Chế độ rời nhà
    public void leaveHome() {

        light.off();
        fan.off();
        ac.off();
    }

    // Chế độ ngủ
    public void sleepMode() {

        light.off();
        ac.setTemperature(28);
        fan.lowSpeed();
    }

    // Lấy nhiệt độ
    public void getCurrentTemperature() {

        double temp = sensor.getTemperatureCelsius();

        System.out.println("Nhiệt độ hiện tại: " + temp + "°C");
    }
}
