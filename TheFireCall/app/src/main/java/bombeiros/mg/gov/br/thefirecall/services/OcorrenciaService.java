package bombeiros.mg.gov.br.thefirecall.services;

import android.os.AsyncTask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import bombeiros.mg.gov.br.thefirecall.R;
import bombeiros.mg.gov.br.thefirecall.entities.OcorrenciaEntity;
import bombeiros.mg.gov.br.thefirecall.views.ViewOcorrencia;

/**
 * Created by Hiago Paraguaçú on 15/05/2017.
 *
 */

public class OcorrenciaService extends AsyncTask<Void, Void, OcorrenciaEntity> {
    private ViewOcorrencia viewOcorrencia;
    private Exception exception = null;
    private String warn = null;


    public OcorrenciaService(ViewOcorrencia viewOcorrencia) {
        this.viewOcorrencia = viewOcorrencia;
    }

    @Override
    protected OcorrenciaEntity doInBackground(Void... params) throws HttpStatusCodeException{
        OcorrenciaEntity saida = new OcorrenciaEntity();
        //saida.setId(new BigDecimal(2));

        String url = viewOcorrencia.getBaseContext().getString(R.string.host)+"/ocorrencia/getdata/2";

        try {
            // Definir o nome de usuário ea senha para criar um pedido de autenticação básica
//            HttpAuthentication authHeader = new HttpBasicAuthentication("teste", "123");
            HttpHeaders requestHeaders = new HttpHeaders ();
//            requestHeaders.setAuthorization (authHeader);

            HttpEntity<?> requestEntity = new HttpEntity <Object> (saida, requestHeaders);

            // Criar uma nova instância do RestTemplate
//            RestTemplate restTemplate = new RestTemplate();
            // Adiciona o conversor de mensagens
//            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            //restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            //metodo get
//            Object teste = restTemplate.getForObject(url, Object.class);
            //saida = restTemplate.getForObject(url, OcorrenciaEntity.class);

            //metodo  post
            //saida = restTemplate.postForObject(url, saida, UsuarioEntity.class);

            //post com autenticacao ----------------ver como tratar timeout dp
//            ResponseEntity<OcorrenciaEntity> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, OcorrenciaEntity.class);
            //saida = response.getBody();


            //timeout
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout (4 *1000);
            requestFactory.setReadTimeout(10 * 1000);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate(requestFactory);

// Add the String message converter
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

// Make the HTTP GET request, marshaling the response to a String
            //SinaisVitaisEntity teste = restTemplate.getForObject("http://172.10.100.78:5000/sinal/getdata/1", SinaisVitaisEntity.class);
            saida = restTemplate.getForObject(url, OcorrenciaEntity.class);
            //mainActivity.persistLocalOcorrencia(saida);
            return saida;
        }
        catch (HttpStatusCodeException e){
            if(HttpStatus.UNAUTHORIZED == e.getStatusCode())
                warn = "Login não autorizado!\nUsuário e/ou senha inválidos.";
            return null;
        }
        catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(OcorrenciaEntity result) {
        viewOcorrencia.updateView(result);
        if(exception != null)
            viewOcorrencia.Error(exception);
        if(warn != null)
            viewOcorrencia.Warn(warn);
//        mainActivity.persistLocalOcorrencia(result);
    }
}
