package com.example.estoque3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.estoque3.R;
import com.example.estoque3.entity.Product;
import com.example.estoque3.ui.main.PageViewModel;
import com.example.estoque3.util.AdapterProduct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.estoque3.entity.Product.getIdUser;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;

public class AddSaleActivity extends AppCompatActivity implements AdapterProduct.OnProductListerner{

    private PageViewModel pageViewModel;
    private static final String ARG_SECTION_NUMBER = "2";
    private AdapterProduct adapterProduct;
    private List<Product> listProduct= new ArrayList<>();
    private Product productselect;
    private Intent intent;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        firebaseDbReference.child("ProductsAndServices").child("PRODUCTS").child(getIdUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //listProduct.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Product productTemp = ds.getValue(Product.class);
                    listProduct.add(productTemp);
                }
                adapterProduct.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String x = String.valueOf(error);
            }
        });

        adapterProduct= new AdapterProduct(listProduct,this,this);
        recyclerView= findViewById(R.id.recyclerViewprodutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterProduct);
    }


    @Override
    public void onProductClick(int position) {

        productselect = listProduct.get(position);

        intent.putExtra("id", productselect.getId());
        intent.putExtra("nome",productselect.getName());
        intent.putExtra("precovenda",String.valueOf(productselect.getSealValue()));
        intent.putExtra("precocompra",String.valueOf(productselect.getExpenseValue()));
        intent.putExtra("quantidade",String.valueOf(productselect.getQuantity()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setNegativeButton("Apagar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                productselect.remove();
            }
        }).setMessage("Nome: "+productselect.getName()+"\n"+
                "Quantidade estocada: "+productselect.getQuantity()+"\n"+
                "Preço de compra: "+productselect.getExpenseValue()+" R$"+"\n"+
                "Valor de Venda: "+productselect.getSealValue()+" R$"+"\n");

        builder.create();
        builder.show();
    }
}