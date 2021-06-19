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
import android.content.Intent;
import android.graphics.PorterDuff;

public class FormularioProdutoAcitivity extends AppCompatActivity {

    private ProdutoService service = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_produto_acitivity);
        setTitle("Edição de Produto");
        configuraBotaoSalvar();
        inicializaObjeto();
    }

    private void inicializaObjeto() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("objeto") != null) {
            Produto objeto = (Produto) intent.getSerializableExtra("objeto");
            EditText id = findViewById(R.id.editTextTextPersonId);
            EditText descricao = findViewById(R.id.editTextTextPersonDescricao);
            EditText quantidadeEmEstoque = findViewById(R.id.editTextTextPersonQuantidadeEmEstoque);
            id.setText(objeto.getId());
            descricao.setText(objeto.getDescricao());
            quantidadeEmEstoque.setText(objeto.getQuantidadeEmEstoque());
            id.setEnabled(false);
            Button botaoSalvar = findViewById(R.id.buttonSalvar);
            botaoSalvar.setText("Atualizar");
        }
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.buttonSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FormularioCripto","Clicou em Salvar");
                Produto produto = recuperaInformacoesFormulario();
                Intent intent = getIntent();
                if (intent.getSerializableExtra("objeto") != null) {
                    Produto objeto = (Produto) intent.getSerializableExtra("objeto");
                    produto.setId(objeto.getId());
                    produto.setQuantidadeEmEstoque(objeto.getQuantidadeEmEstoque());
                    Log.i("FormularioCripto","Estou aqui1");
                    if (validaFormulario(produto)) {
                        Log.i("FormularioCripto","Estou aqui2");
                        atualizaProduto(produto);
                    }
                } else {
//                    produto.setDataCriacao(new Date());
                    if (validaFormulario(produto)) {
                        salvaProduto(produto);
                    }
                }
            }
        });
    }

    private boolean validaFormulario(Produto produto){
        boolean valido = true;
        EditText id = findViewById(R.id.editTextTextPersonId);
        EditText descricao = findViewById(R.id.editTextTextPersonDescricao);
        EditText quantidadeEmEstoque = findViewById(R.id.editTextTextPersonQuantidadeEmEstoque);
        if (produto.getId() == null || produto.getId().trim().length() == 0){
            id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (produto.getDescricao() == null || produto.getDescricao().trim().length() == 0){
            descricao.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            descricao.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (produto.getQuantidadeEmEstoque() == null || produto.getQuantidadeEmEstoque().trim().length() == 0){
            quantidadeEmEstoque.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            quantidadeEmEstoque.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (!valido){
            Log.e("FormularioProduto", "Favor verificar os campos destacados");
            Toast.makeText(getApplicationContext(), "Favor verificar os campos destacados", Toast.LENGTH_LONG).show();
        }
        return valido;
    }

    private void salvaProduto(Produto produto) {
        ProdutoService service = RestServiceGenerator.createService(ProdutoService.class);
        Call<Produto> call = service.criaProduto(produto);
//        Call<Produto> call;
//        Log.i("FormularioProduto","Vai criar produto "+produto.getId());
//        call = service.criaProduto(produto);
        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioCripto", "Salvou produto "+ produto.getId());
                    Toast.makeText(getApplicationContext(), "Salvou a produto "+ produto.getDescricao(), Toast.LENGTH_LONG).show();
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

    private void atualizaProduto(Produto produto) {
        Call<Produto> call;
        Log.i("FormularioProduto","Vai atualizar produto "+produto.getDescricao());
        call = service.atualizaProduto(produto.getId(), produto);
        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioProduto", "Atualizou a produto " + produto.getId());
                    Toast.makeText(getApplicationContext(), "Atualizou a produto " + produto.getId(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioProduto", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                Log.e("FormularioProduto", "Erro: " + t.getMessage());
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