package bombeiros.mg.gov.br.thefirecall.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hiago Paraguaçú on 02/12/2017.
 *
 */

public class Ocorrencia extends SQLiteOpenHelper{
    static final String __tablename__ = "OCORRENCIA2";
    static final String ID = "_id";
    static final String TELEFONE = "telefone";
    static final String SOLICITANTE = "solicitante";
    static final String MUNICIPIO = "municipio";
    static final String ENDERECO = "endereco";
    static final String NUMERO = "numero";
    static final String BAIRRO = "bairro";
    static final String REFERENCIA = "referencia";
    static final String PACIENTE = "paciente";
    static final String SEXO = "sexo";
    static final String IDADE = "idade";
    static final String QUEIXA = "queixa";
    static final String OBSERVACOES = "observacoes";
    static final String EMERGENCIA = "emergencia";
    static final String STATUS = "status";
    static final String DATA = "data";
    static final String ID_ATENDENTE = "id_atendente";

    public Ocorrencia(Context context) {
        super(context, __tablename__, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ __tablename__ +" ("
                + ID + " integer primary key autoincrement,"
                + TELEFONE + " text,"
                + SOLICITANTE + " text,"
                + MUNICIPIO + " text,"
                + ENDERECO + " text,"
                + NUMERO + " integer,"
                + BAIRRO + " text,"
                + REFERENCIA + " text,"
                + PACIENTE + " text,"
                + SEXO + " text,"
                + IDADE + " integer,"
                + QUEIXA + " text,"
                + OBSERVACOES + " text,"
                + EMERGENCIA + " text,"
                + STATUS + " text,"
                + DATA + " text,"
                + ID_ATENDENTE + " integer"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + __tablename__);
        onCreate(db);
    }
}
