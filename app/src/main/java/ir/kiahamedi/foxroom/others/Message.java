package ir.kiahamedi.foxroom.others;


public class Message {


    private String fromName,message;
    private boolean isSelf;



    public Message()
    {

    }


    public Message (String fromName,String message,boolean isSelf)
    {
        this.fromName=fromName;
        this.message=message;
        this.isSelf=isSelf;
    }


    public String getFromName(){


        return this.fromName;
    }


    public void setFromName(String froName)
    {

        this.fromName=froName;

    }

    public String getMessage()
    {
        return message;
    }


    public void setMessage(String message)
    {
        this.message=message;
    }


    public boolean isSelf()
    {
     return isSelf;
    }

    public void setSelf(boolean isSelf)
    {
        this.isSelf=isSelf;
    }
}
