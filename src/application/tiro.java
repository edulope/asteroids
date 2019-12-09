package application;
import jplay.Keyboard;
import jplay.Sprite;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class tiro extends obj{
    public tiro(nave n){
        super();
        sprite = new Sprite("levels/tiro.png");
        sprite.setX(n.getSprite().getX()+n.getSprite().width/2);
        sprite.setY(n.getSprite().getY()+n.getSprite().height/2);
        this.ang = n.ang;
        this.vel = 0.2f;
    }
    public void movimenta(){
        sprite.setX(sprite.getX() - vel*sin(ang));
        sprite.setY(sprite.getY() + vel*cos(ang));
    }
}
