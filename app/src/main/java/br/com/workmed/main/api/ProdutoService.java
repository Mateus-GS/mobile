package br.com.workmed.main.api;

import br.com.workmed.main.entidades.Produto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProdutoService {

    @Headers({
            "Accept: application/json",
            "User-Agent: workMed"
    })
    @GET("produto")
    Call<List<Produto>> getProduto();

    @GET("produto/{id}")
    Call<Produto> getProduto(@Path("id") String id);

    @POST("produto")
    Call<Produto> criaProduto(@Body Produto produto);

    @PUT("produto/{id}")
    Call<Produto> atualizaProduto(@Path("id") String id, @Body Produto produto);

    @DELETE("produto/{id}")
    Call<Boolean> excluiProduto(@Path("id") String id);

}
