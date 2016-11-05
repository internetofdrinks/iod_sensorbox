package internet.of.drinks.bac;

import com.tinkerforge.BrickletAnalogIn;
import com.tinkerforge.IPConnection;
import internet.of.drinks.App;
import internet.of.drinks.IpConnection;

import static java.lang.Math.pow;

/**
 * Created by Martin on 04.11.2016.
 */
public class BacDriver {
    private final IPConnection ipConnection;
    private final BrickletAnalogIn brickletAnalogIn;

    public BacDriver(String brickletId) {
        ipConnection = IpConnection.INSTANCE.getConnection();
        brickletAnalogIn = new BrickletAnalogIn(brickletId, ipConnection);
    }

    public double getBacLevel(Integer milliseconds) throws Exception {
        float maxVoltage = 0.0F;

        for(int i=0; i<=milliseconds; i=i+100) {
            // Get current voltage (unit is mV)
            float voltage = (brickletAnalogIn.getVoltage() / 1000.0F);
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


        double bac = (partsPerMillion / 260.0f) * 1.1;
        System.out.println("BAC: " + bac );

        return bac;
    }

    public static void main(String[] args) throws Exception {
        String brickletId = App.ADC_UID;

        BacDriver bacDriver = new BacDriver(brickletId);
        bacDriver.getBacLevel(5000);
    }

}
