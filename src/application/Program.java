/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import jplay.Window;

import javax.swing.*;
import java.awt.*;


public class Program {
    public static void main(String[] args){
        janela j = new janela();
        JFrame inst = new JFrame();
        inst.setSize(400, 400);
        String texto = "Bem vindo ao Asteroids 2000\ndesenvolvido por: Joao Pedro de Almeida Oliveira Costa\nclique na tela para pular introducao\nna tela de warning, segure <ESQ> para modo estatico e <DIR> para modo super\n\n\n\nno jogo:\n <DIR><ESQ>rotacionam a nave\n<UP>acelera a nave\n <DOWN>desacelera nave\n <SPACEBAR>para atirar" +
                "\n\naperte <ENTER> para selecionar opcao e <ESQ> para pausar";
        JTextArea text = new JTextArea(texto);

        inst.add(text);
        inst.pack();
        inst.setVisible(true);
        j.zera();
        j.inicializa(800, 600);

        while(true){
        j = new menu();
        j.executa();
        j = new level();
        j.executa();}

    }
           
}
