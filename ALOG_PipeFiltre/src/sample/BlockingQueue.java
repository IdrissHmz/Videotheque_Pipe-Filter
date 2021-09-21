package sample;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueue extends Pipe {
   
 Queue<Request> _inData = new LinkedList<Request>();
 
 public synchronized  void dataIN (Request in){
     _inData.add(in);
     notify(); 
 }

 public synchronized Request dataOUT (){
	 if(_inData.isEmpty())
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      return _inData.poll();
 }
	 
}
	
	
