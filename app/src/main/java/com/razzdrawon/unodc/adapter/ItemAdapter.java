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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.razzdrawon.unodc.R;
import com.razzdrawon.unodc.activity.FormActivity;
import com.razzdrawon.unodc.model.Item;
import com.razzdrawon.unodc.model.Option;

import java.util.List;

/**
 * Created by mapadi3 on 03/07/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    Context context;
    List<Item> itemList, copyItemList;
    TextView.OnEditorActionListener mTextViewListener;

    public ItemAdapter(Context context, List<Item> itemList, List<Item> copyItemList) {
        this.context = context;
        this.itemList = itemList;
        this.copyItemList = copyItemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout qstnLayout, optsLayout, detailsLayout;
        public TextView qstnNbr, qstnStr;
        public EditText openAns, openAnsDetails;
        public RadioGroup optsRadio;
        public Spinner optsDetailsSpin;
        public Button finishBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            qstnLayout = (LinearLayout) itemView.findViewById(R.id.qstn_layout);
            optsLayout = (LinearLayout) itemView.findViewById(R.id.opts_layout);
            detailsLayout = (LinearLayout) itemView.findViewById(R.id.details_layout);

            qstnNbr = (TextView) itemView.findViewById(R.id.qstn_nbr);
            qstnStr = (TextView) itemView.findViewById(R.id.qstn_str);

            openAns = (EditText) itemView.findViewById(R.id.open_answer_et);
            optsRadio = (RadioGroup) itemView.findViewById(R.id.opts_rb);

            openAnsDetails = (EditText) itemView.findViewById(R.id.details_et);
            optsDetailsSpin = (Spinner) itemView.findViewById(R.id.det_opts_spn);

            finishBtn = (Button) ((FormActivity)context).findViewById(R.id.finish_btn);

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
//        if (itemList.get(position).getShown() || itemList.get(position).getQstnNbr().equals("1")) {
//            holder.qstnLayout.setVisibility(View.VISIBLE);
//        } else {
//            holder.qstnLayout.setVisibility(View.GONE);
//        }


        //******************* Is it an open answer? **********************
        //******************* Set visible edittext, place listener to make sure it is answered, get the string to store it, make next question visible **********************

        if (itemList.get(position).getOpenAnswerFlag()) {
            holder.openAns.setVisibility(View.VISIBLE);
            holder.openAns.setText(itemList.get(position).getOpenAnswer());

            holder.openAns.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                        itemList.get(position).setOpenAnswer(holder.openAns.getText().toString());

                        itemList.add(copyItemList.get(position + 1));
//                        itemList.get(position + 1).setShown(true);

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


        //******************* Is it a multi option answer? **********************
        //******************* Set visible radiogroup, place listener to make sure it is answered, get option to store it, make next question visible **********************

        if (itemList.get(position).getOptions() != null) {
            holder.optsRadio.removeAllViews();
            holder.optsRadio.clearCheck();
            holder.optsLayout.setVisibility(View.VISIBLE);
            holder.optsRadio.setVisibility(View.VISIBLE);

            final Boolean[] hasDetails = {false};

            //create radio buttons -- initializing what is already answered
            for (int i = 0; i < itemList.get(position).getOptions().size(); i++) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(itemList.get(position).getOptions().get(i).getOpt() + ") " + itemList.get(position).getOptions().get(i).getOptStr());
                radioButton.setId(i);

                // Check if it was choosen
                if (itemList.get(position).getOptions().get(i).getChosen()) {
                    radioButton.setChecked(true);

                    if(itemList.get(position).getOptions().get(i).getOpenAnswerFlag()) {
                        hasDetails[0] = true;
                        holder.openAnsDetails.setVisibility(View.VISIBLE);
                        holder.openAnsDetails.setText(itemList.get(position).getOptions().get(i).getOpenAnswer());
                    }
                    else {
                        holder.openAnsDetails.setVisibility(View.GONE);
                    }

                    if(itemList.get(position).getOptions().get(i).getOptions() != null){
                        hasDetails[0] = true;
                        holder.optsDetailsSpin.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.optsDetailsSpin.setVisibility(View.GONE);
                    }


                }

                holder.optsRadio.addView(radioButton);
            }

            setOnEnter(holder.openAnsDetails, position, holder.optsRadio.getCheckedRadioButtonId(), holder);

            holder.optsRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // This will get the radiobutton that has changed in its check state
                    RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);

                    // This puts the value (true/false) into the variable
                    if (checkedRadioButton != null) {
                        boolean isChecked = checkedRadioButton.isChecked();
                        // If the radiobutton that has changed in check state is now checked...
                        if (isChecked) {

                            for (Option opt: itemList.get(position).getOptions()) {
                                opt.setChosen(false);
                            }

                            final int idx = holder.optsRadio.indexOfChild(checkedRadioButton);
                            itemList.get(position).getOptions().get(idx).setChosen(true);

                            //************* if the option chosen has to specify somthing else... ***************

                            //************* if an open answer is needed **************
                            if (itemList.get(position).getOptions().get(idx).getOpenAnswerFlag()) {
                                hasDetails[0] = true;
                                holder.openAnsDetails.setVisibility(View.VISIBLE);

                            } else {
                                holder.openAnsDetails.setVisibility(View.GONE);
                            }

                            //************* if a multi opt is needed **************
                            if (itemList.get(position).getOptions().get(idx).getOptions() != null) {
                                hasDetails[0] = true;
                                holder.optsDetailsSpin.setVisibility(View.VISIBLE);
                            } else {
                                holder.optsDetailsSpin.setVisibility(View.GONE);
                            }



                            if(copyItemList.size() > itemList.size()){
                                itemList.add(copyItemList.get(position + 1));
                            }
                            else {
                                holder.finishBtn.setVisibility(View.VISIBLE);
                            }

                            notifyDataSetChanged();


                        } else {
                            holder.optsRadio.clearCheck();
                        }
                    }

                }
            });

//            if (copyItemList.size() == itemList.size()) {
//                holder.finishBtn.setVisibility(View.VISIBLE);
//            }

            if(hasDetails[0])
                holder.detailsLayout.setVisibility(View.VISIBLE);
            else
                holder.detailsLayout.setVisibility(View.GONE);

        } else {
            holder.optsLayout.setVisibility(View.GONE);
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

    private void setOnEnter(final EditText et, final int position, final int idx, final ItemAdapter.ViewHolder holder){
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {

                    itemList.get(position).getOptions().get(idx).setOpenAnswer(holder.openAnsDetails.getText().toString());

                    //Hiding keyboard
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    handled = true;
                }
                return handled;
            }
        });
    }

}
