package com.example.android.justjava;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    boolean checkBox1_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void itemClicked(View view){
        CheckBox checkBox1 = (CheckBox) view;
        checkBox1_status = checkBox1.isChecked();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice(quantity);
        displayMessage(createOrderSummary(price));
    }

    private String createOrderSummary(int price){
        String priceMessage = "Name: Shaunak Jani"+"\nAdd Whipped cream? "+checkBox1_status+"\nQuantity: "+quantity+"\nTotal: $"+ price + "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity) {
        int price = quantity * 5;
        return price;
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
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method decrements the quantity.
     */
    public void decrement(View view) {
        quantity--;
        displayQuantity(quantity);
    }
}
