package otomasyon.kafeotomasyonu;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import otomasyon.kafeotomasyonu.Modal.Kullanici;
import otomasyon.kafeotomasyonu.Modal.LoginController;
import otomasyon.kafeotomasyonu.Modal.SignUpController;

public class SignupActivity extends AppCompatActivity {
    Button kayitOl;
    EditText mail,parola,adSoyad;
    //firebase gerekliler
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //firebase kullanıcı gerekli
        mAuth = FirebaseAuth.getInstance();
        //status bar rengini değiştiriyoruz. Eskiden status barı değiştiremiyorduk. Belli
        //bir sürüm üzerini destekliyor bu yüzden sdk kontrolü yaptık
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        //gerekli komponentler
        adSoyad = (EditText) findViewById(R.id.et_kayit_ad);
        mail = (EditText) findViewById(R.id.et_kayit_mail);
        parola = (EditText) findViewById(R.id.et_kayit_parola);
        kayitOl= (Button) findViewById(R.id.btn_kayitol);
        //kayıt ol butonuna tıklandıgında
        setOnKayitOlListener();
    }

    private void setOnKayitOlListener() {
        kayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Butona Tıklayınca signIn metodu çalışacak
                //ad soyad boşsa hata döndür
                if (TextUtils.isEmpty(adSoyad.getText().toString())) {
                    Toast.makeText(SignupActivity.this, R.string.adsoyadgir , Toast.LENGTH_SHORT).show();
                    return;
                }
                //mail boşsa hata döndür
                if (TextUtils.isEmpty(mail.getText().toString())) {
                    Toast.makeText(SignupActivity.this, R.string.mailgir , Toast.LENGTH_SHORT).show();
                    return;
                }
                //parola boşsa hata döndür
                if (TextUtils.isEmpty(parola.getText().toString())) {
                    Toast.makeText(SignupActivity.this, R.string.parolagir , Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    //firebase hesağ oluşturma metodu mail ve parola gönderiyoruz
                    mAuth.createUserWithEmailAndPassword(mail.getText().toString(), parola.getText().toString())
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //parola 6 karakterden küçükse hata döndür
                                    if (parola.length()<6){
                                        parola.setError(getText(R.string.parola6karakter));
                                        return;
                                    }
                                    //kayıt başarısızsa
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, R.string.baglantihatasi, Toast.LENGTH_SHORT).show();
                                    }
                                    //kayıt başarılıysa
                                    else{
                                        //aldığımız ad soyad ve mail bilgileri veritabanına yazılır
                                        bilgileriYaz();
                                        //Ana activitye yönlendirilir
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

    private DatabaseReference mDatabase;
    private void bilgileriYaz(){
        //veritabanı referans gösterilir
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //firebase tarafından kullanıcıya atanan user id çekilir
        //id adlı değişkene aktarılır
        final String id = user.getUid();
        //ad soyad ve mail strinlere atanız
        String name = adSoyad.getText().toString();
        String email = mail.getText().toString();
        //yeni bir kullanıcı oluşturulu
        SignUpController sc = new SignUpController(email,name);
        //veritabanında musterilerin altına alınan id le birlikte veriler yazdırılır
        mDatabase.child("musteriler").child(id).setValue(sc);

    }

}
