package otomasyon.kafeotomasyonu;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button girisYap, parolasifirla;
    EditText mail,parola;
    String email, password;
    ProgressBar pb;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        //Üstteki StatusBar çubuğunun rengini arkaplanla aynı yapar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        //tasarımda kullandığımız komponentleri tanımladık
        mail = (EditText) findViewById(R.id.et_mail);
        parola = (EditText) findViewById(R.id.et_parola);
        girisYap= (Button) findViewById(R.id.btn_giris);
        parolasifirla= (Button) findViewById(R.id.btn_parolasifirla);
        pb= (ProgressBar) findViewById(R.id.pb_login);

        //giriş yap butonuna tıklayınca çalışacak metot
        setOnGirisYapListener();
        //parola sıfırla butonuna basıldığında çalışacak metot
        setOnParolaSifirla();
    }

    private void setOnParolaSifirla() {
        parolasifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parola sıfırlama sayfasına gitmesi içi intent yazdık
                Intent intent = new Intent(LoginActivity.this, ParolaSifirlaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setOnGirisYapListener() {
        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                // mail alanına yazılan veriyi email değişkenine attık
                email = mail.getText().toString();
                // parola alanına yazılan veriyi password değişkenine attık
                password = parola.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    //eğer eposta girilmemişse hata döndürecek
                    Toast.makeText(LoginActivity.this, R.string.mailgir , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    //parola girilmemişse hata döndürecek
                    Toast.makeText(LoginActivity.this, R.string.parolagir , Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    //giriş yapmak için gerekli firebase metodu alınan email ve parolayı parametrele gönderdik
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //eğer parola 6 haneden kısaysa hata döndürüyoruz
                                    if (parola.length()<6){
                                        parola.setError(getText(R.string.parola6karakter));
                                        return;
                                    }
                                    if (!task.isSuccessful()) {
                                        pb.setVisibility(View.GONE);
                                        //eğer giriş başarılı değilse hata döndürdük
                                        Toast.makeText(LoginActivity.this, R.string.girishatasi, Toast.LENGTH_SHORT).show();

                                    }else{
                                        //eğer giriş başarılıysa garson mu kullanıcı mı olduğunu anlamak için
                                        // garsonMu metodunu çağırdık
                                        garsonMu();
                                    }
                                }
                            });
                }
            }
        });
    }


    Boolean garsonVarMi;
    public void garsonMu(){
        //firebase kullanıcı girişi metodları için gerekli
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //user.getUid ile firebasin her kullanıcıya verdiği kullanıcı idyi çektik
        final String id = user.getUid();
        //veritabanında garsonları referans aldık
        DatabaseReference myRef = database.getReference("garsonlar");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //bu metot ile garsonların altında girilen kullanıcı id var mı diye bakılıyor
                //exits metodu bize true ya da false döndürüyor. Eğer garsonların altında girilen kullanıcı
                // idsi varsa true dönecek
                garsonVarMi=dataSnapshot.child(id).exists();
                if(garsonVarMi){
                    //eğer garsonsa garson ekraına gönderiyoruz
                    Intent intent = new Intent(LoginActivity.this, GarsonEkraniActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    //değilse zaten müşteridir müşteri ekranına gönderiyoruz
                    Intent intent = new Intent(LoginActivity.this, MusteriActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                //veriyi çekememişsek internet bağlantısında hata vardır. Bu yüzden hata döndürüyoruz
                Toast.makeText(LoginActivity.this, R.string.baglantihatasi, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
