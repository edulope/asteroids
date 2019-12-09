package application;

import jplay.Keyboard;
import jplay.Sound;
import jplay.Sprite;
import jplay.Window;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.PI;

public class level extends janela{
    private int cond;
    private int grandes;
    private int limiteg;
    private List<asteroid>novos;
    private List<asteroid>troca;
    private List<obj>retira;
    private List<obj> objetos;
    private int score;
    private Sprite vida;



    public level(){
        super();
        retira = new ArrayList<>();
        novos = new ArrayList<>();
        troca = new ArrayList<>();
        objetos = new ArrayList<>();
        cond = 0;
    }

    public void executa(){
        begin();
        play();
        musica.pause();
        telafinal();
        encerra();
    }

    private void begin(){
        fundo = new Sprite("menu/download.png");
        orgprint();
        musica = new Sound("levels/OST/WARNING.wav");
        musica.play();
        Sprite ready = new Sprite("levels/WARNING.png");
        ready.setX(window.getWidth() / 2 - ready.width / 2);
        ready.setY(window.getHeight() / 2 - ready.height / 2);
        inicio = System.currentTimeMillis();
        fim = System.currentTimeMillis();
        while (fim - inicio < 5000) {
            if (keyboard.keyDown(Keyboard.LEFT_KEY)) cond = 1;
            if (keyboard.keyDown(Keyboard.RIGHT_KEY)) cond = 2;
            if (keyboard.keyDown(Keyboard.DOWN_KEY)) cond = 0;
            fim = System.currentTimeMillis();
            if (fim - inicio < 1000 || (fim - inicio > 2000 && fim - inicio < 3000) || fim - inicio > 4000) {
                if (!imprimiveis.contains(ready)) imprimiveis.add(ready);
            } else if (imprimiveis.contains(ready)) imprimiveis.remove(ready);
            imprime();
            window.update();
        }
    }

    private void play(){
        score = 0;
        limiteg = 3;
        grandes = 0;
        musica = new Sound("levels/OST/tema.wav");
        musica.play();
        fundo = new Sprite("levels/fundo.jpg");
        orgprint();
        musica.setRepeat(true);
        int vidas = 3;
        nave n;
        if(cond == 1)n = new navee();
        else n = new nave();
        if(cond != 2)vida = new Sprite("levels/3vidas.jpg");
        objetos.add(n);
        String s;


        while(vidas > 0){
            if(Math.random()<0.0007)geraposicao();

            if(score>3000){
                limiteg = 4;
                if(score>5000){
                    limiteg = 5;
                    if(score>7000){
                        limiteg = 6;
                        if(score>9000){
                            limiteg = 7;
                        }
                    }
                }
            }




            if(keyboard.keyDown(Keyboard.ESCAPE_KEY)){
                if(pause())descarregarObjetos();
            }
            if(keyboard.keyDown(Keyboard.SPACE_KEY)) n.geratiro();
            fundo.draw();

            for(obj o: objetos){
                o.movimenta();
                switch(isout(o.getSprite())){
                    case(1): if(0 < o.velat('X')) o.getSprite().setX(-o.getSprite().width);
                        break;
                    case(2): if(0 > o.velat('X')) o.getSprite().setX(window.getWidth());
                        break;
                    case(3): if(0 < o.velat('Y')) o.getSprite().setY(-o.getSprite().height);
                        break;
                    case(4): if(0 > o.velat('Y')) o.getSprite().setY(window.getHeight());
                        break;
                }
                if(o instanceof nave || o instanceof navee){
                    nave aux = (nave) o;
                    if(cond !=2){for(tiro t : aux.tiros){
                        for(obj oo : objetos){
                            if(oo instanceof asteroid) {
                                if (t.getSprite().collided(oo.getSprite())){
                                    retira.add(t);
                                    retira.add(oo);
                                }
                            }
                        }
                    }}
                   for(obj oo: objetos){
                       if(oo instanceof asteroid) {
                           if(aux.getSprite().collided(oo.getSprite())){
                               if(cond!=2) {
                                   if (aux.getDelaydano() <= 0) {
                                       vidas--;
                                       aux.setDelaydano(10000);
                                       if (vidas == 2) vida = new Sprite("levels/2vidas.jpg");
                                       if (vidas == 1) vida = new Sprite("levels/1vida.jpg");
                                   }
                               }
                               else retira.add(oo);
                           }
                       }
                   }
                   aux.setDelaydano(aux.getDelaydano()-1);
                }
                if(o instanceof nave || o instanceof navee){
                    nave aux = (nave) o;
                    if((aux.getDelaydano()/1000)%2 == 0)o.imprime();}
                else o.imprime();
            }

            for(obj o: retira){
                if(o instanceof asteroid){
                    asteroid aux = (asteroid) o;
                    if(aux.getTamanho() == 3){
                        grandes --;
                        score = score + 300;
                    }
                    if(aux.getTamanho() == 2)score = score + 100;
                    if(aux.getTamanho() == 1)score = score + 50;
                    geravelocidade(aux);
                }
                else n.tiros.remove((tiro) o);
            }
            retira.clear();


            for(asteroid a:novos){

                if(a.getDelay()<=0)troca.add(a);
                if((a.getDelay()/1000)%2 != 0)a.getSprite().draw();
                a.setDelay(a.getDelay()-1);
            }
            for(asteroid a:troca){
                a.atualizas();
                novos.remove(a);
                objetos.add(a);
            }
            troca.clear();
            if(cond!=2)vida.draw();
            s = "SCORE" + score;
            window.drawText(s, 55, 10, Color.YELLOW);
            window.update();
        }

    }

    private int isout(Sprite s){
        if(s.getX() > window.getWidth())return 1;
        if(s.getX()+s.width < 0)return 2;
        if(s.getY() > window.getHeight())return 3;
        if(s.getY()+s.height < 0)return 4;
        return 0;
    }

    private boolean pause(){
        Sprite pause = new Sprite("levels/pause.jpg");
        boolean b = false;
        while(true){
            if(keyboard.keyDown(Keyboard.RIGHT_KEY) || keyboard.keyDown(Keyboard.LEFT_KEY))b = !b;
            if(keyboard.keyDown(Keyboard.ENTER_KEY))return b;
            pause.draw();
            if(!b){
                window.drawText("CONTINUAR", 250, 400, Color.YELLOW);
                window.drawText("SAIR", 500, 400, Color.WHITE);
            }
            else{
                window.drawText("CONTINUAR", 250, 400, Color.WHITE);
                window.drawText("SAIR", 500, 400, Color.YELLOW);
            }
            window.update();
        }
    }

    private void geravelocidade(asteroid a){
        objetos.remove(a);
        if(a.getTamanho() > 1) {
            if (cond!=2){asteroid b = new asteroid(a.getTamanho() - 1, a.getVel() * 1.5f, a.getAng());
            asteroid c = new asteroid(a.getTamanho() - 1, a.getVel() * 1.5f, a.getAng() + 2.0944);
            asteroid d = new asteroid(a.getTamanho() - 1, a.getVel() * 1.5f, a.getAng() + 4.188795109151);
            b.getSprite().setX(a.getSprite().getX());
            b.getSprite().setY(a.getSprite().getY());
            c.getSprite().setX(a.getSprite().getX());
            c.getSprite().setY(a.getSprite().getY());
            d.getSprite().setX(a.getSprite().getX());
            d.getSprite().setY(a.getSprite().getY());
            objetos.add(b);
            objetos.add(c);
            objetos.add(d);}
            else {
                asteroid b = new asteroid(a.getTamanho() - 1, a.getVel() * 4f, a.getAng());
                asteroid c = new asteroid(a.getTamanho() - 1, a.getVel() * 4f, a.getAng() + 1.25664);
                asteroid d = new asteroid(a.getTamanho() - 1, a.getVel() * 4f, a.getAng() + 2.51327);
                asteroid e = new asteroid(a.getTamanho() - 1, a.getVel() * 4f, a.getAng() + 3.76991);
                asteroid f = new asteroid(a.getTamanho() - 1, a.getVel() * 4f, a.getAng() + 5.02655);
                b.getSprite().setX(a.getSprite().getX());
                b.getSprite().setY(a.getSprite().getY());
                c.getSprite().setX(a.getSprite().getX());
                c.getSprite().setY(a.getSprite().getY());
                d.getSprite().setX(a.getSprite().getX());
                d.getSprite().setY(a.getSprite().getY());
                e.getSprite().setX(a.getSprite().getX());
                e.getSprite().setY(a.getSprite().getY());
                f.getSprite().setX(a.getSprite().getX());
                f.getSprite().setY(a.getSprite().getY());
                objetos.add(b);
                objetos.add(c);
                objetos.add(d);
                objetos.add(e);
                objetos.add(f);
            }
        }
        a = null;
    }
    private void geraposicao(){
        if(grandes<limiteg){
            double posiX = -(50) + Math.random()*window.getWidth()+ 50;
            double posiY = -(50) + Math.random()*window.getHeight()+ 50;
            if(cond == 1){
                    if(Math.random()>=0.5)posiX = -(50) + Math.random()*(window.getWidth()/2 - 1.5*60);
                    else posiX = window.getWidth()/2 + 1.5*60 + Math.random()*(window.getWidth()+ 50);
                    if(Math.random()>=0.5)posiY = -(50) + Math.random()*(window.getHeight()/2 - 1.5*60);
                    else posiY = window.getHeight()/2 + 1.5*60 + Math.random()*(window.getHeight()+ 50);
                }

            asteroid a = new asteroid(3,(float)(0.001 + Math.random()*0.03), Math.random()*2*PI);
            a.getSprite().setX(posiX);
            a.getSprite().setY(posiY);
            novos.add(a);
            grandes ++;
        }
    }

    private void telafinal(){
        if(cond ==0 ){int n = 0;
        fundo = new Sprite("menu/download.png");
        boolean DONE = false;
        char[] alfabeto = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        String s = "";
        while(!DONE){
            fundo.draw();
            if(n == 0)window.drawText("A", 10, 100, Color.YELLOW);
            else window.drawText("A", 10, 100, Color.WHITE);
            if(n == 1)window.drawText("B", 20, 100, Color.YELLOW);
            else window.drawText("B", 20, 100, Color.WHITE);
            if(n == 2)window.drawText("C", 30, 100, Color.YELLOW);
            else window.drawText("C", 30, 100, Color.WHITE);
            if(n == 3)window.drawText("D", 40, 100, Color.YELLOW);
            else window.drawText("D", 40, 100, Color.WHITE);
            if(n == 4)window.drawText("E", 50, 100, Color.YELLOW);
            else window.drawText("E", 50, 100, Color.WHITE);
            if(n == 5)window.drawText("F", 60, 100, Color.YELLOW);
            else window.drawText("F", 60, 100, Color.WHITE);
            if(n == 6)window.drawText("G", 70, 100, Color.YELLOW);
            else window.drawText("G", 70, 100, Color.WHITE);
            if(n == 7)window.drawText("H", 80, 100, Color.YELLOW);
            else window.drawText("H", 80, 100, Color.WHITE);
            if(n == 8)window.drawText("I", 90, 100, Color.YELLOW);
            else window.drawText("I", 90, 100, Color.WHITE);
            if(n == 9)window.drawText("J", 100, 100, Color.YELLOW);
            else window.drawText("J", 100, 100, Color.WHITE);
            if(n == 10)window.drawText("K", 110, 100, Color.YELLOW);
            else window.drawText("K", 110, 100, Color.WHITE);
            if(n == 11)window.drawText("L", 120, 100, Color.YELLOW);
            else window.drawText("L", 120, 100,Color.WHITE);
            if(n == 12)window.drawText("M", 130, 100, Color.YELLOW);
            else window.drawText("M", 130, 100, Color.WHITE);
            if(n == 13)window.drawText("N", 140, 100, Color.YELLOW);
            else window.drawText("N", 140, 100, Color.WHITE);
            if(n == 14)window.drawText("O", 150, 100, Color.YELLOW);
            else window.drawText("O", 150, 100, Color.WHITE);
            if(n == 15)window.drawText("P", 160, 100, Color.YELLOW);
            else window.drawText("P", 160, 100, Color.WHITE);
            if(n == 16)window.drawText("Q", 170, 100, Color.YELLOW);
            else window.drawText("Q", 170, 100, Color.WHITE);
            if(n == 17)window.drawText("R", 180, 100, Color.YELLOW);
            else window.drawText("R", 180, 100, Color.WHITE);
            if(n == 18)window.drawText("S", 190, 100, Color.YELLOW);
            else window.drawText("S", 190, 100, Color.WHITE);
            if(n == 19)window.drawText("T", 200, 100, Color.YELLOW);
            else window.drawText("T", 200, 100, Color.WHITE);
            if(n == 20)window.drawText("U", 210, 100, Color.YELLOW);
            else window.drawText("U", 210, 100, Color.WHITE);
            if(n == 21)window.drawText("V", 220, 100, Color.YELLOW);
            else window.drawText("V", 220, 100, Color.WHITE);
            if(n == 22)window.drawText("W", 230, 100, Color.YELLOW);
            else window.drawText("W", 230, 100, Color.WHITE);
            if(n == 23)window.drawText("X", 240, 100, Color.YELLOW);
            else window.drawText("X", 240, 100, Color.WHITE);
            if(n == 24)window.drawText("Y", 250, 100, Color.YELLOW);
            else window.drawText("Y", 250, 100, Color.WHITE);
            if(n == 25)window.drawText("Z", 260, 100, Color.YELLOW);
            else window.drawText("Z", 260, 100, Color.WHITE);
            if(n == 26)window.drawText("DELETE", 280, 100, Color.YELLOW);
            else window.drawText("DELETE", 280, 100, Color.WHITE);
            if(n == 27)window.drawText("END", 330, 100, Color.YELLOW);
            else window.drawText("END", 330, 100, Color.WHITE);


            if(keyboard.keyDown(Keyboard.LEFT_KEY)){
                if(!keyboard.keyDown(Keyboard.LEFT_KEY)) {
                    n--;
                    if (n < 0) n = 27;
                }
            }
            if(keyboard.keyDown(Keyboard.RIGHT_KEY)){
                if(!keyboard.keyDown(Keyboard.RIGHT_KEY)) {
                    n++;
                    if (n > 27) n = 0;
                }
            }
            if(keyboard.keyDown(Keyboard.ENTER_KEY)){
                if(n<26){
                    if(s.length()<3)s = s + alfabeto[n];
                }
                if(n==26){
                    if(s.length()>0)s = s.substring(0,s.length()-1);
                    else s = "";
                }

                if(n==27){
                    try {
                        FileWriter arq = new FileWriter("score.txt", true);
                        PrintWriter gravarArq = new PrintWriter(arq);
                        gravarArq.printf("\n"

                                + s + " - " + score);
                        arq.close();
                        return;
                    }
                    catch(Exception e){
                        System.out.println("arquivo nao encontrado");
                        return;
                    }
                }
            }

            window.drawText(s, 400, 100, Color.YELLOW);





            window.update();
        }
    }
    }

    public void encerra(){
        cond = 0;
        grandes = 0;
        limiteg = 0;
        novos.clear();
        novos = null;
        troca.clear();
        troca = null;
        retira.clear();
        retira = null;
        objetos.clear();
        objetos = null;
        score = 0;
        vida = null;
    }
}

