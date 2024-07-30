
import com.constant.Constant;

import java.awt.event.KeyEvent;

public class JunitTest {

    public void Test() {
        for (int i = 0; i < Constant.FunctionKey.length; i++) {
            System.out.println((Constant.FunctionKey[i] ^ KeyEvent.VK_F5));
        }
    }

}
