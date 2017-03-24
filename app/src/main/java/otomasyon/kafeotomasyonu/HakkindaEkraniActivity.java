package otomasyon.kafeotomasyonu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class HakkindaEkraniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkinda);
        Toast.makeText(this, "Yapım Aşamasında!", Toast.LENGTH_SHORT).show();
    }
}
