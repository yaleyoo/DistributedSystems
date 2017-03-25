package client;

import java.net.*;
import java.io.*;

public class Main {

    public static void main(String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        String[] port = new String[100];
        try {
            port[0] = "";
            //port[1] = "sunrise.cis.unimelb.edu.au";
            port[1] = "SKY-20170223MSP";
            int serverPort = 7899;
            s = new Socket(port[1], serverPort);
            System.out.println("Connection Established");
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            System.out.println("Please enter command:");
            
            //get string from typing
            InputStream input = System.in;
            InputStreamReader inputReader = new InputStreamReader(input);
            BufferedReader bf = new BufferedReader(inputReader);
            String msg = null;
            try {
                msg = bf.readLine(); //读取一行信息
            } catch (IOException e) {
                e.printStackTrace();
            } finally { //关闭IO流
                bf.close();
                inputReader.close();
                input.close();
            }
            //return msg
            TransferToJSON transferToJSON = new TransferToJSON();
            msg = transferToJSON.TransferToJSON(msg);
            
            out.writeUTF(msg);     // UTF is a string encoding see Sn. 4.4
            System.out.println("Sending data");
            String data = in.readUTF();   // read a line of data from the stream
            System.out.println("Received: " + data);
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("==================DS Project=======================");
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }

    }
}
