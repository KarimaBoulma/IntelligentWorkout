package p8.boulma.sancho.intelligentworkout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Mathilde on 23/12/2016.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    SurfaceHolder holder;
    private Resources mRes;
    private Context mContext;
    private boolean in=true;
    Paint paint;
    // tableau modelisant la miniature du jeu
    int [][] miniature;

    // ancres pour pouvoir centrer la carte du jeu
    int        carteTopAnchor;                   // coordonn�es en Y du point d'ancrage de notre carte
    int        carteLeftAnchor;                  // coordonn�es en X du point d'ancrage de notre carte

    // taille de la carte fixe pour ce jeu
    static  int    carteWidth  = 5;
    static  int    carteHeight  =5;
    static  int    carteTileSize =20;

    //image
    private Bitmap block_b;
    private Bitmap block_r;
    private Bitmap win;

    // tableau representant la miniature
    int [][] ref  ;
    // position de reference des block Rouges
    int [][] refBred  ;

    //Thread pour le timer
   // private Thread time_thread;
    private Thread cv_thread;


    // Declaration Objet RefNiveau
    private RefLevel refLevel;

    //Numero du niveau
    private int level=1;

    public GameView(Context context, AttributeSet attrs) {

        super(context,attrs);

        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
        holder = getHolder();
        holder.addCallback(this);

        //chargement des images
        mContext=context;
        mRes = mContext.getResources();
        block_b= BitmapFactory.decodeResource(mRes,R.drawable.blue);
        block_r= BitmapFactory.decodeResource(mRes,R.drawable.red);
        win = BitmapFactory.decodeResource(mRes,R.drawable.win);

        initparameters();
        //creation du thread pour le timer
        //time_thread = new Thread(this);
        cv_thread=new Thread(this);
        //prise de focus pour la gestion des touches
        setFocusable(true);


    }
    //Chargement du niveau a partir du tableau de reference des niveau
    private void loadlevel(){
        refLevel=new RefLevel(mContext.getResources().openRawResource(R.raw.levels),level);
        miniature= refLevel.getRef();

    }
    public void initparameters(){
        paint = new Paint();
        paint.setColor(0xff0000);

        paint.setDither(true);
        paint.setColor(0xFFFFFF00);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setTextAlign(Paint.Align.LEFT);
        miniature=new int [carteHeight][carteWidth];
        loadlevel();
        carteTopAnchor  = 10;/*(getHeight()- carteHeight*carteTileSize)/2;*/
        carteLeftAnchor = (getWidth()- carteWidth*carteTileSize)/2; //CENTRER LA MINIATURE au milieu horizontalement
        if ((cv_thread!=null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");
        }

    }
    // Dessiner la miniature du jeu
    private void paintMiniature(Canvas canvas) {
        for (int i=0; i< carteHeight; i++) {
            for (int j=0; j< carteWidth; j++) {
                switch (miniature[i][j]) {
                    case 0:
                        canvas.drawBitmap(block_b, carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                    case 1:
                        canvas.drawBitmap(block_r,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                }
            }
        }
    }

    // permet d'identifier si la partie est gagnee
    private boolean isWon() {

        return false;
    }
    // dessin du gagne si gagne
    private void paintWin(Canvas canvas) {
        canvas.drawBitmap(win, carteLeftAnchor+ 3*carteTileSize, carteTopAnchor+ 4*carteTileSize, null);
    }

    private void nDraw(Canvas canvas){
        canvas.drawRGB(44,44,44);
        if(isWon()){
            paintWin(canvas);
        }else {
            paintMiniature(canvas);
        }
    }
    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("-> FCT <-", "surfaceChanged "+ width +" - "+ height);
        initparameters();
    }
    public void surfaceCreated(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceCreated");
    }


    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceDestroyed");
    }
    public void run() {
        Canvas c = null;
        while (true) {
            try {
               // time_thread.sleep(40);
                try {
                    c = holder.lockCanvas(null);
                    nDraw(c);
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch(Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN");
            }
        }

    }



}
