package application;

import jplay.Keyboard;
import jplay.Sprite;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;
public class nave extends obj{
    List<tiro>tiros;
    int delay;
    boolean dano;
    int delaydano;

    public int getDelaydano() {
        return delaydano;
    }

    public void setDelaydano(int delaydano) {
        this.delaydano = delaydano;
        if(this.delaydano <0)this.delaydano = 0;
    }

    public nave(){
        sprite = new Sprite("levels/nave.png");
        sprite.setX(400-sprite.width/2);
        sprite.setY(300-sprite.height/2);
        tiros = new ArrayList<>();
        vel = 0;
        ang = 0;
        delay = 0;
    }

    public void movimenta(){
        Keyboard keyboard = janela.getkeyboard();
        getcom(keyboard);
        sprite.setX(sprite.getX() - vel*sin(ang));
        sprite.setY(sprite.getY() + vel*cos(ang));
        List<tiro>remove = new ArrayList<>();
        for(tiro t:tiros){
            t.movimenta();
            if(limits(t.getSprite()))remove.add(t);
        }
        for(tiro t:remove)tiros.remove(t);
        remove.clear();
    }

    public void getcom(Keyboard keyboard){
        if(keyboard.keyDown(Keyboard.DOWN_KEY)){
            if(vel>0)vel = vel - 0.00004f;
            else vel = 0;
        }
        if(keyboard.keyDown(Keyboard.UP_KEY)){
            if(vel<0.2)vel = vel + 0.00004f;
            else vel = 0.2f;
        }
        if(keyboard.keyDown(Keyboard.LEFT_KEY)){
            ang = ang - 0.002;
            sprite.setRotation(ang);
        }
        if(keyboard.keyDown(Keyboard.RIGHT_KEY)){
            ang = ang + 0.002;
            sprite.setRotation(ang);
        }

    }



    public void geratiro(){
        if(delay == 0){ tiro t = new tiro(this);
        tiros.add(t);
        delay = 600;
        }
    }


    public void imprime(){
        if(delay>0)delay--;
        for(tiro t:tiros)t.getSprite().draw();
        sprite.draw();
    }

    private boolean limits(Sprite s){
        if(s.getX()<-100 || s.getX()>900 || s.getY()<-100 || s.getY()>700)return true;
        return false;
    }


}
