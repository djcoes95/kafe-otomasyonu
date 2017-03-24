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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import otomasyon.kafeotomasyonu.Modal.Kullanici;
import otomasyon.kafeotomasyonu.Modal.LoginController;

public class LoginActivity extends AppCompatActivity {
    Button girisYap, parolasifirla;
    EditText mail,parola;
    String email, password;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        mail = (EditText) findViewById(R.id.et_mail);
        parola = (EditText) findViewById(R.id.et_parola);
        girisYap= (Button) findViewById(R.id.btn_giris);
        parolasifirla= (Button) findViewById(R.id.btn_parolasifirla);

        setOnGirisYapListener();
        setOnParolaSifirla();
    }

    private void setOnParolaSifirla() {
        parolasifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                email = mail.getText().toString();
                password = parola.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, R.string.mailgir , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, R.string.parolagir , Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, R.string.girishatasi, Toast.LENGTH_SHORT).show();

                                    }else{
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
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String id = user.getUid();
        DatabaseReference myRef = database.getReference("garsonlar");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                garsonVarMi=dataSnapshot.child(id).exists();
                if(garsonVarMi){
                    Intent intent = new Intent(LoginActivity.this, GarsonEkraniActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, KullaniciActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(LoginActivity.this, R.string.baglantihatasi, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
