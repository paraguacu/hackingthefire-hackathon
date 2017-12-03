package bombeiros.mg.gov.br.thefirecall.views;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import bombeiros.mg.gov.br.thefirecall.R;
import bombeiros.mg.gov.br.thefirecall.database.DBController;
import bombeiros.mg.gov.br.thefirecall.utils.GenericActivity;

public class ViewOcorrencias extends GenericActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ocorrencias);

        //adiciona o toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //habilita a seta voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        DBController dbController = new DBController(getBaseContext());
        Cursor cursor = dbController.getOcorrencias();

        ListView lista = (ListView) findViewById(R.id.lista_ocorrencias);
        String[] nomeCampos = new String[] {"_id", "queixa"};
        int[] to = new int[] { R.id.item_label, R.id.item_value };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.item_ocorrencia,cursor,nomeCampos,to, 0);
        lista.setAdapter(adapter);
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
