package ejemplo.devworms.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by sergio on 26/10/15.
 */
public class MainActivity extends Activity{

    TextView tNombre;
    TextView tCorreo;
    EditText eTCompartir;
    ImageView iImagenPerfil;
    ImageView iCompartir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void compartir(View view)
    {
        // Create an object
        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "books.book")
                .putString("og:title", "A Game of Thrones")
                .putString("og:description", "In the frozen wastes to the north of Winterfell, sinister and supernatural forces are mustering.")
                .putString("books:isbn", "0-553-57340-3")
                .build();


        // Create an action
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("books.reads")
                .putObject("book", object)
                .build();

        // Create the content
        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("book")
                .setAction(action)
                .build();

        ShareDialog.show(this, content);
    }
}
