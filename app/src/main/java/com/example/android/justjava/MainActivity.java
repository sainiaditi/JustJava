/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */
package com.example.android.justjava;

import android.content.Intent;
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

    int quantity=1;

    /**
     * This method displays the given text on the screen.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the order button is clicked.
     * @param view
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText text = (EditText) findViewById(R.id.edit_view);
        String value = text.getText().toString();

        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage= createOrderSummary(value,price,hasChocolate,hasWhippedCream,quantity);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Create Order Summary
     * @param name on the order
     * @return text Summary
     */

    public String createOrderSummary(String name,int price,boolean addChocolate,boolean addWhippedCream,int quantity){
        String priceMessage= getString(R.string.order_summary_name,name);
        priceMessage +="\n"+ getString(R.string.order_summary_whipped_cream,addWhippedCream);
        priceMessage +="\n"+getString(R.string.order_summary_chocolate,addChocolate);
        priceMessage +="\n"+getString(R.string.order_summary_quantity,quantity);
        priceMessage +="\n"+getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(price));
        priceMessage +="\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     *@param addChocolate is selected or not and add price according to it
     *@param addwhippedCream is selected or not and add price according to it
     *@return new updated price
     */
    private int calculatePrice(boolean addwhippedCream, boolean addChocolate) {
        int basePrice=10;
        if(addwhippedCream){
            basePrice += 2;
        }

        if(addChocolate){
            basePrice += 5;
        }

        return quantity*basePrice;
    }

    /**
     * Increment the order
     * @param view
     */

    public void increment(View view) {
        if(quantity == 100){
            Toast.makeText(this ,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }

    /**
     * Decrement the order
     * @param view
     */

    public void decrement(View view) {
        if(quantity == 1){
            Toast.makeText(this ,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     * @param number
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}