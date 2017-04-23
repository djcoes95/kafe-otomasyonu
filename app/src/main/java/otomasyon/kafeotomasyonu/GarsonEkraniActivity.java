package otomasyon.kafeotomasyonu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GarsonEkraniActivity extends AppCompatActivity {
    //firebase veritabanı işlemleri için gerekli
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button cikis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garson);
        //masa sayısını getiren metot
        masaSayisi();
        //çıkış yap butonuna basıldığında
        cikisSetOnClick();
    }
    private void masaSayisi() {
        String masaSayisi="0";
        //veritabanında masalar kısmını referans gösterdik
        DatabaseReference myRef = database.getReference("masalar");
        //firebase veritabanından veri okuma metodu
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //buton oluştur metoduna veritabanından masa sayısını çekip integer olarak parse ettik
                butonOlustur(Integer.parseInt(dataSnapshot.getChildrenCount()+""));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }
    void butonOlustur(int gelen){
        int satir=1,sutun=5,masaid=1;
        //gridlayotu tanımladık
        GridLayout gridLayout = (GridLayout)findViewById(R.id.masalar);
        ///HATA VAR DÜZELT MASA SATIR SÜTÜN BOZUK GELİYOR
        if(gelen>5){
            //tablete göre ayarladık.
            //gelen veriyi 4 e böldük. kaç satır olduğunu bulduk ve 1 satır kesin olacağından
            //bir ekledik
            satir = (gelen/4)+1;
        }
        //gridlayouta sutun sayisini set ettik
        gridLayout.setColumnCount(sutun);
        //gridlayouta satır sayisini set ettik
        gridLayout.setRowCount(satir);
        for(int i=0;i<gelen;i++){
                //gelen masa sayısı kadar buton oluşturulacak
                Button myButton = new Button(this);
                //gelen butona masanın sayısını id olarak verdik
                myButton.setId(masaid);     //hata görünüyor ancak çalışıyor nedenini anlayamadım.
                //butonun metnine Masa ve idsini atadık
                myButton.setText("Masa " + masaid);
                //masanın yuvarlak stlini drawable klasörde masabuton adında oluşturmuştuk. Onu atadık
                myButton.setBackgroundResource(R.drawable.masabuton);
                //res-stylerde olan garsonMasaSecim adında oluşturduğumuz stili atadık
                myButton.setTextAppearance(this,R.style.garsonMasaSecim);
                //gridlayouta butonu ve butonun kaçıncı sırada olduğnu ekledik
                gridLayout.addView(myButton,i);
                //masaid değişkenini arttırdık
                masaid++;
                //hangi butona tıkladığımızı bilmemiz için id tanımladık
                final int id=myButton.getId();
                myButton = ((Button) findViewById(id));
                //herhangi masaya tıklandığında yapılacaklar
                myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {  // butonun click olayi
                //tıklanılan masaya ait sipariş alınması için siparisekle activitye gönderdik
                Intent i = new Intent(GarsonEkraniActivity.this, SiparisEkle.class);
                //masanumarasını diğer activityde kullanmak için gönderdik
                i.putExtra("masaNumarasi",id);
                startActivity(i);
                finish();
            }
        });
        }
    }

    private void cikisSetOnClick() {
        cikis= (Button) findViewById(R.id.btn_garson_cikis);
        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //çıkış butonuna basıldığında olacaklar
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(GarsonEkraniActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
