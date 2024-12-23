package com.example.myjavafx;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Socket socket;
    private String username;
    public Client(Socket socket, String username) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        }catch(Exception e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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

    public void sendMessage() {
        try{
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);

            while(socket.isConnected()) {
               String message = scanner.nextLine();
                bufferedWriter.write(username+": "+message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void ListenerForMessage() {
        new Thread(new Runnable() {
            public void run() {
                String messageChat;
                try {
                    while (socket.isConnected()) {
                        messageChat = bufferedReader.readLine();
                        System.out.println(messageChat);
                    }
                }catch (IOException e){
                    closeEverything(socket,bufferedReader,bufferedWriter);
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Your Username For The Chat: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost",1111);
        Client client = new Client(socket,username);
        client.ListenerForMessage();
        client.sendMessage();
    }
}
