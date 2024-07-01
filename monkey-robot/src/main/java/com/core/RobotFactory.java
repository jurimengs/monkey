package com.core;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class RobotFactory {
    public static Robot createRobot(){
        try {
            Robot robot = new Robot();
//            robot.setAutoWaitForIdle(true);
            return robot;
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
