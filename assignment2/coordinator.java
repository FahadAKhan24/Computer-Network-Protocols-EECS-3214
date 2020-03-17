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

 
/*
FAHAD AHMED KHAN
214468888

TERMINAL COMMANDS USED (in order) :

on nodes/clients: java node 28888   (gsp08)
                  java node 38888   (gsp09)
                  java node 48888   (gsp10)

on server (gsp06): java server 58888                  

on coordinator(gsp07): java coordinator gsp08 28888
                       java coordinator gsp09 38888
                       java coordinator gsp10 48888

                       
ERRORS:

node terminal publishes when the connection closes :
"Exception caught when tryingt to listen on port
connection reset"

IMPROVEMENTS:

1. keep connection alive for a longer period of time then currently happening.


*/
import java.io.*;
import java.net.*;

public class coordinator {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java coordinator <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket CoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(CoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(CoSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            
            String fromNode;
            String fromUser;
          
            //------------ basic initial communication for confirming connection     
          //  fromUser = stdIn.readLine();
            //    if (fromUser != null) {
              //      System.out.println("Cordinator: " + fromUser);
                //      out.println(fromUser); 
                //} 
           
                
                // coordinator communicates to nodes to connect ("attack") 
            out.println("connect to server");
            while ((fromNode = in.readLine()) != null) {
                System.out.println("Node: " + fromNode);
           
            } 
            
                     
                
            
         }   catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        }   catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}