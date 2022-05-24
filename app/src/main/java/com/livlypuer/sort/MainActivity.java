package com.livlypuer.sort;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import im.dacer.androidcharts.BarView;

public class MainActivity extends AppCompatActivity {

    BarView barView;
    ProgressBar progressBar;
    TextView textView;
    Button shuffleButton;
    final int count = 15;
    ArrayList<Integer> dataList = new ArrayList<Integer>() {
        {
            for (int i = 0; i < count; i++){
                add(i);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        textView.setText("Массив отсортирован");
        progressBar.setVisibility(View.INVISIBLE);
        shuffleButton = findViewById(R.id.shuffle);
        barView = findViewById(R.id.bubleBarView);
        barView.setBottomTextList(toListString(dataList));
        barView.setDataList(dataList, count);

        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Collections.shuffle(dataList);
                barView.setBottomTextList(toListString(dataList));
                barView.setDataList(dataList, count);
                shuffleButton.setEnabled(false);
                // Init
                final int[] i = {0};
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (shuffleButton.isEnabled()){
                            return;
                        }
                        if (SortBuble()){
                            progressBar.setVisibility(View.INVISIBLE);
                            shuffleButton.setEnabled(true);
                            textView.setText("Массив отсортирован");
                        }else{
                            i[0] += 1;
                            textView.setText("Идет сортировка пузырьком" + repeat(i[0] % 4, "."));
                            handler.postDelayed(this, 500);
                        }
                    }
                };
                handler.postDelayed(runnable, 1000);

            }
        });
    }

    protected ArrayList<String> toListString(ArrayList<Integer> list) {
        return list.stream().map(Object::toString).collect(Collectors.toCollection(ArrayList::new));
    }

    public static void sort(ArrayList<Integer> array) {

    }
    public String repeat(int i, String str) {
        String result = "";
        for( int u = 0; u < i; u++ ) {
            result = result + str;
        }
        return result;
    }
    protected boolean SortBuble() {
        for (int i = 0; i < dataList.size() - 1; i++) {
            // внутренний цикл прохода
            for (int j = dataList.size() - 1; j > i; j--) {
                if (dataList.get(j - 1) > dataList.get(j)) {
                    int tmp = dataList.get(j - 1);
                    dataList.set(j - 1, dataList.get(j));
                    dataList.set(j, tmp);
                    barView.setBottomTextList(toListString(dataList));
                    barView.setDataList(dataList, count);
                    return false;
                }
            }
        }
        return true;

//        new Thread() {
//            public void run() {
//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // i - номер прохода
//                        for (int i = 0; i < dataList.size() - 1; i++) {
//                            // внутренний цикл прохода
//                            for (int j = dataList.size() - 1; j > i; j--) {
//                                if (dataList.get(j - 1) > dataList.get(j)) {
//                                    int tmp = dataList.get(j - 1);
//                                    dataList.set(j - 1, dataList.get(j));
//                                    dataList.set(j, tmp);
//                                    try {
//                                        barView.setBottomTextList(toListString(dataList));
//                                        barView.setDataList(dataList, 9);
//                                        Thread.sleep(100);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            }
//                        }
//                    }
//                });
//            }
//        }.start();
    }
}