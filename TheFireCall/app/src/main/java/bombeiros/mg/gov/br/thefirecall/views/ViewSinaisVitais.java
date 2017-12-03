package bombeiros.mg.gov.br.thefirecall.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import bombeiros.mg.gov.br.thefirecall.R;
import bombeiros.mg.gov.br.thefirecall.entities.SinaisVitaisEntity;
import bombeiros.mg.gov.br.thefirecall.services.SinaisVitaisService;
import bombeiros.mg.gov.br.thefirecall.utils.GenericActivity;

public class ViewSinaisVitais extends GenericActivity {
    private int id_ocorrencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sinais_vitais);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        id_ocorrencia = params.getInt("id_ocorrencia");

        //adiciona o toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //habilita a seta voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    public void enviarSinais(View view){
        SinaisVitaisEntity sinaisVitaisEntity = new SinaisVitaisEntity();

        EditText pressao = (EditText) findViewById(R.id.sinal_pressao);
        sinaisVitaisEntity.setPressao(pressao.getText().toString());

        EditText freq_card = (EditText) findViewById(R.id.sinal_freq_card);
        sinaisVitaisEntity.setFrequenciaCardiaca(freq_card.getText().toString());

        EditText sat_oxi = (EditText) findViewById(R.id.sinal_sat_oxi);
        sinaisVitaisEntity.setSaturacaoOxigenio(sat_oxi.getText().toString());

        EditText temperatura = (EditText) findViewById(R.id.sinal_temperatura);
        sinaisVitaisEntity.setTemperatura(temperatura.getText().toString());

        sinaisVitaisEntity.setId_ocorrencia(id_ocorrencia);

        SinaisVitaisService service = new SinaisVitaisService(this, sinaisVitaisEntity);
        service.execute();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //passa a funcionalidade de fechar o botao home com a seta
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
