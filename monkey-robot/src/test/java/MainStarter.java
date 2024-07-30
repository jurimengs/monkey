import com.yunwow.os.robot.GlobalManager;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class MainStarter {

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.OFF);
        try {
//            GlobalManager.getInstance().DEBUG();
            GlobalManager.getInstance().init();
            GlobalManager.getInstance().startRecord();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
