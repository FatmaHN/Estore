package com.example.e_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int images[] ={R.drawable.imprimantehp,R.drawable.imprimantehp_b,R.drawable.pc_portable_lenevo,R.drawable.souris_optique,
            R.drawable.imprimantehp,R.drawable.imprimantehp_b,R.drawable.pc_portable_lenevo,R.drawable.souris_optique,
            R.drawable.imprimantehp,R.drawable.imprimantehp_b,R.drawable.pc_portable_lenevo,R.drawable.souris_optique};
    String names[] ={"Imprimante hp","Imprimante hp","Pc Portable","Souris sans fil",
            "Imprimante hp","Imprimante hp","Pc Portable","Souris sans fil",
            "Imprimante hp","Imprimante hp","Pc Portable","Souris sans fil"};

    List<itemsModel> itemsList = new ArrayList<>();
    GridView gd;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gd = findViewById(R.id.gd);
        for (int i=0; i< names.length;i++){
            itemsModel itemsModel = new itemsModel(names[i],images[i]);
            itemsList.add(itemsModel);
        }
        customAdapter = new CustomAdapter(itemsList,this);
        gd.setAdapter(customAdapter);

        gd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("*****************************"+i);
                showMyDialog(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Intent i = getIntent();

        registerForContextMenu(gd);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_optionnel,menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customAdapter.getFilter().filter(s);
                return true;
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.i1:
                Intent add = new Intent(MainActivity.this,AddProduct.class);
                startActivity(add);
                break;
            case R.id.search_view:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class CustomAdapter extends BaseAdapter implements Filterable {
        private List<itemsModel> itemsModelList;
        private List<itemsModel> itemsModelListFiltered;
        private Context context;

        public CustomAdapter(List<itemsModel> itemsModelList, Context context) {
            this.itemsModelList = itemsModelList;
            this.itemsModelListFiltered = itemsModelList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.product_item_list, null);
            ImageView imageView = view.findViewById(R.id.imageView);
            TextView tvnames = view.findViewById(R.id.tvname);

            imageView.setImageResource(itemsModelListFiltered.get(i).getImage());
            tvnames.setText(itemsModelListFiltered.get(i).getName());
            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filtre = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();
                    if (charSequence == null || charSequence.length()==0){
                        filterResults.count = itemsModelList.size();
                        filterResults.values = itemsModelList;
                    }else {
                        String searchStr = charSequence.toString().toLowerCase();
                        List<itemsModel> resultData = new ArrayList<>();
                        for (itemsModel itemsModel:itemsList){
                            if (itemsModel.getName().contains(searchStr)){
                                resultData.add(itemsModel);
                            }
                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }
                    return null;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    itemsModelListFiltered = (List<itemsModel>) results.values;
                    notifyDataSetChanged();
                }
            };
            return filtre;
        }
    }


    private void showMyDialog(int pos){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        TextView reference = dialog.findViewById(R.id.ref);
        TextView libelle = dialog.findViewById(R.id.lib);
        TextView description = dialog.findViewById(R.id.desc);
        TextView prix = dialog.findViewById(R.id.prix);
        ImageView image = dialog.findViewById(R.id.img);
        Button b = dialog.findViewById(R.id.ok);
        if (pos ==0){
            reference.setText("123");
            libelle.setText("Impimante ");
            description.setText( "Imprimate Hp Noir");
            prix.setText(" 500 DT");
            image.setImageResource(R.drawable.imprimantehp);
        }
        else if (pos==1){
            reference.setText("124");
            libelle.setText("Impimante ");
            description.setText( "Imprimate Hp Blanc");
            prix.setText(" 600 DT");
            image.setImageResource(R.drawable.imprimantehp_b);

        }
        else if (pos==2){
            reference.setText("125");
            libelle.setText("Pc Portable ");
            description.setText( "Pc Portable lenovo Noir ; core i3; 7th; deux carte graphique; 12 ram");
            prix.setText(" 1300 DT");
            image.setImageResource(R.drawable.pc_portable_lenevo);

        }
        else if (pos==3){
            reference.setText("126");
            libelle.setText("Souris ");
            description.setText( "Souris optique sans fil noir");
            prix.setText(" 50 DT");
            image.setImageResource(R.drawable.souris_optique);
        }
        dialog.show();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {dialog.dismiss();}
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.supprimer,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.supprime:
                itemsList.remove(customAdapter.getItem(info.position));
                customAdapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }
}