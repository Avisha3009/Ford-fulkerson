package com.example.delta1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class QUESTION extends AppCompatActivity {
    Button find;
    EditText ncc;
    EditText nff;
    EditText e;
    EditText s;
    public static final String City ="com.example.Delta1.extra.city";
    public static final String flow = "com.example.Delta1.extra.flow";
    public static final  String st ="com.example.Delta1.extra.start";
    public static final  String en ="com.example.Delta1.extra.end";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ncc = findViewById(R.id.nc);
        find = findViewById(R.id.find);
        nff = findViewById(R.id.nf);
        s = findViewById(R.id.start);
        e = findViewById(R.id.end);
        find.setEnabled(false);

        s.addTextChangedListener(textWatcher);
        e.addTextChangedListener(textWatcher);
        ncc.addTextChangedListener(textWatcher);
        nff.addTextChangedListener(textWatcher);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nc = ncc.getText().toString();
                String nf = nff.getText().toString();
                String stat = s.getText().toString();
                String end = e.getText().toString();

            int source = Integer.parseInt(stat);
            int sink = Integer.parseInt(end);
            int city = Integer.parseInt(nc);
            int n = Integer.parseInt(nf);
                if( source < city && sink < city && source >= 0 && sink >= 0 && n <= city * ( city - 1 )){
                    openActivity(v);
                }
                else{

                    Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_LONG).show();
                }
            }
        });
        }

    public void openActivity(View v){
        String nc = ncc.getText().toString();
        String nf = nff.getText().toString();
        String stat = s.getText().toString();
        String end = e.getText().toString();
        Intent intent=new Intent(this,GetDetails.class);
        intent.putExtra(City,nc);
        intent.putExtra(flow,nf);
        intent.putExtra(st,stat);
        intent.putExtra(en,end);
        startActivity(intent);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nc = ncc.getText().toString();
            String nf = nff.getText().toString();
            String end = e.getText().toString();
            String st = s.toString();

            find.setEnabled(!nc.isEmpty() && !nf.isEmpty() && !end.isEmpty() && !st.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}