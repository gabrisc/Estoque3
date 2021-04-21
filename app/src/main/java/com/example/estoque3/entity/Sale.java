package com.example.estoque3.entity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Iterator;
import java.util.List;

public class Sale {
    private List<EconomicOperation> economicOperationList;
    private String id,clientName,date,paymentType;
    private Double totalValue,discount;

    public void subtrairVendas(){
//
//        Iterator<Produtovendido> iterator=ListagemDeProdutosParaCompras.produtosSelecionados.iterator();
//        while(iterator.hasNext()){
//            Produtovendido produtovendido = iterator.next();
//
//            Produto produto=new Produto();
//            produto.setNomeProduto(produtovendido.getNomeProduto());
//            produto.setIdProduto(produtovendido.getIdProduto());
//            produto.setQuantidadeUnidade(produtovendido.getQuantidadeUnidade()-produtovendido.getQuantidadeVendida());
//            produto.setPrecoVendaUnidade(produtovendido.getPrecoVendaUnidade());
//            produto.setDataeHora(produtovendido.getDataeHora());
//            produto.setPrecoCompraUnidade(produtovendido.getPrecoCompraUnidade());
//
//            DatabaseReference fireBaseRef=ConfigFireBaseReference.getFirebaseDatabase();
//            FirebaseAuth autenticacion = configFireBase.getFireBaseAutenticacao();
//            String idUsuario = Base64Custom.codificarBase64(autenticacion.getCurrentUser().getEmail());
//
//            fireBaseRef.child("armazenamento").child(idUsuario).child("produtos").child(produto.getIdProduto()).setValue(produto).addOnCompleteListener(new OnCompleteListener<Void>() {
//
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//
//                }
//            });
//
//        }
//        ListagemDeProdutosParaCompras.produtosSelecionados.clear();
    }

    public void removerVenda(){

//        DatabaseReference firebaseRef2= ConfigFireBaseReference.getFirebaseDatabase();
//        DatabaseReference produtoRef=firebaseRef2.child("armazenamento").child(getIdUsuario()).child("vendas").child(this.getIdVenda());
//        produtoRef.removeValue();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
