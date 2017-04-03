package otomasyon.kafeotomasyonu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import otomasyon.kafeotomasyonu.Modal.MenuGetirController;
import otomasyon.kafeotomasyonu.Modal.Urunler;

public class MainActivity extends AppCompatActivity {
    Button giris, kayit;
    ListView menu;
    ArrayList<Urunler> urunler = new ArrayList<Urunler>();
    //Firebase İçin gerekliler
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Eğer kullanıcı daha önceden giriş yaptıysa önbellekte tutuluyor.
        //Tekrar tekrar oturum açtırmamak için alttaki metot kullaılmıştır
        menuGetir();
        atlamaYapilacakMi();
        setContentView(R.layout.activity_main);
        //Giriş butonuna tıklanınca
        girisSetOnClick();
        //kayıt ol butonuna tıklanınca
        kayitolSetOnCLick();
    }

    private void menuListele() {

        MenuGetirController adapter = new MenuGetirController(this,R.layout.menu_satir_layout,urunler);

        menu = (ListView)findViewById(R.id.lv_menu);
        if(menu != null){
            menu.setAdapter(adapter);
        }
    }

    private void menuGetir() {

        DatabaseReference myRef = database.getReference("urunler");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i=1;i<=Integer.parseInt(dataSnapshot.getChildrenCount()+"");i++)
                {
                    String urunadi = (String) dataSnapshot.child(String.valueOf(i)).child("urunadi").getValue();
                    String fiyat =  String.valueOf(dataSnapshot.child(String.valueOf(i)).child("urunfiyati").getValue());
                    urunler.add(new Urunler(urunadi,fiyat+" ₺"));
                }

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


    private void kayitolSetOnCLick() {
        kayit = (Button) findViewById(R.id.btn_kayitol);
        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Butona Clickleyince Signup Activitye gidecek.
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void girisSetOnClick() {
        giris = (Button) findViewById(R.id.btn_girisyap);
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Butona Clickleyince Login Activitye gidecek.
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean oturumAcikMi(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
        {
            return true;
        }
        return false;
    }

    private void atlamaYapilacakMi() {
        //Eğer oturum açılmışsa kullanıcı garson mu müşteri mi olduğunu anlamamız lazım
        if(oturumAcikMi()){
            garsonMu();
        }
    }

    Boolean garsonVarMi;
    public void garsonMu(){

        //Bu metotta kullanıcı idsi alınır. Garsonların altında idyi arıyor. Eğer id varsa
        //GarsonActivity'e gidiyor. Yoksa MusteriActivity'e Gidiyor
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String id = user.getUid();
        DatabaseReference myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                garsonVarMi=dataSnapshot.child("garsonlar").child(id).exists();
                if(garsonVarMi){
                    Intent intent = new Intent(MainActivity.this, GarsonEkraniActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, MusteriActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
//                Toast.makeText(MainActivity.this, R.string.baglantihatasi, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
