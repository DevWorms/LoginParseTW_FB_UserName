package ejemplo.devworms.login.pinterest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;
import com.pinterest.android.pdk.Utils;

import ejemplo.devworms.login.R;

public class CreateBoardActivity extends AppCompatActivity implements View.OnClickListener{

    EditText boardName, boardDesc;
    Button saveButton;
    TextView responseView;

    private Button botonIzq, botonDer;
    private TextView txtMensajes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);
        setTitle("New Board");
        boardName = (EditText) findViewById(R.id.board_create_name);
        boardDesc = (EditText) findViewById(R.id.board_create_desc);
        responseView = (TextView) findViewById(R.id.board_response_view);
        saveButton = (Button) findViewById(R.id.save_button);

        botonIzq = (Button) findViewById(R.id.botonIzq);
        botonDer = (Button) findViewById(R.id.botonDer);
        txtMensajes = (TextView) findViewById(R.id.txtMensajes);


        botonIzq.setText("Cancelar");
        botonDer.setText("Aceptar");
        txtMensajes.setText("Ingrese la información");

        botonDer.setOnClickListener(this);
        botonIzq.setOnClickListener(this);

        botonDer.setVisibility(View.GONE);
        botonIzq.setVisibility(View.VISIBLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveBoard();
            }
        });

    }

    private void onSaveBoard() {
        String bName = boardName.getText().toString();
        if (!Utils.isEmpty(bName)) {
            PDKClient.getInstance().createBoard(bName, boardDesc.getText().toString(), new PDKCallback() {
                @Override
                public void onSuccess(PDKResponse response) {
                    Log.d(getClass().getName(), response.getData().toString());
                    responseView.setText(response.getData().toString());
                    txtMensajes.setText("Tablero creado");
                }

                @Override
                public void onFailure(PDKException exception) {
                    Log.e(getClass().getName(), exception.getDetailMessage());
                    responseView.setText(exception.getDetailMessage());
                    txtMensajes.setText("Ocurrió un error");
                }
            });
        } else {
            Toast.makeText(this, "Board name cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}
