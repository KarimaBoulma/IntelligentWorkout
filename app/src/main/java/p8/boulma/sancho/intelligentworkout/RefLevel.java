package p8.boulma.sancho.intelligentworkout;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mathilde on 03/11/2016.
 */
public class RefLevel {
    private int idLevel;
    private static int[][] ref;
    private static int i, j;
    private BufferedReader data;
    private static int [][] initBRed;
    private int nBRed, nLevel,hsMin,hsSec,nbMoves;


    public RefLevel(InputStream input, int idLevel){
        try {
            this.data = new BufferedReader(new InputStreamReader(input));
            this.idLevel = idLevel;
            this.nLevel=0;
            this.i = 0;
            this.j = 0;
            ref= new int [5][5];
            readData();
            data.close();
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    public int getIdLevel() {
        return idLevel;
    }

    public int[][] getRef() {
        return ref;
    }

    public static int[][] getInitBRed() {
        return initBRed;
    }

    public int getnBRed() {
        return nBRed;
    }


    public void readData() {
        String line;
        try {
            line = data.readLine();
            while (!line.contains("LEVEL " + idLevel)) {
                line = data.readLine();
            }
            do {
                line = data.readLine();
                createTerrain(line.toString());
                i++;

            }while (line.charAt(0) != '{');
            createBlockRed(line);
            setHighScore(data.readLine());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void setHighScore(String line){
        String cst []=line.split(":");
        hsMin=Integer.parseInt(cst[0]);
        hsSec=Integer.parseInt(cst[1]);
        try {
            line = data.readLine();
            nbMoves = Integer.parseInt(line);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void createBlockRed(String line){
        nBRed =(line.length()-1)/4;
        initBRed=new int[nBRed][2];
        int a=0,b=0;
        for (String cst : line.split(",")){
            if(b==2) {
                a++;
                b=0;
            }
            if(cst.contains("{")) {
                initBRed[a][b] = Integer.parseInt(""+cst.charAt(1));
            }else if (cst.contains("}")) {
                initBRed[a][b] = Integer.parseInt("" + cst.charAt(0));
            }else {
                initBRed[a][b] = Integer.parseInt(cst);
            }
            b++;

        }
    }
    public void createTerrain(String line) {
        j=0;
        for (String cst : line.split(",")) {
            switch (cst) {
                case "CST_bleu":
                    ref[i][j] = 0;
                    j++;
                    break;
                case "CST_rouge":
                    ref[i][j] = 1;
                    j++;
                    break;
            }
        }

    }


}