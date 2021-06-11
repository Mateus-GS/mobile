package br.com.workmed.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.workmed.R;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import br.com.workmed.main.api.ProdutoService;
import br.com.workmed.main.api.RestServiceGenerator;
import br.com.workmed.main.entidades.Produto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioProdutoAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_produto_acitivity);
        setTitle("Edição de Produto");
        configuraBotaoSalvar();
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.buttonSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FormularioProduto","Clicou em Salvar");
                Produto produto = recuperaInformacoesFormulario();
                salvaProduto(produto);
            }
        });
    }

    private void salvaProduto(Produto produto) {
        ProdutoService service = RestServiceGenerator.createService(ProdutoService.class);
        Call<Produto> call = service.criaProduto(produto);
        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioCripto", "Salvou produto "+ produto.getId());
                    Toast.makeText(getApplicationContext(), "Salvou a produto "+ produto.getId(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioCripto", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                Log.e("FormularioCripto", "Erro: " + t.getMessage());
            }
        });
    }

    @NotNull
    private Produto recuperaInformacoesFormulario() {
        EditText id = findViewById(R.id.editTextTextPersonId);
        EditText descricao = findViewById(R.id.editTextTextPersonDescricao);
        EditText quantidadeEmEstoque = findViewById(R.id.editTextTextPersonQuantidadeEmEstoque);

        Produto produto = new Produto();
        produto.setId(id.getText().toString());
        produto.setQuantidadeEmEstoque(quantidadeEmEstoque.getText().toString());
        produto.setDescricao(descricao.getText().toString());
        return produto;
    }
}