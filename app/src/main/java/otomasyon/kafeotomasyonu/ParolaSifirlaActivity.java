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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ParolaSifirlaActivity extends AppCompatActivity {
    Button sifirla;
    EditText mail;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parola_sifirla);
        sifirla = (Button) findViewById(R.id.btn_sifirla);
        mail = (EditText) findViewById(R.id.et_sifirlamail);
        auth = FirebaseAuth.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        sifirlaSetOnClick();

    }

    private void sifirlaSetOnClick() {
        sifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getText().toString().trim();
                //İLK KONTROL//
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), getString(R.string.mailgir), Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ParolaSifirlaActivity.this,getString(R.string.epostagonderildi), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ParolaSifirlaActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ParolaSifirlaActivity.this,getString(R.string.epostagonderilemedi), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
}
