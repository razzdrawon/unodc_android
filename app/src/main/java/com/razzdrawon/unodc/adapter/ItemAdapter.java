package com.razzdrawon.unodc.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.razzdrawon.unodc.R;
import com.razzdrawon.unodc.model.Item;

import java.util.List;

/**
 * Created by mapadi3 on 03/07/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    Context context;
    List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout qstnLayout;
        public TextView qstnNbr, qstnStr;
        public EditText openAns;
        public RadioGroup optsRadio;

        public ViewHolder(View itemView) {
            super(itemView);
            qstnLayout = (LinearLayout) itemView.findViewById(R.id.qstn_layout);
            qstnNbr = (TextView) itemView.findViewById(R.id.qstn_nbr);
            qstnStr = (TextView) itemView.findViewById(R.id.qstn_str);
            openAns = (EditText) itemView.findViewById(R.id.open_answer_et);
            optsRadio = (RadioGroup) itemView.findViewById(R.id.opts_rb);
        }
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemAdapter.ViewHolder holder, final int position) {
//        final Item item = itemList.get(position);

        //Adding Question Info
        holder.qstnNbr.setText(itemList.get(position).getQstnNbr() + ".- ");
        holder.qstnStr.setText(itemList.get(position).getQstnStr());

        //Is this item ready to show? (Answer following the order by number of question)
        if (itemList.get(position).getShown() || itemList.get(position).getQstnNbr().equals("1")) {
            holder.qstnLayout.setVisibility(View.VISIBLE);
        } else {
            holder.qstnLayout.setVisibility(View.GONE);
        }


        //Doing validations to show only what is needed

        //Is it an open answer?
        if (itemList.get(position).getOpenAnswerFlag()) {
            holder.openAns.setVisibility(View.VISIBLE);

//            if(itemList.get(position).getQstnNbr().equals("1") || itemList.get(position).getQstnNbr().equals("8")
//                    || itemList.get(position).getQstnNbr().equals("9") || itemList.get(position).getQstnNbr().equals("11")){
//                holder.openAns.setRawInputType(Configuration.KEYBOARD_QWERTY);
//            }

            holder.openAns.setText(itemList.get(position).getOpenAnswer());

            holder.openAns.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
//                        sendMessage();
                        itemList.get(position).setOpenAnswer(holder.openAns.getText().toString());
                        itemList.get(position + 1).setShown(true);
                        notifyDataSetChanged();

                        //Hiding keyboard
                        InputMethodManager imm = (InputMethodManager) v.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                        handled = true;
                    }
                    return handled;
                }
            });

        } else {
            holder.openAns.setVisibility(View.GONE);
        }


        //Is it a multi option answer?
        if (itemList.get(position).getOptions() != null) {
            holder.optsRadio.setVisibility(View.VISIBLE);
            holder.optsRadio.removeAllViews();

            //create radio buttons
            for (int i = 0; i < itemList.get(position).getOptions().size(); i++) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(itemList.get(position).getOptions().get(i).getOpt() + ") " + itemList.get(position).getOptions().get(i).getOptStr());
                radioButton.setId(i);

                if (itemList.get(position).getOptionsChosen().get(i) != null) {
                    radioButton.setChecked(true);
                }

                holder.optsRadio.addView(radioButton);
            }

            holder.optsRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // This will get the radiobutton that has changed in its check state
                    RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);

                    // This puts the value (true/false) into the variable
                    if (checkedRadioButton != null) {
                        boolean isChecked = checkedRadioButton.isChecked();
                        // If the radiobutton that has changed in check state is now checked...
                        if (isChecked) {
                            itemList.get(position).getOptionsChosen().clear();

                            int idx = holder.optsRadio.indexOfChild(checkedRadioButton);
                            itemList.get(position).getOptionsChosen().put(checkedRadioButton.getId(), itemList.get(position).getOptions().get(idx));

                            itemList.get(position + 1).setShown(true);
                            notifyDataSetChanged();
                        } else {
                            holder.optsRadio.clearCheck();
                        }
                    }

                }
            });

        } else {
            holder.optsRadio.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
