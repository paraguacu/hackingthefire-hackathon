package bombeiros.mg.gov.br.thefirecall.services;

import android.os.AsyncTask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import bombeiros.mg.gov.br.thefirecall.R;
import bombeiros.mg.gov.br.thefirecall.entities.SinaisVitaisEntity;
import bombeiros.mg.gov.br.thefirecall.views.ViewSinaisVitais;

/**
 * Created by Hiago Paraguaçú on 02/12/2017.
 *
 */

public class SinaisVitaisService extends AsyncTask<Void, Void, SinaisVitaisEntity> {
    private ViewSinaisVitais viewSinaisVitais;
    private Exception exception = null;
    private String warn = null;
    private SinaisVitaisEntity sinaisVitaisEntity = null;

    public SinaisVitaisService(ViewSinaisVitais viewSinaisVitais, SinaisVitaisEntity sinaisVitaisEntity) {
        this.viewSinaisVitais = viewSinaisVitais;
        this.sinaisVitaisEntity = sinaisVitaisEntity;
    }

    @Override
    protected SinaisVitaisEntity doInBackground(Void... params)  throws HttpStatusCodeException {
        SinaisVitaisEntity saida = sinaisVitaisEntity;
        String param = "/"+saida.getId_ocorrencia()+"/"+saida.getPressao()+"/"+saida.getFrequenciaCardiaca()+
                "/"+saida.getSaturacaoOxigenio()+"/"+saida.getTemperatura();
        String url = viewSinaisVitais.getBaseContext().getString(R.string.host)+"/sinal/register"+param;

        try {
            // Definir o nome de usuário ea senha para criar um pedido de autenticação básica
//            HttpAuthentication authHeader = new HttpBasicAuthentication("teste", "123");
            HttpHeaders requestHeaders = new HttpHeaders ();
            requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
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
            //ResponseEntity<SinaisVitaisEntity> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SinaisVitaisEntity.class);
            String status = restTemplate.getForObject(url, String.class);
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
    protected void onPostExecute(SinaisVitaisEntity result) {
        //viewSinaisVitais.updateView(result);
        if(exception != null)
            viewSinaisVitais.Error(exception);
        if(warn != null)
            viewSinaisVitais.Warn(warn);
    }
}
