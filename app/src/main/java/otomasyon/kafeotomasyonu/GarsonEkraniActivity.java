package otomasyon.kafeotomasyonu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import otomasyon.kafeotomasyonu.Modal.MasaController;

public class GarsonEkraniActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView dene;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garson);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //dene= (TextView) findViewById(R.id.tv_deneme);
        masaSayisi();
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
        int satir=1,sutun=4,butonsira=0,butonsira2=1;
        Toast.makeText(this, String.valueOf(gelen), Toast.LENGTH_SHORT).show();
        GridLayout gridLayout = (GridLayout)findViewById(R.id.activity_garson);
        ///HATA VAR DÜZELT MASA SATIR SÜTÜN BOZUK GELİYOR
        if(gelen>5){
            satir = (gelen%4)+1;

        }
        gridLayout.setColumnCount(sutun);
        gridLayout.setRowCount(satir);
        for(int r = 1; r <satir; r++ ){

            for(int c = 1; c < sutun; c++ ){
                Button myButton = new Button(this);
                myButton.setId(butonsira);
                myButton.setText("Masa " + butonsira2);
                gridLayout.addView(myButton,butonsira);
                butonsira++;
                butonsira2++;
            }
        }

    }


//    void butonOlustur(int gelen){
//        int x=150,y=300;
//        for(int i = 1;i<=gelen;i++){
//
//        Button myButton = new Button(this);
//        final RelativeLayout ll = (RelativeLayout)findViewById(R.id.activity_garson);  // butonun eklenecegi layout tanimlandi
//        myButton.setId(i);   // butona id atandi
//        myButton.setX(x);  // butona x koordinati verildi
//        myButton.setY(y);  // butona y koordinati verildi
//        myButton.setText(" "+i);
//        final int id=myButton.getId();
//
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(200,100);  // butonun boyutlari verildi
//        ll.addView(myButton, lp);
//        myButton = ((Button) findViewById(id));
//        myButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {  // butonun click olayi
//                Toast.makeText(view.getContext(),
//                        "Button "+ id +" tiklandi", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
//            x+=100;
//            y+=100;
//        }
//    }
}
