package kenai.cursoandroid.requisicoeshttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.button_recuperar);
        textoResultado = findViewById(R.id.text_resultado);

        botaoRecuperar.setOnClickListener(view -> {
            MyTask task = new MyTask();
            String urlApi = "https://blockchain.info/ticker";
            String urlCep = "https://viacep.com.br/ws/06332130/json/";
            task.execute(urlCep);
        });
    }

    class MyTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {

                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();


                //RECUPERA OS DADOS EM BYTES
                InputStream inputStream = conexao.getInputStream();

                // LE OS BYTES E DECODIFICA PARA CARACTERES
                inputStreamReader = new InputStreamReader(inputStream);

                // TRANSFORMA OS CARACTERES EM STRING
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";

                while((linha = reader.readLine()) != null){
                    buffer.append(linha);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();

        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            String logradouro = null;

            try {
                JSONObject jsonObject = new JSONObject(resultado);
                logradouro = jsonObject.getString("logradouro");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            textoResultado.setText(logradouro);
        }
    }

}