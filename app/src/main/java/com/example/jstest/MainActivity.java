package com.example.jstest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.webkit.JavascriptInterface;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webview = (WebView)findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new WebAppInterface(this), "Android");

        String unencodedHtml = "<script type=\"text/javascript\">\n" +
                "let result = 5; result = result + 20; Android.showText(result.toString());" +
                "</script>";
        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(), Base64.NO_PADDING);
        webview.loadData(encodedHtml, "text/html", "base64");
    }

    public class WebAppInterface {
        Context mContext;
        TextView text = (TextView)findViewById(R.id.text);

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showText(String text) {
            this.text.setText(text);
        }
    }
}