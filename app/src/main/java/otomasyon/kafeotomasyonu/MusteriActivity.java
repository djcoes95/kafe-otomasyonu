package otomasyon.kafeotomasyonu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MusteriActivity extends AppCompatActivity {
    Button cikis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici);
        cikis= (Button) findViewById(R.id.btn_cikisyap);
        cikisSetOnClick();
    }

    private void cikisSetOnClick() {
        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MusteriActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
