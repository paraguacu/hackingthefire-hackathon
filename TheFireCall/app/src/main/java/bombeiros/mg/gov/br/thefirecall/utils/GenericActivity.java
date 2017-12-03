package bombeiros.mg.gov.br.thefirecall.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.BaseDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONObject;

import bombeiros.mg.gov.br.thefirecall.R;
import bombeiros.mg.gov.br.thefirecall.views.InicioPatrulha;
import bombeiros.mg.gov.br.thefirecall.views.ViewOcorrencias;

/**
 * Created by Hiago Paraguaçú on 02/12/2017.
 *
 */

public class GenericActivity extends AppCompatActivity implements AppCompatCallback {
    private Socket mSocket;

//    {
//        try {
//            mSocket = IO.socket("http://172.10.100.78:8000/chat");
//        } catch (URISyntaxException e) {
//            Error(e);
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    public void conectScoket () {
//        try {
//            mSocket = IO.socket("http://172.10.100.78:5000");
//        } catch (URISyntaxException e) {
//            Error(e);
//        }
//        mSocket.on("message", onNewMessage);
//
//        mSocket.connect();
////        Emitter emitter =
////        mSocket.send("message","teste socket io");
////        System.out.println(emitter);
//    }

    public void emit(){
        Emitter emmiter = mSocket.emit("reply", "testes 2");
        Toast.makeText(this, "", Toast.LENGTH_LONG ).show();
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            //Info(args.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
//                    try {
//                        username = data.getString("username");
//                        message = data.getString("message");
//                    } catch (JSONException e) {
//                        return;
//                    }

                    // add the message to view
                    // addMessage(username, message);
                    if (data != null)
                    Info(data.toString());
                }
            });
        }
    };

    public Drawer leftDrawer (Bundle savedInstanceState, Toolbar toolbar){
        Drawer result;
        try {
            View header_drawer = LayoutInflater.from(getApplication()).inflate(R.layout.drawer_header, null);
            ImageView imageView = (ImageView) header_drawer.findViewById(R.id.img_header_drawer);
            imageView.setImageResource(R.drawable.logo_bombeiro);

            result = new DrawerBuilder()
                    .withActivity(this)
                    .withRootView(R.id.view_conteiner) //coloca o container no layout que precisa sob a actionbar
                    .withToolbar(toolbar)
                    .withDisplayBelowStatusBar(false)
                    .withActionBarDrawerToggleAnimated(true)
                    .withSelectedItem(-1)       //deseleciona autommatico na hora de abrir o app
                    .withHeader(header_drawer)  //cabeçalho

                    //.withFooter(footer_drawer)  //rodapé
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName(R.string.label_start).withIcon(FontAwesome.Icon.faw_area_chart),
                            new PrimaryDrawerItem().withName(R.string.label_ocorrencias).withIcon(FontAwesome.Icon.faw_book),
                            new PrimaryDrawerItem().withName(R.string.label_desativar).withIcon(FontAwesome.Icon.faw_ban)
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, final IDrawerItem drawerItem) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = null;
                                    switch (((BaseDrawerItem) drawerItem).getName().getTextRes()){
                                        case R.string.label_start:
                                            intent = new Intent(GenericActivity.this, InicioPatrulha.class);
                                            break;
                                        case R.string.label_ocorrencias:
                                            intent = new Intent(GenericActivity.this, ViewOcorrencias.class);
                                            break;
                                        case R.string.label_desativar:
                                            Info("clicou no terceiro");
                                        break;
                                    }
                                    if (intent != null) {
                                        GenericActivity.this.startActivity(intent);
                                    }
                                }
                            });
                            return false;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .build();
            return result;
        }
        catch (Exception e){
            Error(e);
            return null;
        }
    }

    public boolean isConected() {
        ConnectionDetector cd = new ConnectionDetector(this);
        boolean status;

        if (!cd.isConnectingToInternet()) {
            status = false;
            Toast.makeText(this, "O aplicativo não consegue se conectar à internet, favor " +
                    "verificar sua conexão.", Toast.LENGTH_LONG).show();
        } else {
            status = true;
            //Toast.makeText(this, R.string.nao_conectado, Toast.LENGTH_LONG).show();
        }
        return status;
    }


    public void Error(Exception e){
        Toast.makeText(this, "Houve o seguinte erro:\n" + e, Toast.LENGTH_LONG).show();
    }

    public void Error(String e){
        Toast.makeText(this, "Houve o seguinte erro:\n" + e, Toast.LENGTH_LONG).show();
    }

    public void Info(String info){
        Toast.makeText(this, "INFO: " + info, Toast.LENGTH_LONG).show();
    }

    public void Warn(String warn){
        Toast.makeText(this, "WARN: "+ warn, Toast.LENGTH_LONG).show();
    }
}
