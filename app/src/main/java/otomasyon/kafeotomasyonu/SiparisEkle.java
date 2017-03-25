package otomasyon.kafeotomasyonu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import otomasyon.kafeotomasyonu.Modal.Urunler;
import otomasyon.kafeotomasyonu.Modal.UrunlerListe;

public class SiparisEkle extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final List<UrunlerListe> urunlerr=new ArrayList<UrunlerListe>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_ekle);
        siparisGoster();
        
    }

    private void siparisGoster() {

        urunYazdir();
    }

    private void urunYazdir() {
        System.out.println(urunlerr);
    }
}
