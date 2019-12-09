package application;

import jplay.Keyboard;
import jplay.Sprite;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public abstract class obj {
    protected Sprite sprite;
    protected float vel;
    protected double ang;

    public Sprite getSprite() {
        return sprite;
    }

    public obj(){
        vel = 0;
        ang = 0.0;
    }

    public float getVel() {
        return vel;
    }

    public double getAng() {
        return ang;
    }

    public void imprime(){
        sprite.draw();
    }

    public double velat(char c){
        if(c == 'X')return - vel*sin(ang);
        if(c == 'Y')return + vel*cos(ang);
        return 0;
    }

    public abstract void movimenta();
}
