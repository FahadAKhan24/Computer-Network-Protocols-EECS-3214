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
import java.util.*;
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
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in)) ; 
            
        ) {
            String confirmation;
            String studentAnswer;
            String clientPortNumber;
            String serverTime;
            String userInput;
            ArrayList<String> answers = new ArrayList<String>();
         
         
            
          // process outputLine : get current time of server. first output sent.
          
          Calendar cal1 = Calendar.getInstance();
          SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
          serverTime= sdf1.format(cal1.getTime());
          System.out.println( serverTime );
            
         // server log file set up
         
          FileWriter fileWriter = new FileWriter("server.log", true);
          PrintWriter printWriter = new PrintWriter(fileWriter);
        
          
          
         /*  GET ALL THE DATA FROM CLIENT AND STORE IT. 
                    Student_id = clientPortNumber
                    store all the answers in String array . index of array will represent question numbers.
         */     
              confirmation= in.readLine();
              System.out.println("Node/client: " + confirmation); 
              
              clientPortNumber = in.readLine();                         // client sent it's port number here.
              System.out.println("Node/client: " + clientPortNumber);  // print what the client/node said.
              
              out.println("connected");                      // sent that to client/node for connection confirmation
              
              
              
              while ((studentAnswer= in.readLine()) != null) {
                     
              	     System.out.println(" answer node sent: " + studentAnswer);  // just a checking measure. may remove later
                     answers.add(studentAnswer);
                     
                  //   userInput = stdIn.readLine();  // instructur typing close to end class and takes no more answers
                  //   if (userInput.equals("close"))   // this is causing problems because stdin.readLine waits for an input before going to next loop(i.e question)
                   //  break;
               }
             
             
           /*   WRITE DATA TO SERVER.LOG  
                        (doing this at the end will ensure data from one student is written in one go into the server.log file)  
                         use loops                                        
           */        
                 
                    printWriter.printf("Student %s  : Join time : %s \n", clientPortNumber , serverTime);  // clientPortnumber  = inputLine  {for now}
                    
                        
                    for (int i=0; i<answers.size(); i++){ 
                           printWriter.printf(" q%d: %s \n " , (i+1) , answers.get(i));
                    }
                    printWriter.close(); 
         
                    
           // CLOSING THE CONNECTION 
       /*        userInput = stdIn.readLine();
                if (userInput.equals("close")) {  
                socket.close();
                out.println("if this goes, connection isn't closed yet");
                }
         */       
            //}
            socket.close();
        
//----------------------- exceptions -------------------------        
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

.
