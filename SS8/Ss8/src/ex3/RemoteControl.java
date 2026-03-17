package ex3;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class RemoteControl {

    private Map<Integer, Command> buttons = new HashMap<>();
    private Stack<Command> history = new Stack<>();

    public void setCommand(int button, Command command) {
        buttons.put(button, command);
        System.out.println("Đã gán " + command.getClass().getSimpleName() + " cho nút " + button);
    }

    public void pressButton(int button) {

        Command command = buttons.get(button);

        if (command != null) {
            command.execute();
            history.push(command);
        } else {
            System.out.println("Nút chưa được gán.");
        }
    }

    public void undo() {

        if (!history.isEmpty()) {

            Command command = history.pop();
            command.undo();

        } else {
            System.out.println("Không có lệnh để undo.");
        }
    }
}