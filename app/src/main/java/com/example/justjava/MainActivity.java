package com.example.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText customerName = (EditText) findViewById(R.id.name_text_view);
        String name = customerName.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolaateCheckBOx = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolaateCheckBOx.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        /**
         * when user click order , the mail apps open and order summary print in mail body
         */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_sub, name));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // this method increment the quantity
    public void increment(View view) {
        quantity++;
        if (quantity == 16) {
            Toast.makeText(this, getText(R.string.increment_toast), Toast.LENGTH_SHORT).show();
            quantity--;
            return;
        }
        display(quantity);
    }

    //this method decrease the quantity
    public void decrement(View view) {
        quantity--;
        if (quantity == 0) {
            Toast.makeText(this, getText(R.string.decrement_toast), Toast.LENGTH_SHORT).show();
            quantity++;
            return;
        }
        display(quantity);
    }

    /**
     * this method calculate the price
     *
     * @return it return the price
     */
    public int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }
        price = price * quantity;
        return price;
    }

    /**
     * Create summary of order
     *
     * @param price           hold total price
     * @param addWhippedCream hold whether whipped cream checkbox marked or not
     * @param addChocolate    hold if chocolate checkbox checked or not
     * @param name            store the name of the customer
     * @return it return the message to show in order summary
     */
    @SuppressLint("StringFormatMatches")
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = getString(R.string.order_summary_name, name);
        if (addWhippedCream) {
            priceMessage += getText(R.string.order_summary_whippedcream);
        }
        if (addChocolate) {
            priceMessage += getText(R.string.order_summary_chocolate);
        }
        priceMessage += getString(R.string.order_summary_quantity, quantity);
        priceMessage += getString(R.string.order_summary_price, price);
        return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}