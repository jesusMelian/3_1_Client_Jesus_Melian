package com.jesusmelian;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        final int PORT = 8080;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        boolean seguir = true;
        boolean first = true;
        Scanner sc = new Scanner(System.in);
        String[] listMsg = null;
        String user = null;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String returnValue = null;
        System.out.println("Hora actual: " + dateFormat.format(date));

        Socket socket = new Socket("localhost", PORT);

        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());


        while(seguir) {
            try {
                if(first) {
                    System.out.println("INTRODUZCA EL NOMBRE DE USUARIO: ");
                    user = sc.nextLine();
                    if (user != null) {
                        oos.writeObject(user);
                        first = false;
                        listMsg = (String[]) ois.readObject();
                    }
                }else{
                    if(listMsg != null){
                        System.out.println("LISTA DE MENSAJES: ");
                        for(String msgs: listMsg){
                            System.out.println(msgs);
                        }
                        if(user != null) {
                            System.out.println("INTRODUZCA EL MENSAJE: debe comenzar con'message:'");
                            String msg = sc.nextLine();
                            oos.writeObject(msg);
                            //Leo lo que obtengo del servidor
                            returnValue = (String) ois.readObject();

                            //COMPRUEBO SI) FINALIZO
                            if (returnValue.equalsIgnoreCase("GoodBye")) {
                                if (oos != null) oos.close();
                                if (ois != null) ois.close();
                                if (socket != null) socket.close();
                                seguir = false;
                                System.out.println("Conexión cerrada!");
                            }else{
                                //AÑADO AL ARRAY DE STRING EL MENSAJE INTRODUCIDO
                                listMsg[listMsg.length-1]="<"+user+">" + " : [" + date +"]"+ "<"+returnValue+">";
                                System.out.println("El servidor responde: " + returnValue);
                                System.out.println("ALMACENO: "+listMsg[listMsg.length-1]);
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
