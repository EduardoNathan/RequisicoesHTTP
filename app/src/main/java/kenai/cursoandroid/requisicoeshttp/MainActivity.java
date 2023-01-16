package kenai.cursoandroid.requisicoeshttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kenai.cursoandroid.requisicoeshttp.api.CEPService;
import kenai.cursoandroid.requisicoeshttp.api.DataService;
import kenai.cursoandroid.requisicoeshttp.model.CEP;
import kenai.cursoandroid.requisicoeshttp.model.Foto;
import kenai.cursoandroid.requisicoeshttp.model.Postagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;
    private Retrofit retrofit;
    private DataService service;
    //private List<Foto> listaFotos = new ArrayList<>();
    private List<Postagem> listaPostagens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.button_recuperar);
        textoResultado = findViewById(R.id.text_resultado);

        retrofit = new Retrofit.Builder()
//                .baseUrl("https://viacep.com.br/ws/")
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(DataService.class);

        botaoRecuperar.setOnClickListener(view -> {
//            recuperarCepRetrofit();
              //recuperarListaRetrofit();
            //salvarPostagem();
            //atualizarPostagem();
            removerPostagem();

        });
    }

    private void removerPostagem(){
    Call<Void> call = service.removerPostagem(2);
    call.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if(response.isSuccessful()){
                textoResultado.setText("Status: " + response.code());
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {

        }
    });
    }

    private void atualizarPostagem(){

        //Postagem postagem = new Postagem("1234", null, "Corpo postagem");

        Postagem postagem = new Postagem();
        postagem.setBody("Corpo da postagem alterado");

        Call<Postagem> call = service.atualizarPostagem(2, postagem);
        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if(response.isSuccessful()){
                    Postagem postagemResposta = response.body();
                    textoResultado.setText(
                            "Status: " + response.code() +
                                    "id: " + postagemResposta.getId() +
                                    "userId: " + postagemResposta.getUserId() +
                                    "body: " + postagemResposta.getBody() +
                                    "titulo: " + postagemResposta.getTitle());
                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });

    }

    private void salvarPostagem(){

        // Configurando objeto
//        Postagem postagem = new Postagem("1234", "Titulo postagem!", "Corpo postagem");

        // Configurando Retrofit, call e salvar postagem
        DataService service = retrofit.create(DataService.class);
        Call<Postagem> call = service.salvarPostagem("1234", "Titulo postagem!", "Corpo postagem");

        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if(response.isSuccessful()){
                    Postagem postagemResposta = response.body();
                    textoResultado.setText(
                            "Código: " + response.code() +
                            "id: " + postagemResposta.getId() +
                            "titulo: " + postagemResposta.getTitle());
                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });

    }

    private void recuperarListaRetrofit(){
        DataService service = retrofit.create(DataService.class);
        //Call<List<Foto>> call = service.recuperarFotos();
        Call<List<Postagem>> call = service.recuperarPostagens();
        call.enqueue(new Callback<List<Postagem>>() {
            @Override
            public void onResponse(Call<List<Postagem>> call, Response<List<Postagem>> response) {
                if(response.isSuccessful()){
                    listaPostagens = response.body();

                    for(int i=0; i<listaPostagens.size(); i ++){
                        Postagem postagem = listaPostagens.get(i);
                        Log.d("resultado: ", "resultado: "
                                + postagem.getId()
                                + " / " + postagem.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Postagem    >> call, Throwable t) {

            }
        });
    }

    private void recuperarCepRetrofit(){

        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> call = cepService.recuperarCEP("06332130");

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if(response.isSuccessful()){
                    CEP cep = response.body();
                    textoResultado.setText(cep.getCep() + " / " + cep.getBairro() + " / " + cep.getUf());
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });
    }
}