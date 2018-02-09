package com.example.android.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    boolean checkBox1_status;
    boolean checkBox2_status;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Catures if checkbox 1 is clicked
     *
     * @param view checkbox
     */

    public void itemClicked1(View view) {
        CheckBox checkBox1 = (CheckBox) view;
        checkBox1_status = checkBox1.isChecked();
    }

    /**
     * Catures if checkbox 2 is clicked
     *
     * @param view checkbox
     */
    public void itemClicked2(View view) {
        CheckBox checkBox2 = (CheckBox) view;
        checkBox2_status = checkBox2.isChecked();
    }

    /**
     * Order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice(quantity);
        EditText nameEditText = (EditText) findViewById(R.id.name_field);
        name = nameEditText.getText().toString();
        String order = createOrderSummary(name, price);
        String subject = "JustJava order for " + name;
        composeEmail(subject,order);
    }

    /**
     * Creates Email intent to open Email app
     * @param subject of the email
     * @param body which contains order summary
     */

    public void composeEmail(String subject,String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Creates Order Summary when Order button is Clicked
     *
     * @param name  of customer
     * @param price of the order
     */
    private String createOrderSummary(String name, int price) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped cream? " + checkBox1_status;
        priceMessage += "\nAdd Chocolate? " + checkBox2_status;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }



    /**
     * Displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param quantity selected
     * @return the price
     */
    private int calculatePrice(int quantity) {
        int basePrice = 5;
        if (checkBox1_status){
            basePrice+=1;
        }
        if (checkBox2_status){
            basePrice+=2;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setTextColor(Color.parseColor("#4DB6AC"));
        orderSummaryTextView.setText(message);
    }

    /**
     * This method increments the quantity.
     */
    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method decrements the quantity.
     */
    public void decrement(View view) {
        if (quantity<1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }
}
