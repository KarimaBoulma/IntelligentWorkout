package p8.boulma.sancho.intelligentworkout;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SANCHO Mathilde on 20/12/2016.
 */
public class MenuActivity extends AppCompatActivity {

    private MediaPlayer mPlayer =null;
    final ArrayList SelectedParams =new ArrayList();
    final boolean[] checkedParams = new boolean[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }
    public void goOnActivity(View v, boolean isGame){
        Intent intent;
        if(isGame){
            intent = new Intent(this,GameActivity.class);

        }else {
            intent = new Intent(this, ScoreActivity.class);
        }
        startActivity(intent);
    }

    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.buttonExit:
                finish();
                System.exit(0);
                break;
            case R.id.buttonInfo:
                showPopUpNames();
                break;
            case R.id.buttonParam:
                showPopUpParam();
                break;
            case R.id.buttonPlay:
                goOnActivity(v,true);
                break;
            case R.id.buttonRes:
                goOnActivity(v,true);
                break;
            default :
                goOnActivity(v,false);


        }
    }

    public void showPopUpNames(){
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Informations");
        helpBuilder.setMessage("App by BOULMA Karima and SANCHO Mathilde");
        helpBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
           public void onClick(DialogInterface dialog,int which){
               // Ne fais rien mais ferme la fenêtre (Dialog)

           }
        });
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();

    }
    public void showPopUpParam(){

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Settings");
        helpBuilder.setMultiChoiceItems(R.array.Params,checkedParams,new DialogInterface.OnMultiChoiceClickListener(){
           @Override
            public void onClick(DialogInterface dialog,int which,boolean isChecked){
               if(isChecked){
                       SelectedParams.add(which);
               }else if(SelectedParams.contains(which)){
                   SelectedParams.remove(Integer.valueOf(which));
               }
           }
        });

        helpBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                //sauvegarde des paramètres
                System.out.println(SelectedParams.toString());
            }
        });
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();

    }
    private void appSound(int resId){
        if (mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer=MediaPlayer.create(this,resId);
        mPlayer.start();
    }
}
