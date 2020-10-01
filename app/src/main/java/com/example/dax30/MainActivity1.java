package com.example.dax30;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity1 extends AppCompatActivity {

    //Array von einer eigenen Klasse Share.class --> Hat String name und String url und int ID --> mit for-Schleife kann man die Objekte dann erzeugen
    Share[] shares = new Share[]{new Share("Adidas", "Adidas-Aktie/DE000A1EWWW0", "DE000A1EWWW0", "adidas", "11730015", 2), new Share("Allianz", "Allianz-Aktie/DE0008404005", "DE0008404005", "allianz", "322646", 3), new Share("BASF", "BASF-Aktie/DE000BASF111", "DE000BASF111", "basf", "11450563", 4), new Share("Bayer", "Bayer-Aktie/DE000BAY0017", "DE000BAY0017", "bayer", "10367293", 5), new Share("Beiersdorf", "Beiersdorf-Aktie/DE0005200000", "DE0005200000", "beiersdorf", "324660", 6), new Share("BMW-St", "BMW-St-Aktie/DE0005190003", "DE0005190003", "bmw", "324410", 7), new Share("Continental", "Continental-Aktie/DE0005439004", "DE0005439004", "continental", "327800", 8), new Share("Covestro", "Covestro-Aktie/DE0006062144", "DE0006062144", "covestro", "29178035", 9), new Share("Daimler", "Daimler-Aktie/DE0007100000", "DE0007100000", "daimler", "945657", 10), new Share("Delivery-Hero", "Delivery-Hero-Aktie/DE000A2E4K43", "DE000A2E4K43", "delivery_hero", "37200572", 11), new Share("Deutsche-Bank", "Deutsche-Bank-Aktie/DE0005140008", "DE0005140008", "deutsche_bank", "829257", 12), new Share("Deutsche-Boerse", "Deutsche-Boerse-Aktie/DE0005810055", "DE0005810055", "deutsche_boerse", "1177233", 13), new Share("Deutsche-Post", "Deutsche-Post-Aktie/DE0005552004", "DE0005552004", "deutsche_post", "1124244", 14), new Share("Deutsche-Telekom", "Deutsche-Telekom-Aktie/DE0005557508", "DE0005557508", "deutsche_telekom", "1026592", 15), new Share("Deutsche-Wohnen", "Deutsche-Wohnen-Aktie/DE000A0HN5C6", "DE000A0HN5C6", "deutsche_wohnen", "2454186", 16), new Share("EON", "EON-Aktie/DE000ENAG999", "DE000ENAG999", "eon", "4334819", 17), new Share("Fresenius-Medical-Care", "Fresenius-Medical-Care-Aktie/DE0005785802", "DE0005785802", "fresenius_medical_care", "520878", 18), new Share("Fresenius", "Fresenius-Aktie/DE0005785604", "DE0005785604", "fresenius", "332902", 19), new Share("HeidelbergCement", "HeidelbergCement-Aktie/DE0006047004", "DE0006047004", "heidelbergcement", "335740", 20), new Share("Henkel Vz.", "Henkel-Vz-Aktie/DE0006048432", "DE0006048432", "henkel_vz", "335910", 21), new Share("Infineon", "Infineon-Aktie/DE0006231004", "DE0006231004", "infineon", "1038049", 22), new Share("Linde-PLC", "Linde-PLC-Aktie/IE00BZ12WP82", "IE00BZ12WP82", "linde", "37962490", 23), new Share("Merck-KGaA", "Merck-KGaA-Aktie/DE0006599905", "DE0006599905", "merck", "412799", 24), new Share("MTU-Aero-Engines", "MTU-Aero-Engines-Aktie/DE000A0D9PT0", "DE000A0D9PT0", "mtu", "2166689", 25), new Share("Muenchener-Rueck", "Muenchener-Rueck-Aktie/DE0008430026", "DE0008430026", "munich_re", "341960", 26), new Share("RWE-St", "RWE-St-Aktie/DE0007037129", "DE0007037129", "rwe", "1158883", 27), new Share("SAP", "SAP-Aktie/DE0007164600", "DE0007164600", "sap", "345952", 28), new Share("Siemens", "Siemens-Aktie/DE0007236101", "DE0007236101", "siemens", "827766", 29), new Share("Volkswagen-Vz", "Volkswagen-Vz-Aktie/DE0007664039", "DE0007664039", "volkswagen_vz", "352781", 30), new Share("Vonovia", "Vonovia-Aktie/DE000A1ML7J1", "DE000A1ML7J1", "vonovia", "21644750", 31)};

    //bei Erzeugung der TextViews wird ein Mapping Eintrag hinzugefügt, damit man weiß, welcher TextView zu welchem Share gehört
    Map<Share, TextView> textViewMap = new HashMap<>();

    //Bei For-Schleife für Https-Requests durch HashMap laufen und nicht durch Shares-Array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        final LinearLayout sharesContainer = (LinearLayout) findViewById(R.id.sharesContainer);

        //Layout der Firmenbezeichnungen
        LinearLayout.LayoutParams shareNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        shareNameParams.setMargins(25, 25, 25, 25);

        //Layout des Preises
        LinearLayout.LayoutParams shareParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        shareParams.setMargins(25, 25, 25, 25);

        for (final Share share : shares) {
            //Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            //Create TextView
            final TextView shareName = new TextView(this);
            shareName.setText(share.getName());
            shareName.setTextColor(Color.parseColor("#000000"));
            shareName.setTypeface(shareName.getTypeface(), Typeface.BOLD);
            shareName.setTextSize(20);
            shareName.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
            ll.addView(shareName, shareNameParams);

            //Create TextView
            TextView sharePrice = new TextView(this);
            sharePrice.setText("aktueller Kurs");
            sharePrice.setTextColor(Color.parseColor("#000000"));
            sharePrice.setTypeface(sharePrice.getTypeface(), Typeface.BOLD_ITALIC);
            sharePrice.setTextSize(20);
            sharePrice.setGravity(View.TEXT_ALIGNMENT_VIEW_START);
            ll.addView(sharePrice, shareParams);

            //Create Mapping
            textViewMap.put(share, sharePrice);

            sharesContainer.addView(ll, shareParams);

            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity1.this, DetailedActivity.class);
                    intent.putExtra("SHARE_NAME", share.getName());
                    intent.putExtra("SHARE_URL", share.getUrl());
                    intent.putExtra("SHARE_ISIN", share.getIsin());
                    intent.putExtra("SHARE_URLDETAIL", share.getUrlDetail());
                    intent.putExtra("SHARE_VALOR", share.getValor());
                    intent.putExtra("SHARE_NUMMER", share.getNummer());
                    startActivity(intent);
                }
            });
        }


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    getUpdateSharePrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000 * 5);


    }

    private void getUpdateSharePrice() throws IOException {

        //if Abfrage, bei welcher dann die URL entschieden wird je nachdem auf welchem Tab man sich befindet
        //final Document doc = Jsoup.connect("https://www.finanzen.net/aktien/mdax-realtimekurse").header("Cache-control", "no-cache").post();
        //final Document doc = Jsoup.connect("https://www.finanzen.net/aktien/sdax-realtimekurse").header("Cache-control", "no-cache").post();
        //final Document doc = Jsoup.connect("https://www.finanzen.net/aktien/tecdax-realtimekurse").header("Cache-control", "no-cache").post();

        final Document doc = Jsoup.connect("https://www.finanzen.net/aktien/dax-realtimekurse").header("Cache-control", "no-cache").post();

        for (final Share share : shares) {


            Elements select = doc.select("#realtime_chart_list > div.table-responsive.relative > table > tbody > tr:nth-child(" + share.getNummer() + ") > td:nth-child(6) > div > span");
            final String preis = select.text();

            final TextView priceView = textViewMap.get(share);
            assert priceView != null;

            final String preis1 = ((String) priceView.getText()).replace("\u20ac", "");

            char c = '.';

            final String preisNeu = preis.replace(',', c);

            final String preis1Neu = preis1.replace(',', c);


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    priceView.setText(preis + "\u20ac");

                    if (!preis1Neu.equals("aktueller Kurs")) {

                        if (Double.parseDouble(preis1Neu) > Double.parseDouble(preisNeu)) {
                            priceView.setTextColor(Color.parseColor("#269b00"));

                        } else if (Double.parseDouble(preis1Neu) < Double.parseDouble(preisNeu)) {
                            priceView.setTextColor(Color.parseColor("#f21600"));

                        } else {
                            priceView.setTextColor(Color.parseColor("#000000"));
                        }
                    }

                }
            });


        }
    }
}