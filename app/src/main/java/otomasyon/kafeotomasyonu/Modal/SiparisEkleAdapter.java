package otomasyon.kafeotomasyonu.Modal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import otomasyon.kafeotomasyonu.R;

/**
 * Created by Bilal on 4.04.2017.
 */

public class SiparisEkleAdapter extends ArrayAdapter<SiparisGetir> {
    Button ekle;
    private Context context;
    public SiparisEkleAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<SiparisGetir> items) {
        super(context, resource, items);
        this.context=context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // view holder kurulumu
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.siparis_satir_layout, null);

            // view holderı sıfırdan oluşturma
            viewHolder = new ViewHolder();
            viewHolder.urunAdi = (TextView) convertView.findViewById(R.id.list_item_string);
            viewHolder.urunSayisi = (TextView) convertView.findViewById(R.id.urun_sayisi);
            viewHolder.ekle = (Button) convertView.findViewById(R.id.add_btn);
            viewHolder.sil = (Button) convertView.findViewById(R.id.delete_btn);
            convertView.setTag(viewHolder);

        }else {

            // view holderı zaten daha önceden oluşturduğumuz
            // view holdera eşitleme
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        TextView urunadi = (TextView)v.findViewById(R.id.list_item_string);
//        ekle = (Button) v.findViewById(R.id.add_btn);
//        Button sil = (Button) v.findViewById(R.id.delete_btn);
//        SiparisGetir sg = getItem(position);
//        if (sg != null) {
//
//            if(urunadi != null){
//                urunadi.setText(sg.getUrunadi());
//            }
//        }
//        return v;
        final SiparisGetir sg = getItem(position);
            viewHolder.urunAdi.setText(sg.getUrunadi());
            viewHolder.urunSayisi.setText(sg.getUrunsayisi()+"");
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = sg.getUrunsayisi();
                temp++;
                sg.setUrunsayisi(temp);

                finalViewHolder.urunSayisi.setText(sg.getUrunsayisi()+"");
            }
        });
        viewHolder.sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = sg.getUrunsayisi();
                if(temp>0){
                temp--;
                }
                sg.setUrunsayisi(temp);

                finalViewHolder.urunSayisi.setText(sg.getUrunsayisi()+"");
            }
        });
        return convertView;


    }
    class ViewHolder {
        TextView urunAdi;
        TextView urunSayisi;
        Button ekle;
        Button sil;
    }

}
