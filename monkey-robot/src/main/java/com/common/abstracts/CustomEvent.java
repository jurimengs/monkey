package com.common.abstracts;

import java.awt.*;
import java.io.Serializable;

public interface CustomEvent extends Serializable {
    void execute(Robot robot) throws InterruptedException;
}
