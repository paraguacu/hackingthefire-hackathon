package bombeiros.mg.gov.br.thefirecall.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Hiago Paraguaçú on 21/03/2016.
 *
 */
public class ConnectionDetector {
    private Context context;

    ConnectionDetector(Context context){
        this.context = context;
    }

    /**
     * Retorna se o app está conectado na internet
     * @return
     */
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
