 package com.example.estoque3.ui.main;

 import android.content.DialogInterface;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AlertDialog;
 import androidx.fragment.app.Fragment;
 import androidx.lifecycle.ViewModelProvider;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;
 import com.example.estoque3.Activity.UpdateProductActivity;
 import com.example.estoque3.R;
 import com.example.estoque3.entity.EconomicOperation;
 import com.example.estoque3.util.adapters.AdapterEconomicOperation;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.ValueEventListener;
 import java.util.ArrayList;
 import java.util.List;
 import static com.example.estoque3.entity.EconomicOperation.getIdUser;
 import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;


public class EconomicOperationFragment extends Fragment implements AdapterEconomicOperation.OnEconomicOperationListerner {

    private PageViewModel pageViewModel;
    private static final String ARG_SECTION_NUMBER = "2";
    private AdapterEconomicOperation adapterEconomicOperation;
    private List<EconomicOperation> economicOperationList = new ArrayList<>();
    private EconomicOperation economicOperationSelect;
    private Intent intent;
    private RecyclerView recyclerView;

    public EconomicOperationFragment() {}

    public static EconomicOperationFragment newInstance(int index) {
        EconomicOperationFragment fragment = new EconomicOperationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 2;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_economic_operation, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSale);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        adapterEconomicOperation = new AdapterEconomicOperation(economicOperationList,view.getContext(), this::onEconomicOperationClick);

                firebaseInstance.getReference()
                        .child(getIdUser())
                        .child("EconomicOperations")
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        economicOperationList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            EconomicOperation economicOperationTemp = ds.getValue(EconomicOperation.class);
                            economicOperationList.add(economicOperationTemp);
                        }
                        adapterEconomicOperation.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String x = String.valueOf(error);
                    }
                });
                recyclerView.setAdapter(adapterEconomicOperation);
                //recyclerView.setAdapter(adapterEconomicOperation);

        return view;
    }

    @Override
    public void onEconomicOperationClick(int position) {
        economicOperationSelect = economicOperationList.get(position);
        intent = new Intent(getContext().getApplicationContext(), UpdateProductActivity.class);
        intent.putExtra("id", economicOperationSelect.getId());
        intent.putExtra("nome", economicOperationSelect.getName());
        intent.putExtra("precovenda",String.valueOf(economicOperationSelect.getSealValue()));
        intent.putExtra("precocompra",String.valueOf(economicOperationSelect.getExpenseValue()));
        intent.putExtra("quantidade",String.valueOf(economicOperationSelect.getQuantity()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext().getApplicationContext());
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(intent);
            }
        }).setNegativeButton("Apagar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                economicOperationSelect.remove();
            }
        }).setMessage("Nome: "+ economicOperationSelect.getName()+"\n"+
                "Quantidade estocada: "+ economicOperationSelect.getQuantity()+"\n"+
                "Preço de compra: "+ economicOperationSelect.getExpenseValue()+" R$"+"\n"+
                "Valor de Venda: "+ economicOperationSelect.getSealValue()+" R$"+"\n");

        builder.create();
        builder.show();
    }
}