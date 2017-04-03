package otomasyon.kafeotomasyonu.Modal;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import otomasyon.kafeotomasyonu.R;

/**
 * Created by Bilal on 4.04.2017.
 */

public class SiparisEkleAdapter extends ArrayAdapter<SiparisGetir> {

    public SiparisEkleAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<SiparisGetir> items) {
        super(context, resource, items);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.siparis_satir_layout, null);
        }


        SiparisGetir sg = getItem(position);
        if (sg != null) {

            TextView urunadi = (TextView)v.findViewById(R.id.list_item_string);
            Button ekle = (Button) v.findViewById(R.id.add_btn);
            Button sil = (Button) v.findViewById(R.id.delete_btn);

            if(urunadi != null){
                urunadi.setText(sg.getUrunadi());
            }
        }
        return v;


    }
}
