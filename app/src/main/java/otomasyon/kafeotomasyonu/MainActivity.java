package otomasyon.kafeotomasyonu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button giris, kayit;
    //Firebase İçin gerekliler
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Eğer kullanıcı daha önceden giriş yaptıysa önbellekte tutuluyor.
        //Tekrar tekrar oturum açtırmamak için alttaki metot kullaılmıştır
        atlamaYapilacakMi();
        setContentView(R.layout.activity_main);
        //Giriş butonuna tıklanınca
        girisSetOnClick();
        //kayıt ol butonuna tıklanınca
        kayitolSetOnCLick();

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
