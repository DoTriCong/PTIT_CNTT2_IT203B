package ex5;

public class SleepModeCommand implements Command {

    private Light light;
    private Fan fan;
    private AirConditioner ac;

    public SleepModeCommand(Light light, Fan fan, AirConditioner ac) {
        this.light = light;
        this.fan = fan;
        this.ac = ac;
    }

    @Override
    public void execute() {

        System.out.println("SleepMode: Tắt đèn");
        light.off();

        System.out.println("SleepMode: Điều hòa set 28°C");
        ac.setTemperature(28);

        System.out.println("SleepMode: Quạt tốc độ thấp");
        fan.low();
    }
}