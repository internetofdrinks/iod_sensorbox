package internet.of.drinks.bac;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletAnalogIn;
import com.tinkerforge.IPConnection;
import org.codehaus.groovy.control.messages.ExceptionMessage;

import java.io.IOException;

import static java.lang.Math.pow;

/**
 * Created by Martin on 04.11.2016.
 */
public class BacDriver {
    private final IPConnection ipConnection;
    private final BrickletAnalogIn brickletAnalogIn;

    public BacDriver(String brickletId, String host, Integer port) {
        ipConnection = new IPConnection();
        brickletAnalogIn = new BrickletAnalogIn(brickletId, ipConnection);
        try {
            ipConnection.connect(host, port);
        } catch (IOException | AlreadyConnectedException e) {
           throw new RuntimeException(e);
        }
    }

    public double getBacLevel(Integer milliseconds) throws Exception {
        float maxVoltage = 0.0F;

        for(int i=0; i<=milliseconds; i=i+100) {
            // Get current voltage (unit is mV)
            float voltage = (brickletAnalogIn.getVoltage() / 1000.0F) * 1.15F;
            if (voltage >= maxVoltage) {
                maxVoltage = voltage;
            }
            Thread.currentThread().sleep(100);
        }

        System.out.println("Voltage " + maxVoltage);

        if(maxVoltage < 2.2F) {
            System.out.println("You're sober");
            return 0.0;
        }

        double partsPerMillion = 150.4351049F * pow(maxVoltage, 5) - 2244.75988F * pow(maxVoltage, 4) + 13308.5139F * pow(maxVoltage, 3) - 39136.08594F * pow(maxVoltage, 2) + 57082.6258F * maxVoltage - 32982.05333F;

        System.out.println("Parts per Million " + partsPerMillion);


        double bac = partsPerMillion / 2600.0f;
        System.out.println("BAC: " + bac );

        return bac;
    }

    public static void main(String[] args) throws Exception {
        String brickletId = "vhe";
        String host = "localhost";
        Integer port = 4223;

        BacDriver bacDriver = new BacDriver(brickletId, host, port);
        bacDriver.getBacLevel(2000);
    }

}
