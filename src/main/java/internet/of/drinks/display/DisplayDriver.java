package internet.of.drinks.display;

import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import internet.of.drinks.App;
import internet.of.drinks.IpConnection;
import internet.of.drinks.bac.BacDriver;

import java.io.IOException;

/**
 * Created by Martin on 05.11.2016.
 */
public class DisplayDriver {

    private final BrickletLCD20x4 display;

    public DisplayDriver(String brickletId) {
        IPConnection connection = IpConnection.INSTANCE.getConnection();
        display = new BrickletLCD20x4(brickletId, connection);
        try {
            if (!display.isBacklightOn()) {
                display.backlightOn();
            }
        } catch (TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void clearLine(Short line) {
        try {
            display.writeLine(line, (byte) 0, "                    ");
        } catch (TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void displayMessage(Short line, String message) {
        try {
            clearLine(line);
            display.writeLine(line, (byte) 0, message);
        } catch (TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void addButtonPressedListener(BrickletLCD20x4.ButtonPressedListener listener) {
        display.addButtonPressedListener(listener);
    }

    public void close() {
        try {
            if (display.isBacklightOn()) {
                display.backlightOff();
            }
            display.clearDisplay();
        } catch (TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DisplayDriver displayDriver = new DisplayDriver(App.DISP_UID);
        displayDriver.displayMessage((short) 0, "Tt");
        displayDriver.displayMessage((short) 1, "Tt2");
        displayDriver.displayMessage((short) 2, "Te3");
        displayDriver.displayMessage((short) 3, "st4");
    }
}
