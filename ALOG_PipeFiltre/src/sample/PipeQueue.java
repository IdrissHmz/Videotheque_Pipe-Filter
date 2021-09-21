package sample;

import java.util.LinkedList;
import java.util.Queue;

public class PipeQueue extends Pipe {
   
 Queue<Request> _inData = new LinkedList<Request>();
 
 public void dataIN (Request in){
     _inData.add(in);
 }
     
 public Request dataOUT (){
      return _inData.poll();
 }
	 
}
	
	
