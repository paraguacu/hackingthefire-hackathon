package bombeiros.mg.gov.br.thefirecall.views;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.mikepenz.materialdrawer.Drawer;

import org.json.JSONException;
import org.json.JSONObject;

import bombeiros.mg.gov.br.thefirecall.R;
import bombeiros.mg.gov.br.thefirecall.database.DBController;
import bombeiros.mg.gov.br.thefirecall.entities.OcorrenciaEntity;
import bombeiros.mg.gov.br.thefirecall.services.OcorrenciaService;
import bombeiros.mg.gov.br.thefirecall.utils.GenericActivity;

public class ViewOcorrencia extends GenericActivity {
    private Drawer result  = null;
    private int id_ocorrencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_conteiner);

        //conectScoket();

        //adiciona o toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        result = leftDrawer(savedInstanceState, toolbar);
        mountView(getCurrentFocus());

//        SocketIO socketIO = new SocketIO();
//        mSocket = socketIO.getSocket();
//        mSocket.on("conect", onUserJoined);

        //emit();

        //textView = (TextView) findViewById(R.id.teste_id);
    }

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        Log.e("ERROR_SOCKET", e.getMessage());
                        return;
                    }

//                    addLog(getResources().getString(R.string.message_user_joined, username));
//                    addParticipantsLog(numUsers);
                }
            });
        }
    };

    public void mountView(View view){
        FrameLayout conteiner = (FrameLayout) findViewById(R.id.insert_conteiner);
        View viewChild = getLayoutInflater().inflate(R.layout.aguardando_chamado, null);
//        view.setId(R.id.aguardando);
        conteiner.removeAllViews();
        conteiner.addView(viewChild);
    }

    public void mountOcorrencia(View view){
        getDados();
    }

    public void getDados () {
        //emit();
        OcorrenciaService ocorrenciaService = new OcorrenciaService(this);
        ocorrenciaService.execute();
    }

    public void updateView(OcorrenciaEntity ocorrenciaEntity){
        if(ocorrenciaEntity == null)
            return;
        //persistencia local
        persistLocalOcorrencia(ocorrenciaEntity);

        //gera tela
        FrameLayout conteiner = (FrameLayout) findViewById(R.id.insert_conteiner);
        conteiner.removeAllViews();
        conteiner.buildLayer();

        View view = getLayoutInflater().inflate(R.layout.view_ocorrencia, null);
        LinearLayout item_list = (LinearLayout) view.findViewById(R.id.item_list);
        conteiner.addView(view);

        item_list.addView(setItem("Emergência:", ocorrenciaEntity.isEmergencia() ? "Sim" : "Não", true));
        item_list.addView(setItem("Município:", ocorrenciaEntity.getMunicipio()));
        item_list.addView(setItem("Endereço:", ocorrenciaEntity.getEndereco()));
        item_list.addView(setItem("Número:", ocorrenciaEntity.getNumero()+""));
        item_list.addView(setItem("Bairro:", ocorrenciaEntity.getBairro()));
        item_list.addView(setItem("Referência:", ocorrenciaEntity.getReferencia()));
        item_list.addView(setItem("Paciente:", ocorrenciaEntity.getPaciente()));
        item_list.addView(setItem("Sexo:", ocorrenciaEntity.getSexo()));
        item_list.addView(setItem("Idade:", ocorrenciaEntity.getIdade()+""));
        item_list.addView(setItem("Queixa:", ocorrenciaEntity.getQueixa()));
        item_list.addView(setItem("Observações:", ocorrenciaEntity.getObservacoes()));
        item_list.addView(setItem("Status:", ocorrenciaEntity.getStatus()));
        item_list.addView(setItem("Data:", ocorrenciaEntity.getData()));
        item_list.addView(setItem("Telefone:", ocorrenciaEntity.getTelefone()+""));
        item_list.addView(setItem("Solicitante:", ocorrenciaEntity.getSolicitante()));
        item_list.addView(createButtons());

        id_ocorrencia = ocorrenciaEntity.getId();
    }

    private View setItem (String label, String value) {
        return setItem(label, value, false);
    }

    private View setItem (String label, String value, boolean isFocused) {
        View view = getLayoutInflater().inflate(R.layout.item_ocorrencia, null);
        TextView labelTxt = (TextView) view.findViewById(R.id.item_label);
        labelTxt.setText(label);

        TextView valueTxt = (TextView) view.findViewById(R.id.item_value);
        valueTxt.setText(value);

        //mudar estilo quando infro principal
        if(isFocused){
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.item_conteiner);
            //linearLayout.setScrollBarStyle();
        }
        return view;
    }

    private View createButtons (){
        View view = getLayoutInflater().inflate(R.layout.button_route, null);
        Button button = (Button) view.findViewById(R.id.btn_route);

        Button view_sinais_vit = (Button) view.findViewById(R.id.btn_sinal_enviar);
        view_sinais_vit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        goToViewSinaisVitais();
                    }
                });
            }
        });

        return view;
    }

    private void goToViewSinaisVitais(){
        Intent intent = new Intent(ViewOcorrencia.this, ViewSinaisVitais.class);

        Bundle params = new Bundle();
        params.putInt("id_ocorrencia", id_ocorrencia);
        intent.putExtras(params);

        ViewOcorrencia.this.startActivity(intent);
    }

    public void persistLocalOcorrencia (OcorrenciaEntity ocorrenciaEntity) {
        if(ocorrenciaEntity == null)
            return;
        DBController dbController = new DBController(getBaseContext());
        Cursor cursor = dbController.getOcorrencia(ocorrenciaEntity.getId());
        String status = "";

        if(cursor != null){
            status = dbController.insereOcorrencia(ocorrenciaEntity.getId(), ocorrenciaEntity.getTelefone()+"",
                    ocorrenciaEntity.getSolicitante(), ocorrenciaEntity.getMunicipio(), ocorrenciaEntity.getEndereco(),
                    ocorrenciaEntity.getNumero(), ocorrenciaEntity.getBairro(), ocorrenciaEntity.getReferencia(),
                    ocorrenciaEntity.getPaciente(), ocorrenciaEntity.getSexo(), ocorrenciaEntity.getId(),
                    ocorrenciaEntity.getQueixa(), ocorrenciaEntity.getObservacoes(), ocorrenciaEntity.isEmergencia(),
                    ocorrenciaEntity.getStatus(), ocorrenciaEntity.getData(), ocorrenciaEntity.getId_atendente());
        }

        if(status.equals("success"))
            Info("Ocorrência salva para consulta offline.");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        //adicione os valores que precisam ser salvos da gaveta para o pacote
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        //lidar com a imprensa volta : D fechar a gaveta primeiro e se a gaveta é fechada perto a atividade
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
