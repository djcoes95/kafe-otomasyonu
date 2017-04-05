package otomasyon.kafeotomasyonu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import otomasyon.kafeotomasyonu.Modal.SiparisEkleAdapter;
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
        //masabilgilerini aldığımız metot
        masaBilgileri();
        //sipariş ver butonuna tıklandığında olacaklar
        siparisVersetOnClick();
    }

    private void siparisVersetOnClick() {
        //henüz siparişver butonu aktif değil
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
                    //ürün adları yeterli olduğundan ürün adlarını çekip stringe atıyoruz
                    String urunadi = (String) dataSnapshot.child(String.valueOf(i)).child("urunadi").getValue();
                    //Siparis getir adında oluşturduğumuz modele göre ürün adını gönderidl
                    urunler.add(new SiparisGetir(urunadi,0));
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
    private void masaBilgileri()
    {
        //garson activityden seçilen masanumarasını çağırdık
        Bundle extras = getIntent().getExtras();
        //masa id adlı değişkene atadık
        int masaid = extras.getInt("masaNumarasi");
        //Textviewe masa numarasını atadık
        TextView masa = (TextView) findViewById(R.id.textMasa);
        masa.setText("Masa "+String.valueOf(masaid));
    }
}
