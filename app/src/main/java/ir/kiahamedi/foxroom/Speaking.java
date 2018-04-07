package ir.kiahamedi.foxroom;


import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.kiahamedi.foxroom.others.Message;
import ir.kiahamedi.foxroom.others.Utils;
import ir.kiahamedi.foxroom.others.WsConfig;

public class Speaking extends AppCompatActivity {


    private WebSocket clinte = null;

    private EditText etMsg;

    private MessagesListAdapter adapter;
    private List<Message> all_messages;
    private ListView lvChat;
    private Utils myUtils;
    private String name=null;
    private static final String TAG_SELF = "self";
    private static final String TAG_NEW = "new";
    private static final String TAG_EXIT = "exit";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking);



        etMsg = (EditText) findViewById(R.id.inputMsg);
        lvChat = (ListView) findViewById(R.id.list_view_messages);

        myUtils = new Utils(getApplicationContext());

        name = getIntent().getExtras().getString("name");
        all_messages = new ArrayList<Message>();
        adapter = new MessagesListAdapter(this,all_messages);
        lvChat.setAdapter(adapter);
        AsyncHttpClient.getDefaultInstance().websocket(

                WsConfig.WEB_SOCKET_URL+name,null,new AsyncHttpClient.WebSocketConnectCallback()
                {
                    @Override
                    public void onCompleted (Exception ex,WebSocket websocket)
                    {
                        if (ex != null)
                        {
                            showTost(ex.toString());
                            return;
                        }

                        clinte=websocket;
                        clinte.setStringCallback(
                                new WebSocket.StringCallback() {
                                    @Override
                                    public void onStringAvailable(String s) {
                                        parsemessage(s);
                                    }
                                }
                        );

                        clinte.setDataCallback(
                                new DataCallback() {
                                    @Override
                                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                                        parsemessage(
                                                ByteToHex(bb.getAllByteArray())
                                        );
                                    }
                                }
                        );


                        CompletedCallback disconnect = new CompletedCallback() {
                            @Override
                            public void onCompleted(Exception ex) {
                                String msg = "Disconnected! reason ->" + ex.toString();
                                showTost(msg);
                                myUtils.storeSessionId(null);
                            }
                        };

                        clinte.setClosedCallback(disconnect);

                        clinte.setEndCallback(disconnect);

                    }
                }

        );

    }



    public void onBtnSendClick(View v)
    {

        sendMessageToServer(myUtils.getSendMessageJson(etMsg.getText().toString()));
    }

    public void sendMessageToServer(String msg){

        if (clinte != null && clinte.isOpen())
        {
            clinte.send(msg);
        }

    }

    public void showTost(final String text)
    {


        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),text,Toast.LENGTH_LONG).show();
                    }
                }
        );

    }






    public void parsemessage(final String str)
    {

        try{

            JSONObject jObj = new JSONObject(str);
            String flag = jObj.getString("flag");
            if(flag.equalsIgnoreCase(TAG_SELF))
            {
                myUtils.storeSessionId(jObj.getString("sessionId"));

            }else if(flag.equalsIgnoreCase(TAG_NEW))
            {
                showTost(jObj.getString("name")+" "+jObj.getString("message")+". Online Peoples: "+jObj.getString("onlineCount"));
            }else if(flag.equalsIgnoreCase(TAG_EXIT))
            {
                showTost(jObj.getString("name")+" "+jObj.getString("message"));
            }else if(flag.equalsIgnoreCase(TAG_MESSAGE))
            {
                String fromName = name;
                String message = jObj.getString("message");
                String sessionId = jObj.getString("sessionId");
                boolean isSelf = true;
                if(! sessionId.equals(myUtils.getSessionId()))
                {
                    fromName=jObj.getString("name");
                    isSelf=false;
                }

                Message m = new Message(fromName,message,isSelf);
                appendmessage(m);
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void appendmessage(final Message m)
    {
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        all_messages.add(m);
                        adapter.notifyDataSetChanged();
                        playBeep();
                        etMsg.setText("");
                    }
                }
        );
    }

    public String ByteToHex(byte[] data)
    {
        char[] hexArray = "123456789ABCDEF".toCharArray();
        char[] hexChars =  new char[data.length*2];
        for (int i=0;i<data.length;i++)
        {
            int v = data[i] & 0xFF;
            hexChars[i*2] = hexArray [v >>> 4];
            hexChars[i*2+1]=hexArray[v & 0x0F];

        }

        return new String(hexChars);
    }

    public void playBeep()
    {

        try{


            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),notification);


            r.play();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}