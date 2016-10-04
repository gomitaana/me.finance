package froyo.itesm.mx.mefinance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread loading = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3500);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {
                    Intent i = new Intent(LoadingScreen.this, MainMenu.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        loading.start();
    }
}
