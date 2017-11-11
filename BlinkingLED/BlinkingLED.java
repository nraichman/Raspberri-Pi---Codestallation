import javax.microedition.midlet.MIDlet;
import jdk.dio.gpio.*;
import jdk.dio.*;
import java.io.*;

class BlinkingLED extends MIDlet implements PinListener {
   private static GPIOPin pin = null;

   @Override
   public void startApp() {
       System.out.println("in startApp method");
       try {
           GPIOPinConfig config = new GPIOPinConfig.Builder()
               .setControllerNumber(0)
               .setPinNumber(18)
               .setDirection(GPIOPinConfig.DIR_OUTPUT_ONLY)
               .setDriveMode(GPIOPinConfig.MODE_OUTPUT_PUSH_PULL)
               .setInitValue(false)
               .build();

           pin = DeviceManager.open(GPIOPin.class, config);
           for (int i = 0; i < 200; i++) {
               pin.setValue(true);
               System.out.println("On " + i);
               Thread.sleep(100);
               pin.setValue(false);
               Thread.sleep(900);
           }
       } catch (IOException e) {
           e.printStackTrace();
       } catch(InterruptedException e) {
           System.out.println("Thread interrupted: " + e.getMessage());
       } finally {
           try {
               if (pin != null) {
                   System.out.println("Closing GPIO pin");
                   pin.close();
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }

   @Override
   public void destroyApp(boolean unconditional) {
       try {
           if (pin != null) {
               System.out.println("destroy called, closing GPIO pin");
               pin.close();
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   @Override
   public void valueChanged(PinEvent event) {
   }
}
