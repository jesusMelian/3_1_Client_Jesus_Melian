package com.jesusmelian;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        final int PORT = 8080;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        boolean first = true;
        Scanner sc = new Scanner(System.in);
        String listMsg = null;
        String user = null;
        String returnValue = null;

        Socket socket = new Socket("localhost", PORT);

        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());



            try {

                    System.out.println("INTRODUZCA EL NOMBRE DE USUARIO: ");
                    user = sc.nextLine();
                    if (user != null) {
                        oos.writeObject(user);
                        listMsg = (String) ois.readObject();
                    }

                    if(listMsg != null) {
                        System.out.println("-----------------------------------------------------------");
                        System.out.println(listMsg);
                        System.out.println("-----------------------------------------------------------");

                    }

                    String msg = "";

                    //BUCLE HASTA QUE SE PONGA BYE
                    while(!msg.equals("bye")){
                        if(user != null) {

                            System.out.println("INTRODUZCA EL MENSAJE: debe comenzar con'message:'");
                            msg = sc.nextLine();
                            oos.writeObject(msg);
                            //Leo lo que obtengo del servidor
                            listMsg = (String) ois.readObject();

                            if(listMsg != null) {
                                System.out.println("-----------------------------------------------------------");
                                System.out.println(listMsg);
                                System.out.println("-----------------------------------------------------------");
                            }
                        }
                    }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                if (oos != null) oos.close();
                if (ois != null) ois.close();
                if (socket != null) socket.close();
                System.out.println("Conexión cerrada!");

            }

    }
}
