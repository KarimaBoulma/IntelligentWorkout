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

import java.util.Random;

/**
 * Created by Mathilde on 23/12/2016.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    SurfaceHolder holder;
    private Resources mRes;
    private Context mContext;
    private boolean in;
    Paint paint;
    // tableau modelisant la miniature du jeu
    int [][] miniature;

    // ancres pour pouvoir centrer la miniature du jeu
    int        miniTopAnchor;                   // coordonn�es en Y du point d'ancrage de notre carte
    int        miniLeftAnchor;                  // coordonn�es en X du point d'ancrage de notre carte
    // ancres pour pouvoir centrer la grille du jeu
    int        gridTopAnchor;                   // coordonn�es en Y du point d'ancrage de notre carte
    int        gridLeftAnchor;                  // coordonn�es en X du point d'ancrage de notre carte

    // taille de la matrice fixe pour ce jeu
    static  int    matrixWidth  = 5;
    static  int    matrixHeight  =5;
    static  int    gridTileSize = 120;
    static  int    miniTileSize =40;

    //image
    private Bitmap mblock_b;
    private Bitmap mblock_r;
    private Bitmap gblock_b;
    private Bitmap gblock_r;
    private Bitmap win;

    // tableau representant la grille du jeu
    int [][] grid  ;

    //nombre de block rouge
    int nBred;
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
        mblock_b= BitmapFactory.decodeResource(mRes,R.drawable.blue);
        mblock_r= BitmapFactory.decodeResource(mRes,R.drawable.red);
        gblock_b= BitmapFactory.decodeResource(mRes,R.drawable.gblue);
        gblock_r= BitmapFactory.decodeResource(mRes,R.drawable.gred);
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
        nBred=refLevel.getnBRed();

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
        miniature=new int [matrixHeight][matrixWidth];
        grid=new int [matrixHeight][matrixWidth];
        loadlevel();
        createGridAleatoire();
    }
    // Dessiner la miniature du jeu
    private void paintMiniature(Canvas canvas) {
        for (int i=0; i< matrixHeight; i++) {
            for (int j=0; j< matrixWidth; j++) {
                switch (miniature[i][j]) {
                    case 0:
                        canvas.drawBitmap(mblock_b, miniLeftAnchor+ i*miniTileSize, miniTopAnchor+ j*miniTileSize, paint);
                        break;
                    case 1:
                        canvas.drawBitmap(mblock_r,miniLeftAnchor+ i*miniTileSize, miniTopAnchor+ j*miniTileSize, paint);
                        break;
                }
            }
        }
    }
    // Dessine la barre d'affichage du timer
    private void paintInfo(Canvas canvas) {
      //  canvas.drawBitmap(block_b, carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);

    }

    // Cree la grille aléatoire du jeu
    private void createGridAleatoire(){
        int nbr=0;
        while(nbr!=nBred){
            for (int i = 0; i < matrixHeight; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (nbr != nBred) {
                        Random r = new Random();
                        int n = r.nextInt(2);
                        if (n == 1) {
                            grid[i][j] = n;
                            nbr++;
                        }
                    }

                }
            }
        }
    }
    // Dessine la grille aléatoire du jeu
    private void paintGrid(Canvas canvas) {
        for (int i=0; i< matrixHeight; i++) {
            for (int j=0; j< matrixWidth; j++) {
                switch (grid[i][j]) {
                    case 0:
                        canvas.drawBitmap(gblock_b, gridLeftAnchor+ j*gridTileSize, gridTopAnchor+ i*gridTileSize, null);
                        break;
                    case 1:
                        canvas.drawBitmap(gblock_r,gridLeftAnchor+ j*gridTileSize, gridTopAnchor+ i*gridTileSize, null);
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
        //canvas.drawBitmap(win, carteLeftAnchor+ 3*miniTileSize, carteTopAnchor+ 4*miniTileSize, null);
    }

    protected void nDraw(Canvas canvas){
        miniTopAnchor  = 10;
        miniLeftAnchor = (getWidth()- matrixWidth*miniTileSize)/2; //CENTRER LA MINIATURE au milieu horizontalement
        gridTopAnchor= miniTopAnchor+miniTileSize*matrixHeight+150;
        gridLeftAnchor =(getWidth()-gridTileSize*matrixHeight)/2;//pour centrer la grille de jeu
        canvas.drawRGB(0,0,0);
        if(isWon()){
            paintWin(canvas);
        }else {
            paintInfo(canvas);
            paintMiniature(canvas);
            paintGrid(canvas);

        }
    }
    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
       //la surface change ex quand l'utilsateur tourne son téléphone
    }
    public void surfaceCreated(SurfaceHolder arg0) {

        Log.i("-> FCT <-", "surfaceCreated");
        in=true;
        cv_thread.start();
    }


    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceDestroyed");
        in=false;
    }
    public void run() {
        while (in) {
            Canvas c = null;
            try {
               // holder.setFixedSize(500,900);
                c = holder.lockCanvas();
                synchronized (holder) {
                    nDraw(c);
                }
            } finally {
                if (c != null) {
                    holder.unlockCanvasAndPost(c);
                }
            }
            try {
                cv_thread.sleep(20);
            } catch (InterruptedException ie) {
                Log.e("-> RUN <-", "PB DANS RUN");
            }

        }
    }



}
