package otomasyon.kafeotomasyonu.Modal;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import otomasyon.kafeotomasyonu.R;

/**
 * Created by Bilal on 3.04.2017.
 */

public class MenuGetirAdapter extends ArrayAdapter<Urunler> {

    public MenuGetirAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Urunler> items) {
        super(context, resource, items);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.menu_satir_layout, null);
        }


        Urunler p = getItem(position);
        if (p != null) {

            TextView ad = (TextView)v.findViewById(R.id.satir_urunadi);
            TextView fiyat = (TextView)v.findViewById(R.id.satir_fiyat);

            if(ad != null && fiyat != null){
                ad.setText(p.getUrunadi());
                fiyat.setText(p.getUrunfiyati());
            }
        }
        return v;


    }
}
