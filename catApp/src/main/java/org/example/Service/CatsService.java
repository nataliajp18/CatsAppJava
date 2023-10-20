package org.example.Service;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.example.Model.Cats;
import org.example.Model.CatsFav;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService {

    public static void SeeCats() throws IOException {

        //Traer datos de api

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
        Response response = client.newCall(request).execute();

        String json = response.body().string();

        //Se hace para quitar los corchetes de la salida en postman y se pueda leer como un .json
        json = json.substring(1,json.length());
        json = json.substring(0, json.length()-1);

        //Para convertir un objeto desde la clase json (Respuesta de la api la convierta en un objeto tipo gatos)

        Gson gson = new Gson();
        Cats cats = gson.fromJson(json, Cats.class);

        //Para redimensionar la imagen si se requiere

        Image image = null;
        try {
            URL url = new URL(cats.getUrl());
            image = ImageIO.read(url);

            ImageIcon backgroungCat = new ImageIcon(image);

            //Validar ancho de la imagen para hacer redimension

            if (backgroungCat.getIconWidth() > 800){
                Image background = backgroungCat.getImage();
                Image modified = background.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                backgroungCat = new ImageIcon(modified);
            }

            String menu = "Opciones: \n"
                    + "1. Ver otra imagen \n"
                    + "2. Marcar gato como favorito \n"
                    + "3. Volver al menu principal \n";
            String [] buttoms = {"Ver otra imagen", "Favorito", "Volver"};
            String idCat = cats.getId();

            //Crear interfaz grafica
            String option = (String) JOptionPane.showInputDialog(null, menu, idCat, JOptionPane.INFORMATION_MESSAGE, backgroungCat, buttoms,buttoms[0]);

            //Opciones para el usuario
            int selection = -1;

            for (int i = 0; i < buttoms.length; i++){
                if (option.equals(buttoms[i])){
                    selection = i;
                }
            }
            switch (selection){
                case 0:
                    SeeCats();
                    break;
                case 1:
                    favoriteCat(cats);
                    break;
                default:
                    break;
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static void favoriteCat(Cats cats){

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"image_id\":\"" + cats.getId() +"\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", cats.getApikey())
                    .build();
            Response response = client.newCall(request).execute();

        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static void SeeFavorites(String apiKey) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", apiKey)
                .build();
        Response response = client.newCall(request).execute();

        String elJson = response.body().string();

        if(!response.isSuccessful()) {
            response.body().close();
        }

        //Se crea un objeto gson
        Gson gson = new Gson();

        CatsFav[] catsArray = gson.fromJson(elJson, CatsFav[].class);

        if (catsArray.length > 0){
            int min = 1;
            int max = catsArray.length;
            int aleatorio = (int) (Math.random() * ((max-min) + 1)) + min;
            int indice = aleatorio - 1;

            CatsFav catsFav = catsArray[indice];

            Image image = null;
            try{
                URL url = new URL(catsFav.getImage().getUrl());
                image = ImageIO.read(url);

                ImageIcon backgroungCat = new ImageIcon(image);

                //Validar ancho de la imagen para hacer redimension

                if (backgroungCat.getIconWidth() > 800){
                    Image background = backgroungCat.getImage();
                    Image modified = background.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                    backgroungCat = new ImageIcon(modified);
                }

                String menu = "Opciones: \n"
                        + "1. Ver otra imagen \n"
                        + "2. Eliminar favorito \n"
                        + "3. Volver al menu principal \n";
                String [] buttoms = {"Ver otra imagen", " Eliminar Favorito", "Volver"};
                String idCat = catsFav.getId();

                //Crear interfaz grafica
                String option = (String) JOptionPane.showInputDialog(null, menu, idCat, JOptionPane.INFORMATION_MESSAGE, backgroungCat, buttoms,buttoms[0]);

                //Opciones para el usuario
                int selection = -1;

                for (int i = 0; i < buttoms.length; i++){
                    if (option.equals(buttoms[i])){
                        selection = i;
                    }
                }
                switch (selection){
                    case 0:
                        SeeFavorites(apiKey);
                        break;
                    case 1:
                        DeleteFavorite(catsFav);
                        break;
                    default:
                        break;
                }
            }catch (IOException e){
                System.out.println(e);
            }

        }
    }

    public static void DeleteFavorite(CatsFav catsFav){
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/" + catsFav.getId())
                    .delete(null)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", catsFav.getApiKey())
                    .build();

            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()) {
                response.body().close();
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

}
