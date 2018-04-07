package ir.kiahamedi.foxroom.others;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class Utils {

    private Context context;
    private SharedPreferences shaderpref;


    private static int KET_MODE_PRIVATE = 0;
    private static String KEY_SHARED_PREF = "ANDROID_WEB_CHAT";
    private static String KEY_SESSION_ID = "sessionId";
    private static String FLAG_MESSAGE = "message";



    public Utils(Context t)
    {
        this.context=t;
        shaderpref=this.context.getSharedPreferences(KEY_SHARED_PREF,KET_MODE_PRIVATE);

    }


    public void storeSessionId(String sessionId)
    {
        SharedPreferences.Editor editor=shaderpref.edit();
        editor.putString(KEY_SESSION_ID,sessionId);
        editor.apply();
    }

    public String getSessionId()
    {
        return shaderpref.getString(KEY_SESSION_ID,null);
    }


    public String getSendMessageJson(String message)
    {

        String json=null;

        try {

            JSONObject jObj = new JSONObject();
            jObj.put("flag",FLAG_MESSAGE);
            jObj.put("sessionId",getSessionId());
            jObj.put("message",message);

            json=jObj.toString();

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return json;
    }
}
