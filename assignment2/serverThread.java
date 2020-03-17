/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class serverThread extends Thread {
    private Socket socket = null;

    public serverThread(Socket socket) {
        super("serverThread");
        this.socket = socket;
    }
    
    public void run() {

        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine;
            String outputLine1;
            String randomCom1;
            String randomCom2;
           //   String randomCom3;
         
            
          // process outputLine : get current time of server. first output sent.
          
          Calendar cal1 = Calendar.getInstance();
          SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
          outputLine1 = sdf1.format(cal1.getTime());
          System.out.println( outputLine1 );
            
         // server log file set up
         
          FileWriter fileWriter = new FileWriter("server.log", true);
          PrintWriter printWriter = new PrintWriter(fileWriter);

         //   while ((inputLine = in.readLine()) != null) {   // read what client/node sent
              inputLine = in.readLine();                         // client sent it's port number here.
              System.out.println("Node/client: " + inputLine);  // print what the client/node said.
                out.println("connected");                      // sent that to client/node for connection confirmation
                        

                out.println(outputLine1);                // sent client time.
         
           //   write to server.log
                   // printWriter.print("test string\n");
                    printWriter.printf("Connection by client using port number: %s at time : %s \n", inputLine , outputLine1);  // clientPortnumber  = inputLine  {for now}
                    printWriter.close(); // ?? can following out(printwriter) still work after this
         
                    
         // exchange strings with Client to keep  connection alive      
                    out.println("1");
                
                    randomCom1 = in.readLine();                         
                    System.out.println("Node/client: " + randomCom1);
                    
                    out.println("3");
                    
                    randomCom2 = in.readLine();                         
                    System.out.println("Node/client: " + randomCom2);                  
                    
                    out.println("5");
                socket.close();
                out.println("if this goes, connection isn't closed yet");
            //}
           // socket.close();
        
//----------------------- exceptions -------------------------        
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
