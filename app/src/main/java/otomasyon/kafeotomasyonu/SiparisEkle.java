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
        //siparisGoster();
        
    }

//    private void siparisGoster() {
//        DatabaseReference myRef = database.getReference("urunler");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                dataSnapshot  = dataSnapshot.child("1");
//                String fiyat = (String) dataSnapshot.getValue();
//                //Urunler u = dataSnapshot.getValue(Urunler.class);
//                urunlerr.add(new UrunlerListe(u.getUrunadi(),fiyat));
////                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
////                    Urunler u = postSnapshot.child(1+"").getValue(Urunler.class);
////                    urunlerr.add(new UrunlerListe(u.getUrunadi(),u.getUrunfiyati()));
////                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        });
//        urunYazdir();
//    }

    private void urunYazdir() {
        System.out.println(urunlerr);
    }
}
