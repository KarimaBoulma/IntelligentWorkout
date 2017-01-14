package p8.boulma.sancho.dataBase;

/**
 * Created by Mathilde on 09/01/2017.
 */

public class HighScore {
    private int id,nbmoves;
    private long time;

    public HighScore(){
        id=0;
        time=0;
        nbmoves=0;
    };

    public HighScore(int id, long time, int nbmoves){
        this.id=id;
        this.time=time;
        this.nbmoves=nbmoves;
    }

    public int getId() {
        return id;
    }

    public void setNbmoves(int nbmoves) {
        this.nbmoves = nbmoves;
    }

    public int getNbmoves() {
        return nbmoves;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
