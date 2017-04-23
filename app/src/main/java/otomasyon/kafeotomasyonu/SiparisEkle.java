package otomasyon.kafeotomasyonu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import otomasyon.kafeotomasyonu.Modal.SiparisEkleAdapter;
import otomasyon.kafeotomasyonu.Modal.SiparisEkleController;
import otomasyon.kafeotomasyonu.Modal.SiparisGetir;

public class SiparisEkle extends AppCompatActivity {
    //firebase veritabanı işlemleri için
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //menüyü getirmek için kullanılacak listviev ve dizi tanımlandı
    ListView menu;
    ArrayList<SiparisGetir> urunler = new ArrayList<SiparisGetir>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_ekle);
        //sipariş göster metodu
        siparisGoster();

        TextView masa = (TextView) findViewById(R.id.textMasa);
        //masabilgilerini aldığımız metot
        masa.setText("Masa "+ masaBilgileri());
        //sipariş ver butonuna tıklandığında olacaklar
        int id = siparisIdBelirle();
        siparisVersetOnClick(id);

        //geri butonuna basıldığında
        gerisetOnClick();
    }

    private void gerisetOnClick() {
        Button geri = (Button) findViewById(R.id.siparis_ekrani_geri);
        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SiparisEkle.this, GarsonEkraniActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void siparisVersetOnClick(final int id) {
        Button siparisVer = (Button) findViewById(R.id.siparisver);
        //henüz siparişver butonu aktif değil
        siparisVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(SiparisGetir sp : urunler){
                    if(sp.getUrunsayisi()>0){
                        bilgileriYaz(sp);
                    }
                }
            }
        });
    }

    int siparisID;
    private int siparisIdBelirle() {
        DatabaseReference syRef = database.getReference("siparisler");
        syRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               siparisID = Integer.parseInt(dataSnapshot.getChildrenCount()+"")+1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return siparisID;
    }

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private void bilgileriYaz(SiparisGetir sp){
        //tarihi çekmek için
        Date simdikiZaman = new Date();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        //veritabanı referans gösterilir
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //firebase tarafından kullanıcıya atanan user id çekilir
        //id adlı değişkene aktarılır
        final String garsonid = user.getUid();
        SiparisEkleController sec = new SiparisEkleController(masaBilgileri(), garsonid,
                sp.getUrunsayisi(),urunIDGetir(sp.getUrunadi()),false,df.format(simdikiZaman));
        //veritabanında musterilerin altına alınan id le birlikte veriler yazdırılır
        mDatabase.child("siparisler").child(siparisID+"").setValue(sec);
        siparisID++;

        Intent i = new Intent(this,GarsonEkraniActivity.class);
        startActivity(i);
    }

    private int urunIDGetir(String urunadi) {
        int urunid=0;
        for(SiparisGetir sp : urunler){
            if(sp.getUrunadi().equals(urunadi)){
                urunid=sp.getUrunid();
                break;
            }
        }
        return urunid;
    }

    private void menuListele() {
        //Listvieww için oluşturduğumuz adapter parametre olarak siparis satir layout ve urunler dizisini gönderdik
        SiparisEkleAdapter adapter = new SiparisEkleAdapter(this,R.layout.siparis_satir_layout,urunler);
        //listview tanımlandı
        menu = (ListView)findViewById(R.id.lv_garson_menu);
        //menü boş değilse verileri menü listesine ekler
        if(menu != null){
            menu.setAdapter(adapter);
        }
    }
    private void siparisGoster() {
        //veritabanında urunler kısmını referans aldık
        DatabaseReference myRef = database.getReference("urunler");
        //veri okuma metodu
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for döngüsüyle tüm ürünleri geziyoruz
                for (int i=1;i<=Integer.parseInt(dataSnapshot.getChildrenCount()+"");i++)
                {
                    //ürün adlarını çekip stringe atıyoruz
                    String urunadi = (String) dataSnapshot.child(String.valueOf(i)).child("urunadi").getValue();
                    long uid = (Long) dataSnapshot.child(String.valueOf(i)).child("urunid").getValue();
                    int urunid = (int) uid;
                    //Siparis getir adında oluşturduğumuz modele göre ürün adını gönderidl
                    urunler.add(new SiparisGetir(urunadi,urunid,0));
                }
                //forla verileri çektikten sonra menü listele metodu çağrıldı
                menuListele();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }
    private int masaBilgileri()
    {
        //garson activityden seçilen masanumarasını çağırdık
        Bundle extras = getIntent().getExtras();
        //masa id adlı değişkene atadık
        int masaid = extras.getInt("masaNumarasi");
        //Textviewe masa numarasını atadık

        return masaid;
    }
}
