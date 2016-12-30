package p8.boulma.sancho.intelligentworkout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * Created by SANCHO Mathilde on 20/12/2016.
 */

public class GameActivity extends Activity {
    private GameView mIntelligentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mIntelligentView =(GameView)findViewById(R.id.gameView);
        mIntelligentView.setVisibility(View.VISIBLE);
    }
}
