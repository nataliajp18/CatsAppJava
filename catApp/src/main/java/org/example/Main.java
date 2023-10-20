package org.example;

import org.example.Model.Cats;
import org.example.Service.CatsService;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        int optionMenu = -1;
        String[] buttom = {"1. Ver gatos", "2. Ver favoritos", "3. Salir"};

        do {
            //Menu principal
            String option = (String) JOptionPane.showInputDialog(null, "Gatitos en Java", "Menu principal",
                    JOptionPane.INFORMATION_MESSAGE, null, buttom, buttom[0]);

            //Se valida la opcion que fue seleccionada por el usuario
            for (int i=0; i < buttom.length; i++){
                if(option.equals(buttom[i])){
                    optionMenu = i;
                }
            }

            switch (optionMenu){
                case 0:
                    CatsService.SeeCats();
                case 1:
                    Cats cats = new Cats();
                    CatsService.SeeFavorites(cats.getApikey());
                default:
                    break;
            }
        }while (optionMenu != 1);

    }
}