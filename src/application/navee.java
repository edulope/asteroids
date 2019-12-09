package application;

import jplay.Keyboard;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class navee extends nave {
    public void getcom(Keyboard keyboard){
        if(keyboard.keyDown(Keyboard.LEFT_KEY)){
            ang = ang - 0.002;
            sprite.setRotation(ang);
        }
        if(keyboard.keyDown(Keyboard.RIGHT_KEY)){
            ang = ang + 0.002;
            sprite.setRotation(ang);
        }
    }
}
