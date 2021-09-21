package sample;



public abstract class Filter  implements Runnable{
 
    Pipe _dataINPipe;
    Pipe _dataOUTPipe;
     
    public Request getData(){
        return _dataINPipe.dataOUT();
    }
     
    public void sendData(Request tempData){
        _dataOUTPipe.dataIN(tempData);
    }
    abstract void execute();



}
 
