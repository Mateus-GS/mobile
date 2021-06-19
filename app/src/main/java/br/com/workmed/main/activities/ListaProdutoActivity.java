package br.com.workmed.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import br.com.workmed.R;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;
import br.com.workmed.main.api.ProdutoService;
import br.com.workmed.main.api.RestServiceGenerator;
import br.com.workmed.main.entidades.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



public class ListaProdutoActivity extends AppCompatActivity {

    private ProdutoService service = null;
    final private ListaProdutoActivity listaProdutoActivity = this;
    private final Context context;

    public ListaProdutoActivity() {
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de Produtos");
        setContentView(R.layout.activity_lista_produto);
        service = RestServiceGenerator.createService(ProdutoService.class);
        criaAcaoBotaoFlutuante();
        criaAcaoCliqueLongo();
    }

    private void criaAcaoCliqueLongo() {
        ListView listView = findViewById(R.id.listViewListaProduto);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ListaProdutoActivity","Clicou em clique longo na posicao "+position);
                final Produto objetoSelecionado = (Produto) parent.getAdapter().getItem(position);
                Log.i("ListaProdutoActivity", "Selecionou a produto "+objetoSelecionado.getDescricao());
                new AlertDialog.Builder(parent.getContext()).setTitle("Removendo produto")
                        .setMessage("Tem certeza que quer remover a produto "+objetoSelecionado.getDescricao()+"?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeProduto(objetoSelecionado);
                            }
                        }).setNegativeButton("Não", null).show();
                return true;
            }
        });
    }

    private void removeProduto(Produto produto) {
        Call<Boolean> call = null;
        Log.i("ListaProdutoActivity","Vai remover produto "+produto.getId());
        call = this.service.excluiProduto(produto.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaProdutoActivity", "Removeu a produto " + produto.getId());
                    Toast.makeText(getApplicationContext(), "Removeu a produto " + produto.getId(), Toast.LENGTH_LONG).show();
                    onResume();
                } else {
                    Log.e("ListaProdutoActivity", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("ListaProdutoActivity", "Erro: " + t.getMessage());
            }
        });
    }

    private void criaAcaoBotaoFlutuante() {
        FloatingActionButton botaoNovo = findViewById(R.id.floatingActionButton);
        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","Adicionar novo Produto");
                startActivity(new Intent(ListaProdutoActivity.this,
                        FormularioProdutoAcitivity.class));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscaProdutos();
    }

    public void buscaProdutos(){
        Call<List<Produto>> call = this.service.getProduto();
        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaProdutoActivity", "Retornou " + response.body().size() + " produto!");
                    ListView listView = findViewById(R.id.listViewListaProduto);
                    listView.setAdapter(new ProdutoAdapter(context,response.body()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i("ListaProdutoActivity", "Selecionou o objeto de posicao "+position);
                            Produto objetoSelecionado = (Produto) parent.getAdapter().getItem(position);
                            Log.i("ListaProdutoActivity", "Selecionou o produto "+objetoSelecionado.getDescricao());
                            Intent intent = new Intent(ListaProdutoActivity.this, FormularioProdutoAcitivity.class);
                            intent.putExtra("objeto", objetoSelecionado);
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.e("ListaProdutoActivity", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): "+ response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.e("ListaProdutoActivity", "Erro: " + t.getMessage());
            }
        });
    }
}