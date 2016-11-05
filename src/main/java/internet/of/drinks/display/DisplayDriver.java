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

    public void displayBac(Double bac) {
        try {
            display.clearDisplay();
            display.writeLine((byte) 0, (byte) 0, "BAC: " + String.format("%1.2f", bac));
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
        BacDriver bacDriver = new BacDriver(App.ADC_UID);
        displayDriver.addButtonPressedListener(button -> {
            double bacLevel = 0;
            try {
                bacLevel = bacDriver.getBacLevel(1000);
                displayDriver.displayBac(bacLevel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        while(true) {
            Thread.currentThread().sleep(10000);
        }
    }
}
