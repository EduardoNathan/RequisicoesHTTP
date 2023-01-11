package kenai.cursoandroid.requisicoeshttp.api;

import kenai.cursoandroid.requisicoeshttp.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CEPService {

    @GET("06332130/json/")
    Call<CEP> recuperarCEP();

}
