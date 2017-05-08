package otomasyon.kafeotomasyonu;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import otomasyon.kafeotomasyonu.Modal.BildirimController;

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
        bildirimKontrol();
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

    public void bildirimKontrol(){
        try {
            DatabaseReference myRef = database.getReference("bildirimler");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //buton oluştur metoduna veritabanından masa sayısını çekip integer olarak parse ettik
                    long bld = dataSnapshot.getChildrenCount();
                    for (int i = 0; i < bld; i++) {
                        //BildirimController bc = new BildirimController(dataSnapshot.child(i).getValue());
                        //BildirimController bc = dataSnapshot.child(String.valueOf(i)).getValue(BildirimController.class);
                        notificationM(Integer.parseInt(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("bildirimId").getValue())), Integer.parseInt(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("masaId").getValue())));
                    }
                    veriSil(Integer.parseInt(String.valueOf(bld)));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
    NotificationManager nm;
    boolean isActive = false;
    public void notificationM(int notifID,int masaID){
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this);
        noBuilder.setContentTitle("Gel Lan Buraya");
        noBuilder.setContentText(String.valueOf(masaID)+" Numaralı masanın size ihtiyacı varmış !");
        noBuilder.setTicker("Alert New Message");
        noBuilder.setSmallIcon(R.mipmap.ic_launcher);

        Intent moreInfoIntent = new Intent(this, GarsonEkraniActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(GarsonEkraniActivity.class);
        taskStackBuilder.addNextIntent(moreInfoIntent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        noBuilder.setContentIntent(pendingIntent);
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(notifID, noBuilder.build());
        isActive = true;
    }
    public void veriSil(int countID){
        try {
            DatabaseReference myRef = database.getReference("bildirimler");
            for (int i = countID-1; 0 <= i; i--) {
                myRef.child(String.valueOf(i)).removeValue();
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
