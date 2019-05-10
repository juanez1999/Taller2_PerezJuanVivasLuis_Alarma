package com.juan.perez.alarma;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;

public class Comunicacion extends Observable implements Runnable {

        DatagramSocket socket;
        InetAddress address;
        boolean conectado;

        Comunicacion() {
            conectado = false;
        }



        @Override
        public void run() {
            if(!conectado){
                try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName("172.30.200.14");
                    conectado = true;
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                while (true) {
                    try {
                        recibir();
                        Thread.sleep(33);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void recibir() throws IOException{ //aqui también se notifica el cambio----------------------
            byte[] buffer = new byte[1000];
            DatagramPacket datagramaRespuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(datagramaRespuesta);
            String mensaje = new String(datagramaRespuesta.getData(), 0 , datagramaRespuesta.getLength());
            setChanged();
            notifyObservers(mensaje);
            clearChanged();
            System.out.println("MENSAJE RECIBIDO: " + new String(datagramaRespuesta.getData()));
        }

        public void enviar(String msj){
            new Thread(()->{
                try {
                    String mensaje = msj;
                    DatagramPacket datagrama = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, address, 5000);
                    socket.send(datagrama);
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }


    }

