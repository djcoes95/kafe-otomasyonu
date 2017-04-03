package otomasyon.kafeotomasyonu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GarsonEkraniActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button cikis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garson);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //dene= (TextView) findViewById(R.id.tv_deneme);
        masaSayisi();
        cikisSetOnClick();
    }
    private String masaSayisi() {
        String masaSayisi="0";
        DatabaseReference myRef = database.getReference("masalar");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                butonOlustur(Integer.parseInt(dataSnapshot.getChildrenCount()+""));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        return masaSayisi;
    }
    void butonOlustur(int gelen){
        int satir=1,sutun=5,masaid=1;
        GridLayout gridLayout = (GridLayout)findViewById(R.id.masalar);
        ///HATA VAR DÜZELT MASA SATIR SÜTÜN BOZUK GELİYOR
        if(gelen>5){
            satir = (gelen/4)+1;
        }
        gridLayout.setColumnCount(sutun);
        gridLayout.setRowCount(satir);
        for(int i=0;i<gelen;i++){
            Button myButton = new Button(this);
                myButton.setId(masaid);
                myButton.setText("Masa " + masaid);
                myButton.setBackgroundResource(R.drawable.masabuton);
                myButton.setTextAppearance(this,R.style.garsonMasaSecim);

                gridLayout.addView(myButton,i);
            masaid++;
            final int id=myButton.getId();
            myButton = ((Button) findViewById(id));

            myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {  // butonun click olayi
                Toast.makeText(view.getContext(),
                        "Masa "+ id +"'ya tiklandi", Toast.LENGTH_SHORT)
                        .show();
                Intent i = new Intent(GarsonEkraniActivity.this, SiparisEkle.class);
                i.putExtra("masaNumarasi",id);
                startActivity(i);

            }
        });
        }
    }


    private void cikisSetOnClick() {
        cikis= (Button) findViewById(R.id.btn_garson_cikis);
        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(GarsonEkraniActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
