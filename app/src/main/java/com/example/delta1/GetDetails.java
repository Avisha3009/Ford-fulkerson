package com.example.delta1;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class GetDetails extends AppCompatActivity {
    TextView t;
    EditText getd;
    EditText getf;
    EditText gets;
    Button add;
    Button find;
    Button clr;

    int count =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_details);

        t = findViewById(R.id.result);
        gets = findViewById(R.id.gets);
        getf = findViewById(R.id.getf);
        getd = findViewById(R.id.getd);
        add = findViewById(R.id.add);
        find = findViewById(R.id.find) ;
        clr = findViewById(R.id.clear);

        Intent intent = getIntent();
        String no_of_cities = intent.getStringExtra(QUESTION.City);
        String no_of_flows = intent.getStringExtra(QUESTION.flow);
        String s = intent.getStringExtra(QUESTION.st);
        String d = intent.getStringExtra(QUESTION.en);

        int V = Integer.parseInt(no_of_cities);
        int E = Integer.parseInt(no_of_flows);
        int source = Integer.parseInt(s);
        int sink = Integer.parseInt(d);
        int i,j;
        int[][] graph = new int[V][V];
        for( i=0;i<V;i++)
            for (j = 0; j < V; j++) {
                graph[i][j] = 0;
            }
        add.setEnabled(false);
        getd.addTextChangedListener(textWatcher);
        getf.addTextChangedListener(textWatcher);
        gets.addTextChangedListener(textWatcher);

        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d= getd.getText().toString();
                String s= gets.getText().toString();
                int src = Integer.parseInt(s);
                int dest = Integer.parseInt(d);
                if (src < V && dest < V){
                    if(graph[src][dest]!=0){
                        graph[src][dest] = 0;
                        count--;
                        Toast.makeText(getApplicationContext(), "Edge "+ src + " -> "+ dest + " cleared", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No such edge was added", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid Edge Value", Toast.LENGTH_SHORT).show();
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String d= getd.getText().toString();
                String s= gets.getText().toString();
                String f= getf.getText().toString();

                int src = Integer.parseInt(s);
                int dest = Integer.parseInt(d);
                int flow = Integer.parseInt(f);
                int maxFlow =-1;
                if (src < V && dest < V){
                    if(count<=E && graph[src][dest]==0&& src!=dest && dest != source && src != sink) {
                        graph[src][dest] = flow;
                       // Toast.makeText(GetDetails.this,"graph[0][1]"+graph[src][dest],Toast.LENGTH_SHORT);
                        count++;
                        //t.setText("Edge "+ src + " -> "+ dest + " "+ flow + " added");
                       Toast.makeText(getApplicationContext(), "Edge "+ src + " -> "+ dest + " "+ flow + " added", Toast.LENGTH_SHORT).show();
                    }
                    if(dest == source||src==sink){
                        Toast.makeText(getApplicationContext(), "Invalid edge", Toast.LENGTH_SHORT).show();
                    }

                    if(src ==dest){
                        Toast.makeText(getApplicationContext(), "Can't add self loops", Toast.LENGTH_SHORT).show();
                    }
                    if(graph[src][dest]!=0){
                        Toast.makeText(getApplicationContext(), "Edge already added", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                }

                if(count==E+1){
                    maxFlow = fordFulkerson(graph,V,source,sink);
                }
//                if(count>E){
//                    Toast.makeText(GetDetails.this, "Max Edges added", Toast.LENGTH_SHORT);
//
//                }
                if(maxFlow!=-1)
                t.setText("Maxflow: "+ maxFlow);
            }
        });
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String d= getd.getText().toString();
            String sr= gets.getText().toString();
            String f= getf.getText().toString();

            add.setEnabled(!d.isEmpty() && !sr.isEmpty() && !f.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    int fordFulkerson(int Graph[][],int V, int s, int t) {
        int u, v;
        int p[] = new int[V];

        int maxFlow = 0;

        while (bfs(Graph, V, s, t, p)) {
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = p[v]) {
                u = p[v];
                path_flow = Math.min(path_flow, Graph[u][v]);
            }

            for (v = t; v != s; v = p[v]) {
                u = p[v];
                Graph[u][v] -= path_flow;
                Graph[v][u] += path_flow;
            }

            // Adding the path flows
            maxFlow += path_flow;
        }
        return maxFlow;
    }

    boolean bfs(int Graph[][], int V, int s, int t, int p[]) {
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        p[s] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false && Graph[u][v] > 0) {
                    queue.add(v);
                    p[v] = u;
                    visited[v] = true;
                }
            }
        }
        return (visited[t] == true);
    }
}