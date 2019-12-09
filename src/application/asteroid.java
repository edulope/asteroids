package application;

import jplay.Keyboard;
import jplay.Sprite;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class asteroid extends obj{
    private int tamanho;
    private int delay;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public asteroid(int i, float v, double a){
        delay = 0;
        if(tamanho <= 3){
            tamanho = i;
            if(tamanho == 1)sprite = new Sprite("levels/AP.png");
            if(tamanho == 2)sprite = new Sprite("levels/AM.png");
            if(tamanho == 3){
                sprite = new Sprite("levels/AGWARNING.png");
                delay = 6000;
            }
            vel = v;
            ang = a;
        }
        else sprite = null;

    }

    public int getTamanho() {
        return tamanho;
    }

    public void movimenta(){
        sprite.setX(sprite.getX() - vel*sin(ang));
        sprite.setY(sprite.getY() + vel*cos(ang));
    }

    public void atualizas(){
        double X = sprite.getX();
        double Y = sprite.getY();
        sprite = new Sprite("levels/AG.png");
        sprite.setX(X);
        sprite.setY(Y);
    }


}
