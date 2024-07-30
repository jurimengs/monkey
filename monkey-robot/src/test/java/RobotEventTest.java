import com.yunwow.os.robot.core.utils.ContentUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;



public class RobotEventTest {
    public static void main(String[] args) throws AWTException {

        String writeMe = "abc";
        Robot robot=new Robot();
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(new StringSelection(""), null);
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
        String sysClipboardText = ContentUtil.getSysClipboardText();
        System.out.println("ContentUtil : " + sysClipboardText);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }


}