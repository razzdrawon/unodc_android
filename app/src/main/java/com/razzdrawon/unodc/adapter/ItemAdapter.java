package com.razzdrawon.unodc.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.razzdrawon.unodc.R;
import com.razzdrawon.unodc.activity.FormActivity;
import com.razzdrawon.unodc.model.DepOption;
import com.razzdrawon.unodc.model.Item;
import com.razzdrawon.unodc.model.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mapadi3 on 03/07/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    Context context;
    List<Item> itemList;
    List<Item> copyItemList;

    public ItemAdapter(Context context, List<Item> itemList, List<Item> copyItemList) {
        this.context = context;
        this.itemList = itemList;
        this.copyItemList = copyItemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout qstnLayout, optsLayout, detailsLayout, chkBoxLayout;
        public CardView qstnCard;
        public TextView qstnNbr, qstnStr, notAns;
        public EditText openAns, openAnsDetails;
        public RadioGroup optsRadio;
        public Spinner optsDetailsSpin;
        public RecyclerView rv;
        public Button finishBtn;
        public TextView detailsTv;

        public ViewHolder(View itemView) {
            super(itemView);
            qstnLayout = (LinearLayout) itemView.findViewById(R.id.qstn_layout);
            qstnCard = (CardView) itemView.findViewById(R.id.card_view);
            optsLayout = (LinearLayout) itemView.findViewById(R.id.opts_layout);
            detailsLayout = (LinearLayout) itemView.findViewById(R.id.details_layout);
            chkBoxLayout = (LinearLayout) itemView.findViewById(R.id.checkBox_layout);

            qstnNbr = (TextView) itemView.findViewById(R.id.qstn_nbr);
            qstnStr = (TextView) itemView.findViewById(R.id.qstn_str);
            notAns = (TextView) itemView.findViewById(R.id.missing_message);

            openAns = (EditText) itemView.findViewById(R.id.open_answer_et);
            optsRadio = (RadioGroup) itemView.findViewById(R.id.opts_rb);

            detailsTv = (TextView) itemView.findViewById(R.id.detail_tv);
            openAnsDetails = (EditText) itemView.findViewById(R.id.details_et);
            optsDetailsSpin = (Spinner) itemView.findViewById(R.id.det_opts_spn);

            rv = (RecyclerView) ((FormActivity)context).findViewById(R.id.recycler_view);
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

        //Set style for blocked and unblocked items
        //validate if the item is blocked
        validateBlockedItems(position, holder);

        //Adding Question Info
        holder.qstnNbr.setText(itemList.get(position).getQstnNbr() + ". ");
        holder.qstnStr.setText(itemList.get(position).getQstnStr());

        //Initialize all for OpenAnswers
        runEditTextOpenAns(position, holder);

        //Initialize all for CheckBoxes
        runMultiOptsCheck(position, holder);

        //Initialize all for RadioGroup
        runOptsRadio(position, holder);

    }


    private void validateBlockedItems(int position, ViewHolder holder) {
        if(itemList.get(position).getBlocked()) {
            holder.qstnCard.setBackgroundColor(context.getResources().getColor(R.color.card_gray));
            holder.qstnNbr.setTextColor(context.getResources().getColor(R.color.text_gray));
            holder.qstnStr.setTextColor(context.getResources().getColor(R.color.text_gray));
            holder.detailsTv.setTextColor(context.getResources().getColor(R.color.text_gray));
        }
        else {
            holder.qstnCard.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            holder.qstnNbr.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.qstnStr.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.detailsTv.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }


    private void runEditTextOpenAns(final int position, final ViewHolder holder) {
        //******************* Is it an open answer? **********************
        //******************* Set visible edittext, place listener to make sure it is answered, get the string to store it, make next question visible **********************

        if (itemList.get(position).getOpenAnswerFlag()) {
            holder.openAns.setVisibility(View.VISIBLE);
            holder.notAns.setVisibility(View.GONE);
            holder.openAns.setText(itemList.get(position).getOpenAnswer());

            //Listener for EditText (level 1 Main ans)
//            setListenerFirstEditText(position, holder);

            holder.openAns.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                        itemList.get(position).setOpenAnswer(holder.openAns.getText().toString());

                        // Adding the new item and make the question answered
                        if(!itemList.get(position).getAnswered()){
//                            itemList.add(copyItemList.get(itemList.get(position).getQstnNbr()));
                            itemList.add(copyItemList.get(position + 1));
                            itemList.get(position).setAnswered(true);
                        }

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
    }

    private void runMultiOptsCheck(final int position, final ViewHolder holder) {
        //******************* Is it multi opt with CheckBox? **********************
        //******************* Set visible CheckBosLayout, place listener to make sure it is answered, get the string to store it, make next question visible **********************
        if(itemList.get(position).getMaxCheck() != null && itemList.get(position).getOptions() != null){
            holder.chkBoxLayout.setVisibility(View.VISIBLE);
            holder.chkBoxLayout.removeAllViews();

            for(final Option opt: itemList.get(position).getOptions()) {
                CheckBox cb = new CheckBox(context);
                cb.setText(opt.getOpt() + ") " + opt.getOptStr());
                cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

                holder.chkBoxLayout.addView(cb);
                if(opt.getChosen()) {
                    cb.setChecked(true);
                    cb.setTextColor(context.getResources().getColor(R.color.colorAccent));
                }
                else{
                    cb.setChecked(false);
                    cb.setTextColor(context.getResources().getColor(android.R.color.black));
                }

                if(itemList.get(position).getBlocked()) {
                    cb.setEnabled(false);
                    cb.setTextColor(context.getResources().getColor(R.color.text_gray));
                    holder.notAns.setVisibility(View.GONE);
                }


                Boolean isOneChecked = false;

                for (Option o: itemList.get(position).getOptions()) {
                    if(o.getChosen()){
                        isOneChecked = true;
                    }
                }

                if(isOneChecked == false){
                    if(!itemList.get(position).getBlocked()) {
                        holder.notAns.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    holder.notAns.setVisibility(View.GONE);
                }

                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(isChecked) {

                            int numChecks = 0;

                            for (int y = 0; y < holder.chkBoxLayout.getChildCount(); y++) {
                                CheckBox cbCh = (CheckBox)holder.chkBoxLayout.getChildAt(y);
                                if(cbCh.isChecked()){
                                    numChecks++;
                                }
                            }

                            if(numChecks > itemList.get(position).getMaxCheck()) {
                                buttonView.setChecked(false);
                            }
                            else {

                                opt.setChosen(true);
                                if (!itemList.get(position).getAnswered()) {
                                    itemList.get(position).setAnswered(true);
                                    itemList.add(copyItemList.get(itemList.get(position).getQstnNbr()));
                                }

                            }
                        }
                        else {
                            opt.setChosen(false);
                        }

                        notifyDataSetChanged();
                    }
                });
            }
        }
        else {
            holder.chkBoxLayout.setVisibility(View.GONE);
        }
    }

    private void runOptsRadio(int position, ViewHolder holder) {


        //******************* Is it a multi option answer? **********************
        //******************* Set visible radiogroup, place listener to make sure it is answered, get option to store it, make next question visible **********************
        if (itemList.get(position).getOptions() != null && itemList.get(position).getMaxCheck() == null) {
            holder.optsRadio.removeAllViews();
            holder.optsRadio.clearCheck();
            holder.optsLayout.setVisibility(View.VISIBLE);
            holder.optsRadio.setVisibility(View.VISIBLE);
            holder.notAns.setVisibility(View.GONE);



            Boolean hasDetails = false;

            //create radio buttons -- initializing what is already answered for all the options
            for (int i = 0; i < itemList.get(position).getOptions().size(); i++) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(itemList.get(position).getOptions().get(i).getOpt() + ") " + itemList.get(position).getOptions().get(i).getOptStr());
                radioButton.setId(i);
                radioButton.setTextAppearance(context, android.R.style.TextAppearance_Large);

                if(itemList.get(position).getBlocked()) {
                    radioButton.setEnabled(false);
                    radioButton.setTextColor(context.getResources().getColor(R.color.text_gray));
                }

                // Check if it was choosen
                if (itemList.get(position).getOptions().get(i).getChosen()) {
                    radioButton.setChecked(true);
                    radioButton.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
                    //Does it have open answer details? set the text and show ET
                    if(itemList.get(position).getOptions().get(i).getOpenAnswerFlag()) {
                        hasDetails = true;
                        holder.openAnsDetails.setVisibility(View.VISIBLE);
                        holder.detailsTv.setVisibility(View.VISIBLE);
                        holder.openAnsDetails.setText(itemList.get(position).getOptions().get(i).getOpenAnswer());
                    }
                    else {
                        holder.openAnsDetails.setVisibility(View.GONE);
                    }


                    //Does it have dependent options? Populate spinner
                    if(itemList.get(position).getOptions().get(i).getOptions() != null){
                        hasDetails = true;
                        holder.optsDetailsSpin.setVisibility(View.VISIBLE);
                        holder.detailsTv.setVisibility(View.VISIBLE);

                        List<DepOption> optionsForSpin = new ArrayList<>();
                        DepOption optDefault = new DepOption(0,"Seleccione una opción");
                        optionsForSpin.add(optDefault);
                        optionsForSpin.addAll(itemList.get(position).getOptions().get(i).getOptions());

                        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                R.layout.spinner_dropdown_layout, optionsForSpin);
                        holder.optsDetailsSpin.setAdapter(spinnerArrayAdapter);

                        if(itemList.get(position).getOptions().get(i).getDependentChosen() != null)
                            holder.optsDetailsSpin.setSelection(itemList.get(position).getOptions().get(i).getDependentChosen());

                    }
                    else {
                        holder.optsDetailsSpin.setVisibility(View.GONE);
                    }

                }
                holder.optsRadio.addView(radioButton);
            }

            if(hasDetails) {
                holder.detailsLayout.setVisibility(View.VISIBLE);
                holder.detailsTv.setVisibility(View.VISIBLE);
            }
            else {
                holder.detailsLayout.setVisibility(View.GONE);
                holder.detailsTv.setVisibility(View.GONE);
            }

            //Listener for Main options (level 1)
            setListenerRadioGroup(position, holder);
            //Listener for Text area to specify  answer (level 2 Details)
            setListenerDetailsEdtTxt(position, holder);
            //Listener for dependent options (level 2 Details)
            setListenerDetailsSpin(position, holder);

        } else {
            holder.optsLayout.setVisibility(View.GONE);
            holder.optsRadio.setVisibility(View.GONE);

        }
    }


    private void setListenerRadioGroup(final int position, final ViewHolder holder) {
        //Listener for RadioGroup
        holder.optsRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);

                // This puts the value (true/false) into the variable
                if (checkedRadioButton != null) {
                    boolean isChecked = checkedRadioButton.isChecked();
                    // If the radiobutton that has changed in check state is now checked...
                    if (isChecked) {
                        //Cleaning the last option in the model
                        for (Option opt: itemList.get(position).getOptions()) {
                            opt.setChosen(false);
                        }
                        final int idx = holder.optsRadio.indexOfChild(checkedRadioButton);
                        RadioButton rb = (RadioButton)  holder.optsRadio.getChildAt(idx);
                        rb.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));

                        itemList.get(position).getOptions().get(idx).setChosen(true);

                        Boolean hasDependentText = false;
                        Boolean hasDependentOpt = false;

                        //*********************************** if the option chosen has to specify somthing else... *****************************

                        //************* if an open answer is needed **************
                        if (itemList.get(position).getOptions().get(idx).getOpenAnswerFlag()) {
                            hasDependentText = true;
                            holder.openAnsDetails.setVisibility(View.VISIBLE);
                            holder.detailsTv.setVisibility(View.VISIBLE);

                        } else {
                            holder.openAnsDetails.setVisibility(View.GONE);
                        }

                        //************* if a multi opt is needed **************
                        if (itemList.get(position).getOptions().get(idx).getOptions() != null) {
                            hasDependentOpt = true;

                            List<DepOption> optionsForSpin = new ArrayList<>();
                            DepOption optDefault = new DepOption(0,"Seleccione una opción");
                            optionsForSpin.add(optDefault);
                            optionsForSpin.addAll(itemList.get(position).getOptions().get(idx).getOptions());

                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    R.layout.spinner_layout, optionsForSpin);

//                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                            holder.optsDetailsSpin.setAdapter(spinnerArrayAdapter);

                            holder.optsDetailsSpin.setVisibility(View.VISIBLE);
                            holder.detailsTv.setVisibility(View.VISIBLE);
                        } else {
                            holder.optsDetailsSpin.setVisibility(View.GONE);
                        }



                        //When it was already answered
                        if (itemList.get(position).getAnswered() && position < copyItemList.size() - 1) {

                            //If we need to block some questions
                            if(itemList.get(position).getOptions().get(idx).getBlocks() != null){
                                for (Integer block: itemList.get(position).getOptions().get(idx).getBlocks()) {

                                    //validating the items to block if they exist just block them or if they dont, create them as blocked
                                    if(itemList.size() >= block){
                                        itemList.get(block - 1).setBlocked(true);
                                        itemList.get(block - 1).setAnswered(true);
                                    }
                                    else {
                                        itemList.add(copyItemList.get(block - 1));
                                        itemList.get(itemList.size() - 1).setAnswered(true);
                                        itemList.get(itemList.size() - 1).setBlocked(true);
                                    }

                                }
                                //validating the item after blocks if it exists just block it or if it doesn't, create it as non-blocked
                                if(itemList.size() > (position + itemList.get(position).getOptions().get(idx).getBlocks().size() + 1)){
                                    itemList.get(position + itemList.get(position).getOptions().get(idx).getBlocks().size() + 1);
                                }
                                else {
//                                    if(!hasDependentText && !hasDependentOpt) {
                                        itemList.add(copyItemList.get(position + itemList.get(position).getOptions().get(idx).getBlocks().size() + 1));
//                                    }
                                }

                            }


                            //If we need to enable some questions
                            if(itemList.get(position).getOptions().get(idx).getEnables() != null){
                                for (Integer enable: itemList.get(position).getOptions().get(idx).getEnables()) {

                                    //validating the items to block if they exist just enable them or if they dont, create them as blocked
                                    if(itemList.get(enable - 1) != null){
                                        itemList.get(enable - 1).setBlocked(false);
                                    }
                                    else {
                                        itemList.add(copyItemList.get(enable - 1));
                                        itemList.get(itemList.size() - 1).setAnswered(true);
                                        itemList.get(itemList.size() - 1).setBlocked(false);
                                    }

                                }
                                //validating the item after blocks if it exists just enable it or if it doesn't, create it as non-blocked
                                if(itemList.size() > (position + itemList.get(position).getOptions().get(idx).getEnables().size() + 1)){
                                    itemList.get(position + itemList.get(position).getOptions().get(idx).getEnables().size() + 1);
                                }
                                else {
//                                    if(!hasDependentText && !hasDependentOpt) {
                                        itemList.add(copyItemList.get(position + itemList.get(position).getOptions().get(idx).getEnables().size() + 1));
//                                    }
                                }

                            }

                        }


                        // Adding the new items (if needed) when the item was selected for the first time
                        if(!itemList.get(position).getAnswered() && position < copyItemList.size() -1){

                            //If we need to block some questions
                            if(itemList.get(position).getOptions().get(idx).getBlocks() != null){
                                for (Integer block: itemList.get(position).getOptions().get(idx).getBlocks()) {
                                    itemList.add(copyItemList.get(block - 1));
                                    itemList.get(itemList.size() - 1).setAnswered(true);
                                    itemList.get(itemList.size() - 1).setBlocked(true);
                                }
                                //This is the new questio to add to the list
                                if(!hasDependentText && !hasDependentOpt) {
                                    itemList.add(copyItemList.get(itemList.size()));
                                    itemList.get(position).setAnswered(true);
                                }
                            }
                            else {
                                //This is the new questio to add to the list
                                if(!hasDependentText && !hasDependentOpt) {
                                    itemList.add(copyItemList.get(position + 1));
                                    itemList.get(position).setAnswered(true);
                                }
                            }

                        }

                        // Just in case this is the last question:
                        if ((itemList.get(position).getQstnNbr()) == copyItemList.size()){

                            Menu menu = ((FormActivity)context).getMenu();
                            menu.getItem(0).setVisible(true);
                            holder.finishBtn.setVisibility(View.VISIBLE);
                        }

                        notifyDataSetChanged();

                    } else {
                        holder.optsRadio.clearCheck();
                    }
                }

            }
        });
    }

    private void setListenerDetailsEdtTxt(final int position, final ViewHolder holder){
        holder.openAnsDetails.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {

                    itemList.get(position).getOptions().get(holder.optsRadio.getCheckedRadioButtonId()).setOpenAnswer(holder.openAnsDetails.getText().toString());


                    if(!itemList.get(position).getAnswered()){
                        itemList.add(copyItemList.get(position + 1));
                        itemList.get(position).setAnswered(true);
                    }

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

    private void setListenerDetailsSpin(final int position, final ViewHolder holder){
        holder.optsDetailsSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positionSpin, long id) {

                if(positionSpin != 0) {

                    itemList.get(position).getOptions().get(holder.optsRadio.getCheckedRadioButtonId()).setDependentChosen(positionSpin);

                    if (!itemList.get(position).getAnswered()) {
                        itemList.add(copyItemList.get(position + 1));
                        itemList.get(position).setAnswered(true);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<Item> getItemList() {
        return itemList;
    }

}