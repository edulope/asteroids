package application;

import jplay.Keyboard;
import jplay.Sound;
import jplay.Sprite;

import java.awt.*;

public class menu extends janela{


    public void executa(){
        try{abertura();
            abertura2();}
        catch (Exception e){
            orgprint();
            musica.pause();
            musica = new Sound("menu/ost/2.wav");
            musica.play();
        }
        orgprint();
        fundo = new Sprite("menu/download.png");
        menufinal();
    }

    private void abertura() throws Exception{
        fundo = new Sprite("menu/capa+preto.jpg");
        Sprite logo = new Sprite("menu/uff-logo.png");
        logo.setX(window.getWidth()/2 - logo.width/2);
        logo.setY(window.getHeight()/2 - logo.height/2);
        musica = new Sound("menu/ost/1.wav");
        musica.play();
        Sprite logo2 = new Sprite("menu/globo-tv-logo.png");
        logo2.setX(window.getWidth()/2 - logo2.width/2);
        logo2.setY(window.getHeight()/2 - logo2.height/2);
        inicio = System.currentTimeMillis();
        fim = System.currentTimeMillis();
        double cont = 0;
        orgprint();
        while(fim - inicio < 12500){
            fim = System.currentTimeMillis();
            if(fim - inicio > 4500){
                if(logo.getX() < window.getWidth() - logo.width)logo.setX(logo.getX() + 0.05);
                if(logo.getY() < window.getHeight() - logo.height)logo.setY(logo.getY()+ 0.05);
                if(!(imprimiveis.contains(logo)))imprimiveis.add(logo);
            }
            if(fim - inicio > 7000){
                if(!(imprimiveis.contains(logo2)))imprimiveis.add(logo2);
            }
            imprime();
            window.update();
            if(mouse.isLeftButtonPressed()){
                throw new Exception();
            }
        }
    }

    private void abertura2()throws Exception {
        musica = new Sound("menu/ost/2.wav");
        musica.play();
        fundo = new Sprite("menu/download.png");
        orgprint();
        long inicio = System.currentTimeMillis();
        long fim = System.currentTimeMillis();
        double cont = 0;
        int t = 0;
        float fracio = 0;
        while(fim - inicio < 13000){
            fim = System.currentTimeMillis();
            fundo.draw();
            for(Sprite i: imprimiveis) i.draw();
            window.drawText("dos alunos de Poo", window.getWidth()/2-20, 10, Color.white);
            if(fim - inicio > 1500){
                window.drawText("O trabalho que revolucionou vidas", window.getWidth()/2-20, 40, Color.white);
            }
            if(fim - inicio > 3500){
                window.drawText("Apresentado por ninguem menos que:", window.getWidth()/2-20, 70, Color.white);
            }
            if(fim - inicio > 6000){
                window.drawText("Joao pedro de Almeida", window.getWidth()/2-20, 100, Color.white);
            }
            if(fim - inicio > 9000){
                window.drawText("vem ai o imperdivel", window.getWidth()/2-20, 130 + t , Color.white);
                fracio = fracio + 0.05f;
                t = (int)fracio;
            }if(mouse.isLeftButtonPressed()){
                throw new Exception();
            }

            window.update();
        }
    }

    private void menufinal(){
        fundo = new Sprite("menu/espaco.jpg");
        orgprint();
        Sprite logo = new Sprite("menu/logo.png");
        Sprite lanky = new Sprite("menu/lanky.png");
        imprimiveis.add(logo);
        imprimiveis.add(lanky);
        int opcaoEscolhida = 0;
        lanky.setX(window.getWidth());
        lanky.setY(window.getHeight()- lanky.height);
        long inicio = System.currentTimeMillis();
        long fim = System.currentTimeMillis();
        logo.setX(window.getWidth()/2 - logo.width/2);
        logo.setY(-logo.height);
        boolean control = true;
        while(true){
            fim = System.currentTimeMillis();
            if(logo.getY() < logo.height/8){
                logo.setY(logo.getY() + 0.03);
            }
            else if(lanky.getX() > window.getWidth()*5/10) lanky.setX(lanky.getX() - 0.03);
            int delay = 1;
            imprime();
            if(fim - inicio > 8000){
                if(keyboard.keyDown(Keyboard.ESCAPE_KEY))descarregarObjetos();
                if(keyboard.keyDown(Keyboard.ENTER_KEY)) {
                    if(control){
                        musica.pause();
                        return;
                    }
                    descarregarObjetos();
                    return;
                }
                if (delay > 0) {
                    delay = delay - (int) window.deltaTime();
                }
                if(control){
                    window.drawText("COMECAR JOGO", 100, 370, Color.YELLOW);
                    window.drawText("SAIR", 100, 420, Color.WHITE);
                }
                else{
                    window.drawText("COMECAR JOGO", 100, 370, Color.WHITE);
                    window.drawText("SAIR", 100, 420, Color.YELLOW);
                }
            }
            if((delay <= 0)  && (keyboard.keyDown(Keyboard.DOWN_KEY) || keyboard.keyDown(Keyboard.UP_KEY))){
                control = !control;
                delay = 1000;
            }


            window.update();
        }
    }

}
