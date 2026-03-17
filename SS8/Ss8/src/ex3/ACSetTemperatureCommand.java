package ex3;

public class ACSetTemperatureCommand implements Command {

    private AirConditioner ac;
    private int newTemp;
    private int oldTemp;

    public ACSetTemperatureCommand(AirConditioner ac, int temp) {
        this.ac = ac;
        this.newTemp = temp;
    }

    public void execute() {
        oldTemp = ac.getTemperature();
        ac.setTemperature(newTemp);
    }

    public void undo() {
        ac.setTemperature(oldTemp);
        System.out.println("Undo: Điều hòa: Nhiệt độ = " + oldTemp);
    }
}