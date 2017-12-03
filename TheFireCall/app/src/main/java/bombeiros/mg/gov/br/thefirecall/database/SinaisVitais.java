package bombeiros.mg.gov.br.thefirecall.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hiago Paraguaçú on 02/12/2017.
 *
 */

public class SinaisVitais extends SQLiteOpenHelper{
    static final String __tablename__ = "SINAISVITAIS1";
    static final String ID = "_id";
    static final String ID_OCORRENCIA = "id_ocorrencia";
    static final String PRESSAO = "pressao";
    static final String FREQUENCIA_CARDIACA = "frequencia_cardiaca";
    static final String SATURACAO_OXIGENIO = "saturacao_oxigenio";
    static final String TEMPERATURA = "temperatura";

    public SinaisVitais(Context context) {
        super(context, __tablename__, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ __tablename__ +" ("
                + ID + " integer primary key autoincrement,"
                + ID_OCORRENCIA + " text,"
                + PRESSAO + " text,"
                + FREQUENCIA_CARDIACA + " text,"
                + SATURACAO_OXIGENIO + " text,"
                + TEMPERATURA + " integer"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + __tablename__);
        onCreate(db);
    }
}
