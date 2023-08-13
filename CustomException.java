package pubsubmq; 

public class CustomException extends Exception {

    private static final long serialVersionUID=1L;
    
    String Message;
    public CustomException(String Message)
    {
    	super(Message);
    	this.Message = Message;
    }} 