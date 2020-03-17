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

 
 /*
FAHAD AHMED KHAN
214468888

TERMINAL COMMANDS USED (in order) :

on server (gsp04): java server 58888 

on nodes/clients: java node gsp04 58888   (gsp08)
                  java node gsp04 58888   (gsp09)
                  java node gsp04 58888   (gsp10)
                
                  
HOW UI WORKS:

The student is promoted to give answers one at a time.
The professor "next" into the terminal after every question.
When there are no more questions, professor types in "close" to close socket and output the log.

ERRORS:

1. I have commented out the "close" implementation for now because it was producing 2 bugs when run with more than one node.
   first bug: only the most recently connected node was being written on server.log
   second bug/inconvenience: the professor would have to type next for each student which doesn't makes sense practically.  

2. When the code is run with one node and one server, prof. typing in "close" closes the socket but for some reason
   data is written unto server.log only when ctrlC is typed into the node terminal.
 

*/
import java.net.*;
import java.io.*;
import java.net.InetAddress;

public class node {
    public static void main(String[] args) throws IOException {
        
     
        
        if (args.length != 2) {  // these two contradict???
            System.err.println(
                "Usage: java node <host name> <port number>");
            System.exit(1);
        }
      
      
 //----------------------------------------------------------------------------       
       
        String ServerHostname = args[0];
        int ServerPortNumber = Integer.parseInt(args[1]);;

        try (
                
             // socket to Server , node acts as a client
            Socket NclientSocket = new Socket(ServerHostname,ServerPortNumber );
             
            PrintWriter out2 =
                new PrintWriter(NclientSocket.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(
                new InputStreamReader(NclientSocket.getInputStream()));
            
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
           
           
        ) {
        
            String inputLine ;
            String fromServer; 
            String userInput;
           // String command = "/bin/bash -c java node gsp32 58888";
            //String portnumberID = Integer.toString(NodePortNumber);
            
            // get node's name 
            
            String studentName = System.getProperty("user.name");
          
            
  
   
                	        
            //---------------------- NODE INTERACTION WITH SERVER-----------------------         
                        
                        
                         out2.println("connected");       // connection confirmation sent to Server
                         out2.println(studentName);   // SEND SERVER user name logged in AS A STUDENT ID/NUMBER 
                         
                         System.out.println("server said: " + in2.readLine());   // connection confirmation sent by Server
                         
                       /*   while ((fromServer = in2.readLine()) != null) {  // read what the Server said. 
                         System.out.println("Server: " + fromServer);     // print it
                         out2.println("2");          // exchange strings with server to keep the connection open
                         out2.println("4");
                         } */
                         
                         
                         System.out.println("give your answer(A-E) to question 1 : ");
                                     
                          while ((userInput = stdIn.readLine()) != null) {
                          	  
                          out2.println(userInput);
                          System.out.println("answer given:" + userInput);
                          System.out.println("give your answer(A-E) to next question : ");
                          }
                    
                    
                  //  System.out.println("server said: " + in2.readLine());
                    
                    
                    
                    
                    
                    
                  //  }
         
           //   } 
                
 //---------------------------------------EXCEPTIONS-----------------       
         } 
          
          catch (UnknownHostException e) {
            System.err.println("Don't know about host " + ServerHostname);
            System.exit(1);
          } 
          catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                ServerHostname);
            System.exit(1);
          } 
          
    }
}