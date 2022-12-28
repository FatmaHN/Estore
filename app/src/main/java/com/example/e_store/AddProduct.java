package com.example.e_store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java. util. Collections;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddProduct extends AppCompatActivity
{
    EditText e1,e2,e3,e4;
    Button b1,b2,b3;
    LinearLayout layout;
    TableLayout table;
    ImageView idImg;
    int SELECT_PICTURE = 200;
   // ArrayList<String> product = new ArrayList<String>();
   public ArrayList<String> data = new ArrayList<String>();
   public ArrayList<String> data1 = new ArrayList<String>();
   public ArrayList<String> data2 = new ArrayList<String>();
   public ArrayList<String> data3 = new ArrayList<String>();
   public ArrayList<ImageView> data4 = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Ajouter un nouveau produit");
        e1 = findViewById(R.id.ref);
        e2 = findViewById(R.id.lib);
        e3 = findViewById(R.id.desc);
        e4 = findViewById(R.id.prix);
        b3 = findViewById(R.id.cancel);
        b2 = findViewById(R.id.selectImg);
        layout = findViewById(R.id.add_product);
        idImg = findViewById(R.id.idImg);
        b1 = findViewById(R.id.save);
        //Button save
        b1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
               if (e1.getText().toString().equalsIgnoreCase("")||
                       e2.getText().toString().equalsIgnoreCase("")||
                       e3.getText().toString().equalsIgnoreCase("")||
                       e4.getText().toString().equalsIgnoreCase("")){

                   Toast.makeText(AddProduct.this, "Un champ vide", Toast.LENGTH_SHORT).show();
                        }
               else
                       {
                            add();
                       }
                }
            });


        //Button selected Image
        b2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    imageChooser();
                }
            });
        //Button cancel
        b3.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    e1.setText("");
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                    idImg.setTag("white");
                    e1.requestFocus();
                }
            });
        registerForContextMenu(layout);
        //registerForContextMenu(e1);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
        {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.retour,menu);
        }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId()){
                case R.id.retour:
                    Intent i = new Intent(AddProduct.this,MainActivity.class);
                    startActivity(i);
                    return true;
            }
            return super.onContextItemSelected(item);
        }
    void imageChooser()
        {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i,"Select Picture"),SELECT_PICTURE);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            if(resultCode == RESULT_OK);

            if (requestCode == SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri){
                    idImg.setImageURI(selectedImageUri);
                }
            }

        }

    public void add(){
        int ref = Integer.parseInt(e1.getText().toString());
        String lib = e2.getText().toString();
        String desc = e3.getText().toString();
        int prix = Integer.parseInt(e4.getText().toString());

        data.add(String.valueOf(ref));
        data1.add(lib);
        data2.add(desc);
        data3.add(String.valueOf(prix));


        TableLayout table = findViewById(R.id.tb1);

        TableRow row = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);
        TextView t4 = new TextView(this);

        int sum = 0;

        for (int i=0;i< data.size();i++){
            String reff = data.get(i);
            String libb = data1.get(i);
            String descc = data2.get(i);
            String prixx = data3.get(i);

            t1.setText(reff);
            t2.setText(libb);
            t3.setText(descc);
            t4.setText(prixx);

            //sum = sum+Integer.parseInt(data2.get(i).toString());
        }
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.addView(t4);
        table.addView(row);

        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");
        //idImg.setImageResource(android.R.color.transparent);
        idImg.setTag("white");
        e1.requestFocus();



    }
}