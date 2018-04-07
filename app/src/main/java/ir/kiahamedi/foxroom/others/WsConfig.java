package ir.kiahamedi.foxroom.others;


public class WsConfig {

    public static final int PORT_NUMBER = 8080;

    /*ip form iwconfig commandline*/
    public static final String HOST_ADDRESE = "192.168.56.1";
    public static final String  WEB_SOCKET_URL = "ws://" + HOST_ADDRESE + ":" + PORT_NUMBER + "/FoxRoomOnWeb/chat?name=";
}
