package br.com.workmed.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.com.workmed.R;
import android.app.Activity;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity {

    private ProdutoService service = null;
    final private MainActivity mainActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = RestServiceGenerator.createService(ProdutoService.class);
        buscaProdutos();
    }

    public void buscaProdutos(){
        ProdutoService service = RestServiceGenerator.createService(ProdutoService.class);
        Call<List<Produto>> call = service.getProduto();
        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    Log.i("WorkmedDAO", "Retornou " + response.body().size() + " Produtos!");
                    List<String> lista2 = new ArrayList<String>();
                    for (Produto item : response.body()) {
                        lista2.add(item.getDescricao());
                    }
                    Log.i("MainActivity", lista2.toArray().toString());
                    ListView listView = findViewById(R.id.listViewListaProduto);
                    listView.setAdapter(new ArrayAdapter<String>(mainActivity,
                            android.R.layout.simple_list_item_1,
                            lista2));
                } else {
                    Log.e("WorkmedDAO", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }
}