package kenai.cursoandroid.requisicoeshttp.api;

import java.util.List;

import kenai.cursoandroid.requisicoeshttp.model.Foto;
import kenai.cursoandroid.requisicoeshttp.model.Postagem;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("/photos")
    Call<List<Foto>> recuperarFotos();

    @GET("/posts")
    Call<List<Postagem>> recuperarPostagens();

}
