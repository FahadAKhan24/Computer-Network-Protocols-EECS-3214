/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
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

public class node {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
            System.err.println("Usage: java node <port number>");
            System.exit(1);
        }
        
      /*  if (args.length != 2) {  // these two contradict???
            System.err.println(
                "Usage: java node <host name> <port number>");
            System.exit(1);
        }
      */
      
 //----------------------------------------------------------------------------       
        int NodePortNumber = Integer.parseInt(args[0]);
        String ServerHostname = "gsp06";
        int ServerPortNumber = 58888;

        try (
             // socket to coordinator , node acts a server
            ServerSocket NserverSocket = new ServerSocket(NodePortNumber);
            Socket coordinatorSocket = NserverSocket.accept();
            PrintWriter out1 =
                new PrintWriter(coordinatorSocket.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(
                new InputStreamReader(coordinatorSocket.getInputStream()));
                
             // socket to Server , node acts as a client
            Socket NclientSocket = new Socket(ServerHostname,ServerPortNumber );
             
            PrintWriter out2 =
                new PrintWriter(NclientSocket.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(
                new InputStreamReader(NclientSocket.getInputStream()));
            
          /*  BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
           */
           
        ) {
        
            String inputLine ;
            String fromServer; 
            String command = "/bin/bash -c java node gsp06 58888";
            String portnumberID = Integer.toString(NodePortNumber);
          
            
  
            // ACTING AS A SERVER  , client is Coordinator
             while ((inputLine = in1.readLine()) != null) {  // READING INPUT FROM COORDINATOR
          //     out1.println(inputLine);                      // send it back to coordinator for confirmation
            
               
         // ACTING AS A CLIENT , server is Server     
                    if (inputLine.equals("connect to server")) {
                        // on terminal this should happen at this point: "java node serverhostname serverport#"  
                        out1.println("ok.will do");
                        Process proc = Runtime.getRuntime().exec(command);
                	        
                       // out2.println("connected");                                  // send Server something.
                          out2.println(portnumberID);
                         
                          while ((fromServer = in2.readLine()) != null) {  // read what the Server said. 
                         System.out.println("Server: " + fromServer);     // print it
                         out2.println("2");          // exchange strings with server to keep the connection open
                         out2.println("4");
                         }
                         
                    }
         
              } 
           }     
 //---------------------------------------EXCEPTIONS-----------------       
          catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + NodePortNumber + " or listening for a connection");
            System.out.println(e.getMessage());
          }
          
     /*     catch (UnknownHostException e) {
            System.err.println("Don't know about host " + ServerHostname);
            System.exit(1);
          } 
          catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                ServerHostname);
            System.exit(1);
          } 
          */
    }
}