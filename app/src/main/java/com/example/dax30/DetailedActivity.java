package com.example.dax30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;

public class DetailedActivity extends AppCompatActivity {

    private String name;
    private String url;
    private String urlDetail;
    private String valor;
    private int nummer;
    private TextView sharePrice;
    private TextView shareBps;
    private TextView shareEps;
    private TextView shareFsv;
    private TextView shareChange;
    private TextView shareKgv;
    private ImageView shareNews;

    private ImageView sharePic;

    private RadioGroup radioGroup;
    private RadioButton radioButtonIntraday;
    private RadioButton radioButtonOneWeek;
    private RadioButton radioButtonOneMonth;
    private RadioButton radioButtonSixMonths;
    private RadioButton radioButtonOneYear;
    private RadioButton radioButtonThreeYears;
    private RadioButton radioButtonFiveYears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        sharePrice = (TextView) findViewById(R.id.sharePrice);
        shareBps = (TextView) findViewById(R.id.shareBps);
        shareEps = (TextView) findViewById(R.id.shareEps);
        shareFsv = (TextView) findViewById(R.id.shareFsv);
        shareChange = (TextView) findViewById(R.id.shareChange);
        shareKgv = (TextView) findViewById(R.id.shareKgv);
        sharePic = (ImageView) findViewById(R.id.sharePic);
        shareNews = (ImageView) findViewById(R.id.shareNews);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButtonIntraday = (RadioButton) findViewById(R.id.radioButtonIntraday);
        radioButtonOneWeek = (RadioButton) findViewById(R.id.radioButtonOneWeek);
        radioButtonOneMonth = (RadioButton) findViewById(R.id.radioButtonOneMonth);
        radioButtonSixMonths = (RadioButton) findViewById(R.id.radioButtonSixMonth);
        radioButtonOneYear = (RadioButton) findViewById(R.id.radioButtonOneYear);
        radioButtonThreeYears = (RadioButton) findViewById(R.id.radioButtonThreeYear);
        radioButtonFiveYears = (RadioButton) findViewById(R.id.radioButtonFiveYear);

        name = getIntent().getStringExtra("SHARE_NAME");
        url = getIntent().getStringExtra("SHARE_URL");
        urlDetail = getIntent().getStringExtra("SHARE_URLDETAIL");
        valor = getIntent().getStringExtra("SHARE_VALOR");
        nummer = getIntent().getIntExtra("SHARE_NUMMER", 2);

        ((TextView) findViewById(R.id.shareNameLabel)).setText(getIntent().getStringExtra("SHARE_NAME"));

        getDetailedInfo();

        radioButtonIntraday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String pic = "https://c.finanzen.net/cst/FinanzenDe/chart.aspx?instruments=16," + valor + ",16,814&style=snapshot_mountain_volume_intraday&period=Intraday&hash";
                Picasso.get().load(pic).into(sharePic);
            }
        });

        radioButtonOneWeek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String pic = "https://c.finanzen.net/cst/FinanzenDe/chart.aspx?instruments=16," + valor + ",16,814&style=snapshot_mountain_volume_oneweek&period=OneWeek&hash";
                Picasso.get().load(pic).into(sharePic);
            }
        });

        radioButtonOneMonth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String pic = "https://c.finanzen.net/cst/FinanzenDe/chart.aspx?instruments=16," + valor + ",16,814&style=snapshot_mountain_volume_onemonth&period=OneMonth&hash";
                Picasso.get().load(pic).into(sharePic);
            }
        });


        radioButtonSixMonths.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String pic = "https://c.finanzen.net/cst/FinanzenDe/chart.aspx?instruments=16," + valor + ",16,814&style=snapshot_mountain_volume_sixmonths&period=SixMonths&hash";
                Picasso.get().load(pic).into(sharePic);
            }
        });

        radioButtonOneYear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String pic = "https://c.finanzen.net/cst/FinanzenDe/chart.aspx?instruments=16," + valor + ",16,814&style=snapshot_mountain_volume_oneyear&period=OneYear&hash";
                Picasso.get().load(pic).into(sharePic);
            }
        });

        radioButtonThreeYears.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String pic = "https://c.finanzen.net/cst/FinanzenDe/chart.aspx?instruments=16," + valor + ",16,814&style=snapshot_mountain_volume_threeyears&period=ThreeYears&hash";
                Picasso.get().load(pic).into(sharePic);
            }
        });

        radioButtonFiveYears.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String pic = "https://c.finanzen.net/cst/FinanzenDe/chart.aspx?instruments=16," + valor + ",16,814&style=snapshot_mountain_volume_fiveyears&period=FiveYears&hash";
                Picasso.get().load(pic).into(sharePic);
            }
        });


        shareNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedActivity.this, NewsActivity.class);
                intent.putExtra("SHARE_NAME", name);
//                intent.putExtra("SHARE_URL", share.getUrl());
//                intent.putExtra("SHARE_ISIN", share.getIsin());
//                intent.putExtra("SHARE_URLDETAIL", share.getUrlDetail());
//                intent.putExtra("SHARE_VALOR", share.getValor());
//                intent.putExtra("SHARE_NUMMER", share.getNummer());
                startActivity(intent);
            }
        });
    }

    private void getDetailedInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.finanzen.net/bilanz_guv/" + urlDetail).get();
                    Document doc1 = Jsoup.connect("https://www.finanzen.net/aktien/dax-realtimekurse").get();

                    Elements select = doc1.select("#realtime_chart_list > div.table-responsive.relative > table > tbody > tr:nth-child(" + nummer + ") > td:nth-child(6) > div > span");
                    final String preis = select.text();

                    Elements selectChange = doc1.select("#realtime_chart_list > div.table-responsive.relative > table > tbody > tr:nth-child(" + nummer + ") > td:nth-child(7) > div > span");
                    final String change = selectChange.text();

                    //hier noch die richtige Dateien einlesen
                    Elements selectBps = doc.select("#bguvform > div > div:nth-child(3) > div.table-responsive > table > tbody > tr:nth-child(1) > td:nth-child(9)");
                    final String bps = selectBps.text();

                    Elements selectEps = doc.select("#bguvform > div > div:nth-child(1) > div.table-responsive > table > tbody > tr:nth-child(1) > td:nth-child(9)");
                    final String eps = selectEps.text();

                    final String pic = "https://c.finanzen.net/cst/FinanzenDe/chart.aspx?instruments=44," + valor + ",44,814&style=snapshot_mountain_volume_intraday&period=Intraday&hash";

                    Elements selectKgv = doc.select("#bguvform > div > div:nth-child(3) > div.table-responsive > table > tbody > tr:nth-child(2) > td:nth-child(9)");
                    final String kgv = selectKgv.text();
                    
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            char c = '.';

                            String bps1 = bps.replace(',', c);
                            String eps1 = eps.replace(',', c);
                            String fSV = String.format("%1.2f", Math.sqrt(22.5 * Double.parseDouble(eps1) * Double.parseDouble(bps1)));
                            fSV = fSV.replace('.', ',');


                            sharePrice.setText(preis + "\u20ac");
                            shareBps.setText(bps);
                            shareEps.setText(eps);
                            shareFsv.setText(fSV);
                            shareChange.setText(change);
                            shareKgv.setText(kgv);

                            String change1 = change.replace(',', c);

                            change1 = change1.substring(0, change.length() - 1);

                            if (Double.parseDouble(change1) >= 0) {
                                shareChange.setTextColor(Color.parseColor("#269b00"));
                            } else {
                                shareChange.setTextColor(Color.parseColor("#f21600"));
                            }

                            Picasso.get().load(pic).into(sharePic);

                            //Farbe grün oder rot, je nachdem ob Kurs höher oder tiefer als vorher
                            //resultAdidas.setTextColor(2);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}