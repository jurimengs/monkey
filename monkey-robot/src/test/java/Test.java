import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.DefaultDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Test implements NativeKeyListener{
    private static final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    public static void main(String[] args) {
        //关闭日志
        logger.setLevel(Level.OFF);
        new Test();
    }

    public Test() {
        //添加监听调度员，如果是Swing程序，就用SwingDispatchService，不是就用默认的
        GlobalScreen.setEventDispatcher(new DefaultDispatchService());
        try {
            //注册监听
            GlobalScreen.registerNativeHook();
            //加入键盘监听，
            GlobalScreen.addNativeKeyListener(this);

        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    //实现键盘监听的三个方法，根据自己情况实现
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        System.out.println("typed"+nativeEvent.paramString());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        System.out.println("pressed"+nativeEvent.paramString());
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        System.out.println("released"+nativeEvent.paramString());
    }
}