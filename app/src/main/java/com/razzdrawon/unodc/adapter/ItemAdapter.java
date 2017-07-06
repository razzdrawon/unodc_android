package com.razzdrawon.unodc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.razzdrawon.unodc.R;
import com.razzdrawon.unodc.model.Item;

import java.util.List;

/**
 * Created by mapadi3 on 03/07/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView qstnNbr, qstnStr;
        public EditText openAns;
        public Spinner optsSpn;

        public ViewHolder(View itemView) {
            super(itemView);
            qstnNbr = (TextView) itemView.findViewById(R.id.qstn_nbr);
            qstnStr = (TextView) itemView.findViewById(R.id.qstn_str);
            openAns = (EditText) itemView.findViewById(R.id.open_answer_et);
            optsSpn = (Spinner) itemView.findViewById(R.id.opts_spn);
        }
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.qstnNbr.setText(item.getQstnNbr() + ".- ");
        holder.qstnStr.setText(item.getQstnStr());
        if(item.getOpenAnswerFlag()) {
            holder.openAns.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
