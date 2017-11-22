package com.company.sticksnsushi.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.sticksnsushi.R;
import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;


public class PaymentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedState) {
        ViewGroup PaymentView = (ViewGroup) layoutInflater.inflate(R.layout.fragment_payment, container, false);

        return PaymentView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CardForm cardForm = (CardForm) view.findViewById(R.id.cardform);
        Button btnPay = (Button) view.findViewById(R.id.btn_pay);
        TextView txtDesAmount = (TextView) view.findViewById(R.id.payment_amount);
        TextView txtAmount = (TextView) view.findViewById(R.id.payment_amount_holder);

        TextView txtCardName = (TextView) view.findViewById(R.id.card_preview_name);
        TextView txtCardNumber = (TextView) view.findViewById(R.id.card_preview_number);
        TextView txtCardExpire = (TextView) view.findViewById(R.id.card_preview_expiry);

        EditText txtExpireDate = (EditText) view.findViewById(R.id.expiry_date);
        EditText txtName = (EditText) view.findViewById(R.id.card_name);
        EditText txtNumber = (EditText) view.findViewById(R.id.card_number);
        EditText txtCVC = (EditText) view.findViewById(R.id.cvc);

        int redColor = getResources().getColor(R.color.colorAccent);


        btnPay.setText("KØB");
        btnPay.setBackgroundColor(redColor);

        txtCardName.setText("Kortholders navn");
        txtCardNumber.setText("Kortnummer");
        txtCardExpire.setText("MM/DD");

        txtAmount.setText("Beløb");
        txtAmount.setTextColor(Color.WHITE);

        txtDesAmount.setText("199 kr.");
        txtDesAmount.setTextColor(Color.WHITE);

        txtName.setHint("Kortholders navn");
        txtName.setHintTextColor(Color.WHITE);
        txtName.setTextColor(Color.WHITE);

        //Test kortnummer: 4026 2069 1846 9714
        txtNumber.setHint("Kortnummer");
        txtNumber.setHintTextColor(Color.WHITE);
        txtNumber.setTextColor(Color.WHITE);


        txtExpireDate.setHint("MM/DD");
        txtExpireDate.setHintTextColor(Color.WHITE);
        txtExpireDate.setTextColor(Color.WHITE);


        txtCVC.setHint("CVC/CCV");
        txtCVC.setHintTextColor(Color.WHITE);
        txtCVC.setTextColor(Color.WHITE);


        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(getActivity(), "Navn: " + card.getName(), Toast.LENGTH_LONG
                ).show();
                selectNavItemFragment(new ConfirmationFragment());
            }
        });

    }
    /**
     * selectNavItemFragment does fragment transaction while we can simply give it an argument of fragment
     * in this case it is optimal to have this method
     * @param fragment
     */
    private void selectNavItemFragment(Fragment fragment) {
        FragmentTransaction ft;
        ft = getFragmentManager().beginTransaction();
        // XML files for animation are downloaded from internet
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(R.id.activity_checkout_frame, fragment);
        ft.commit();
    }
}

