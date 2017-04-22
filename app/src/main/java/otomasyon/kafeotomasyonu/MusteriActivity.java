package otomasyon.kafeotomasyonu;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MusteriActivity extends AppCompatActivity  implements ZXingScannerView.ResultHandler{
    Button cikis, garsonCagir;
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici);
        //çıkış yap butonuna basılınca
        cikisSetOnClick();
        garsonCagirSetOnClick();

    }

    private void garsonCagirSetOnClick() {
        garsonCagir= (Button) findViewById(R.id.btn_garson_cagir);
        garsonCagir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView = new ZXingScannerView(MusteriActivity.this);   // Programmatically initialize the scanner view<br />
                setContentView(mScannerView);
                mScannerView.setResultHandler(MusteriActivity.this); // Register ourselves as a handler for scan results.<br />
                mScannerView.startCamera();

            }
        });
    }

    private void cikisSetOnClick() {
        cikis= (Button) findViewById(R.id.btn_cikisyap);
        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //firebase çıkış yap metodu
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MusteriActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void handleResult(Result result) {
        if(result.getText()!=null){
            garsonGel(result.getText());
            Intent i = new Intent(this,MusteriActivity.class);
            startActivity(i);
        }
    }

    private void garsonGel(String gelen) {
        Toast.makeText(MusteriActivity.this, "Masa Numaranız :" + gelen, Toast.LENGTH_SHORT).show();
    }
}
