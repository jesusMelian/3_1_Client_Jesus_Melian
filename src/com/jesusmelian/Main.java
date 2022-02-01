package com.jesusmelian;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

        Socket socket = new Socket("localhost", PORT);

        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());


        while(seguir) {
            try {
                if(first) {
                    System.out.println("INTRODUZCA EL NOMBRE DE USUARIO: ");
                    String user = sc.nextLine();
                    oos.writeObject(user);
                    first = false;
                }else{
                    System.out.println("INTRODUZCA EL MENSAJE: debe comenzar con'message:'");
                }


                String userName = sc.nextLine();
                oos.writeObject(userName);
                String returnValue = (String) ois.readObject();
                System.out.println("El servidor responde: " + returnValue);
                if (returnValue.equalsIgnoreCase("GoodBye")) {
                    if (oos != null) oos.close();
                    if (ois != null) ois.close();
                    if (socket != null) socket.close();
                    seguir = false;
                    System.out.println("Conexión cerrada!");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
