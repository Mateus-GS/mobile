package br.com.workmed.main.api;

import br.com.workmed.main.entidades.Produto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ProdutoService {

    @Headers({
            "Accept: application/json",
            "User-Agent: workMed"
    })
    @GET("produto")
    Call<List<Produto>> getProduto();

}
