package com.example.proiect_apostu_gavril_1081;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CardAdapter extends BaseAdapter{
    private List<Card> cards;
    private Context context;
    int idImg[]={R.drawable.zara_logo, R.drawable.prada, R.drawable.hm_logo, R.drawable.stradivarius_logo,
            R.drawable.penny, R.drawable.mega, R.drawable.lidl_logo, R.drawable.carrefour_logo, R.drawable.sephora,
            R.drawable.douglas_logo, R.drawable.dm_logo, R.drawable.ikea_logo, R.drawable.dedeman_logo, R.drawable.jysk_logo};


    public CardAdapter(List<Card> cards, Context context) {
        this.cards = cards;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.cards.size();
    }


    @Override
    public Object getItem(int position) {
        if(position>=0&&position<this.getCount()) {
            return this.cards.get(position);
        }
        else{
            throw  new IllegalArgumentException("Incorrect parameters");
        }
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(this.context);
        View generatedView=inflater.inflate(R.layout.card_item_layout,parent,false);

        Card card = (Card) getItem(position);

        ImageView image = generatedView.findViewById(R.id.storeLogoImage);

        TextView store_name_tv=generatedView.findViewById(R.id.store_name_tv);

        store_name_tv.setText(card.getStoreName());

        image.setImageResource(idImg[card.getIdJSONObject()-1]);

        return generatedView;
    }


}
