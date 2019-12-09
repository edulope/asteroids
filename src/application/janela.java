package application;

import jplay.*;
import jplay.Window;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class janela{
    protected static Window window;
    protected static Keyboard keyboard;
    protected static Mouse mouse;
    protected static List<Sprite>imprimiveis;
    protected static Sprite fundo;
    protected static Sound musica;
    protected long inicio;
    protected long fim;




    public void inicializa(int x, int y){
        window = null;
        keyboard = null;
        mouse = null;
        imprimiveis = null;
        fundo = null;
        musica = null;
        long inicio = 0;
        long fim = 0;
        window = new Window(x, y);
        keyboard = window.getKeyboard();
        mouse = window.getMouse();
        imprimiveis = new ArrayList<>();
        window.setCursor( window.createCustomCursor("mouse.png") );
    }


    public void imprime(){
        for(Sprite i: imprimiveis) i.draw();

    }

    public static Keyboard getkeyboard(){
        return keyboard;
    }

    public void orgprint(){
        imprimiveis.clear();
        imprimiveis.add(fundo);
    }

    public void descarregarObjetos(){
        window.exit();
        window = null;
        fundo = null;
        keyboard = null;
        mouse =null;
        musica = null;
        imprimiveis.clear();
    }

    public void zera(){
        window = null;
        fundo = null;
        keyboard = null;
        mouse =null;
        musica = null;
    }



    public void executa(){

    }

}
