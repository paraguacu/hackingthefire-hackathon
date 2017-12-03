package bombeiros.mg.gov.br.thefirecall.views;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import bombeiros.mg.gov.br.thefirecall.R;
import bombeiros.mg.gov.br.thefirecall.utils.GenericActivity;

public class InicioPatrulha extends GenericActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_patrulha);

        //adiciona o toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //habilita a seta voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        TextView left1 = (TextView) findViewById(R.id.grid_card_text1);
        left1.setText("Incêndio");
        left1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info("teste de click");
            }
        });

        TextView right1 = (TextView) findViewById(R.id.grid_card_text2);
        right1.setText("Resgate");
        right1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info("teste de click");
            }
        });

        TextView left2 = (TextView) findViewById(R.id.grid_card_text3);
        left2.setText("Ambulância");
        left2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info("teste de click");
            }
        });

        TextView right2 = (TextView) findViewById(R.id.grid_card_text4);
        right2.setText("Viatura");
        right2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info("teste de click");
            }
        });

        TextView left3 = (TextView) findViewById(R.id.grid_card_text5);
        left3.setText("Aeronave");
        left3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info("teste de click");
            }
        });

        TextView right3 = (TextView) findViewById(R.id.grid_card_text6);
        right3.setText("Moto");
        right3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info("teste de click");
            }
        });

//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.grid_system);
//
//        linearLayout.addView(setItem("Incêndio","Resgate"));
//        linearLayout.addView(setItem("Ambulância","Viatura"));
//        linearLayout.addView(setItem("Aeronave","Moto"));
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
