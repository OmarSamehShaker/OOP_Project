package com.example.myjavafx;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket socket;
    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientName;

    public ClientHandler(Socket socket) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientName = bufferedReader.readLine();
            clients.add(this);
            broadCastMessage("SERVER: "+ clientName + " Has Joined The Chat....");
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }

    }
    @Override
    public void run() {
        String message;

        while(socket.isConnected()) {
            try {
                message = bufferedReader.readLine();
                broadCastMessage(message);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadCastMessage(String message) {
        for (ClientHandler clientHandler : clients) {
            try{
                if(!clientHandler.clientName.equals(clientName)) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }

    public void removeClientHandler(ClientHandler client) {
        clients.remove(this);
        System.out.println("User: " + clientName + " has left the chat");

    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler(this);
        try{
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket != null) {
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
