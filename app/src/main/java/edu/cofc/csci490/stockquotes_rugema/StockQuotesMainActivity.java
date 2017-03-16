package edu.cofc.csci490.stockquotes_rugema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.cofc.stock.Stock;

public class StockQuotesMainActivity extends AppCompatActivity
{
    //define instance variable for widgets
    private Stock stock;
    private String textInput, symbolVal, nameVal, tradePriceVal, tradTimeVal;
    private String changeVal, rangeVal;
    private EditText symbolTextInput;
    private Button getStockQuote;
    private TextView symbol, name, tradePrice, tradeTime, change, weekRange;
    private final static String SAVED_TEXT = "saved_text";
    private final static String SAVED_NAME = "saved_name";
    private final static String SAVED_CHANGE = "saved_text";
    private final static String SAVED_SYMBOL = "saved_symbol";
    private final static String SAVED_PRICE = "saved_price";
    private final static String SAVED_TIME = "saved_time";
    private final static String SAVED_RANGE = "saved_range";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_quotes_main);

        //attach the widgets
        symbolTextInput = (EditText) findViewById(R.id.symbolNameText);
        symbol = (TextView) findViewById(R.id.symbolTextID);
        name = (TextView) findViewById(R.id.nameTextID);
        tradePrice = (TextView) findViewById(R.id.lastTradePriceID);
        tradeTime = (TextView) findViewById(R.id.lastTradeTimeID);
        change = (TextView) findViewById(R.id.changeTextID);
        weekRange = (TextView) findViewById(R.id.weekRangeTextID);
        getStockQuote = (Button) findViewById(R.id.stockQuoteBtn);


        if (savedInstanceState != null)
        {
            symbolTextInput.setText(savedInstanceState.getString(SAVED_TEXT));
            name.setText(savedInstanceState.getString(SAVED_NAME));
            change.setText(savedInstanceState.getString(SAVED_CHANGE));
            tradePrice.setText(savedInstanceState.getString(SAVED_PRICE));
            weekRange.setText(savedInstanceState.getString(SAVED_RANGE));
            tradeTime.setText(savedInstanceState.getString(SAVED_TIME));
            symbol.setText(savedInstanceState.getString(SAVED_SYMBOL));
        }

        //set the event listener to the button widget
        getStockQuote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                textInput = symbolTextInput.getText().toString();
                new StockQuotesTask().execute();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(textInput, SAVED_TEXT);
        outState.putString(nameVal, SAVED_NAME);
        outState.putString(changeVal, SAVED_CHANGE);
        outState.putString(tradePriceVal, SAVED_PRICE);
        outState.putString(tradTimeVal, SAVED_TIME);
        outState.putString(rangeVal, SAVED_RANGE);
        outState.putString(symbolVal, SAVED_SYMBOL);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        symbolTextInput.setText(savedInstanceState.getString(SAVED_TEXT));
        name.setText(savedInstanceState.getString(SAVED_NAME));
        change.setText(savedInstanceState.getString(SAVED_CHANGE));
        tradePrice.setText(savedInstanceState.getString(SAVED_PRICE));
        weekRange.setText(savedInstanceState.getString(SAVED_RANGE));
        tradeTime.setText(savedInstanceState.getString(SAVED_TIME));
        symbol.setText(savedInstanceState.getString(SAVED_SYMBOL));
    }

    private class StockQuotesTask extends AsyncTask<String, Void, Stock>
    {
        @Override
        protected Stock doInBackground(String... params)
        {
            stock = new Stock(textInput);
            try
            {
                stock.load();
            } catch (Exception ex){
                ex.printStackTrace();
            }

           return stock;
        }

        @Override
        protected void onPostExecute(Stock stock)
        {
            super.onPostExecute(stock);

            if (stock == null)
            {
                Toast.makeText(StockQuotesMainActivity.this, "Error in retrieving stock symbol", Toast.LENGTH_LONG).show();
            } else {
                symbolVal = stock.getSymbol();
                nameVal = stock.getName();
                tradTimeVal = stock.getLastTradeTime();
                tradePriceVal = stock.getLastTradePrice();
                changeVal = stock.getChange();
                rangeVal = stock.getRange();

                //set retrieved value
                symbol.setText(symbolVal);
                name.setText(nameVal);
                tradePrice.setText(tradePriceVal);
                tradeTime.setText(tradTimeVal);
                change.setText(changeVal);
                weekRange.setText(rangeVal);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }
    }
}
