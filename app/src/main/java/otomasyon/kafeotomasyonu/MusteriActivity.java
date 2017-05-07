package otomasyon.kafeotomasyonu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import otomasyon.kafeotomasyonu.Modal.BildirimController;

public class MusteriActivity extends AppCompatActivity  implements ZXingScannerView.ResultHandler {
    //firebase veritabanı işlemleri için gerekli
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button cikis, garsonCagir;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici);
        //çıkış yap butonuna basılınca
        cikisSetOnClick();
        garsonCagirSetOnClick();
        bildirimSayisi();
    }

    String bildirimId = "0";

    private void bildirimSayisi() {
        String bilidirimSayisi = "0";
        //veritabanında masalar kısmını referans gösterdik
        DatabaseReference myRef = database.getReference("bildirimler");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //buton oluştur metoduna veritabanından masa sayısını çekip integer olarak parse ettik
                bildirimId = String.valueOf(dataSnapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    ZXingScannerView a;
    private void garsonCagirSetOnClick() {
        garsonCagir = (Button) findViewById(R.id.btn_garson_cagir);
        garsonCagir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView = new ZXingScannerView(MusteriActivity.this);   // Programmatically initialize the scanner view<br />
                setContentView(mScannerView);
                mScannerView.setResultHandler(MusteriActivity.this); // Register ourselves as a handler for scan results.<br />
                mScannerView.startCamera();
                a = mScannerView;
            }
        });
    }

    private void cikisSetOnClick() {
        cikis = (Button) findViewById(R.id.btn_cikisyap);
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
        if (result.getText() != null) {
            garsonGel(result.getText());
            Intent i = new Intent(this, MusteriActivity.class);
            startActivity(i);
        }
    }

    private DatabaseReference mDatabase;

    private void garsonGel(String gelen) {
        Toast.makeText(MusteriActivity.this, "" + gelen + " Numaralı Masaya Garson Çağrıldı.", Toast.LENGTH_SHORT).show();
        vtYaz(gelen);
    }

    NotificationManager nm;
    boolean isActive = false;
    int notifID = 33;

    private void vtYaz(String gelen) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        BildirimController bc = new BildirimController(bildirimId, gelen);
        //veritabanı referans gösterilir
        //veritabanında musterilerin altına alınan id le birlikte veriler yazdırılır
        mDatabase.child("bildirimler").child(bildirimId).setValue(bc);
        a.stopCamera();
        finish();
    }
}