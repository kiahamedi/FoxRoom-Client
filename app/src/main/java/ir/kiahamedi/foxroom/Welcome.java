package ir.kiahamedi.foxroom;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/g.ttf");
        TextView txtlogo = (TextView) findViewById(R.id.txtlogo);
        txtlogo.setTypeface(typeface);
    }



    public void onBtnJoinClick(View v)
    {
        EditText etName =(EditText) findViewById(R.id.etName);
        if (etName.getText().toString().length()>0)
        {
            String name=etName.getText().toString().trim();
            Intent i=new Intent(this,Speaking.class);
            i.putExtra("name",name);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,R.string.name_error,Toast.LENGTH_SHORT).show();
        }
    }
}
