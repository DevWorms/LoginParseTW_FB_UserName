package ejemplo.devworms.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by sergio on 26/10/15.
 */
public class MainActivity extends Activity {

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

    public void compartirFb(View view) {

        iCompartir = (ImageView) findViewById(R.id.iCompartir);
        // Create an object
        /*ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "books.book")
                .putString("og:title", "A Game of Thrones")
                .putString("og:description", "In the frozen wastes to the north of Winterfell, sinister and supernatural forces are mustering.")
                .putString("books:isbn", "0-553-57340-3")
                .build();


        // Create an action
       /* ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("books.reads")
                .putObject("book", object)
                .build();
*//*

        // Create the content
        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("book")
                .setAction(action)
                .build();

        ShareDialog.show(this, content);*/

// compartir una imagen
        //Se saca la imagen de los recursos
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.salchichas);

       /*SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
               .setUserGenerated(true)
               .build();*/
      /*  SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();*/
        //Compartir un enlace con links*/

       /* ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://2014.spaceappschallenge.org/project/mision-tierra-2040/"))
                .setContentTitle("Aqui empezó a desarrollarse Misión Tierra")
                .setContentDescription("En este evento, fué dónde aprendimos a programar en corona SDK\nla dirección de la versión final del jugo es:\nGoogle play: https://play.google.com/store/apps/details?id=com.raptoruvg.misiontierra2040&hl=es_419\n iTunes: https://itunes.apple.com/mx/app/mision-tierra/id963621670?mt=8")
                .setImageUrl(Uri.parse("https://lh6.ggpht.com/SMcpxShLQD42zuBMx33Hovh7fvYG0pYabp8aN4cLhsiUEFzOdcLlpD7_qeGfRiyZCQ=h900-rw"))
                .build();
*/
        //Compartir texto


        // Create an object
  /*      ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "books.reads")
                .putString("og:title", "A Game of Thrones")
                .putString("og:description", "In the frozen wastes to the north of Winterfell, sinister and supernatural forces are mustering.")
                .putString("books:isbn", "0-553-57340-3")
                //.putPhoto("image", photo)
                .build();
        // Create an action
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("books.reads")
                .putObject("book", object)
                .putPhoto("image", photo)
                .build();

        // Create the content
        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("book")

                .setAction(action)
                .build();

*/

        //Compartir texto


// Create an object
        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "books.book")
                .putString("og:title", "A Game of Thrones")
                .putString("og:description", "In the frozen wastes to the north of Winterfell, sinister and supernatural forces are mustering.")
                .putString("books:isbn", "0-553-57340-3")
                .build();

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .setUserGenerated(true)
                .build();

        // Create an action
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("books.reads")
                .putObject("book", object)
                .putPhoto("image", photo)
                .build();
// Create the content
        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("book")
                .setAction(action)
                .build();
        ShareDialog.show(this, content);


    }

    public void compartirTw(View view) {

        Uri path = Uri.parse("android.resource://ejemplo.devworms.login/" + R.drawable.salchichas);

      //  File myImageFile = new File("/path/to/image");
       // Uri myImageUri = Uri.fromFile(myImageFile);
        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text("esto es un tweet")
                .image(path);
        builder.show();


        /*
           try {


            BitmapDrawable image = (BitmapDrawable) imgView.getDrawable();

            OutputStream outStream = null;

            String extStorageDirectory = Environment
                    .getExternalStorageDirectory().toString();

            File file = new File(extStorageDirectory, "image.PNG");

            outStream = new FileOutputStream(file);
            imgReceta.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();


            Uri ImageUri = Uri.fromFile(file);

            //  File myImageFile = new File("/path/to/image");
            // Uri myImageUri = Uri.fromFile(myImageFile);
            TweetComposer.Builder builder = null;

            builder = new TweetComposer.Builder(this.getActivity())
                    .text("¡Me encanta esta receta!")
                    .url(new URL("http://appcocina.parseapp.com"))
                    .image(ImageUri);
            builder.show();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        // hay que tener los permisos de
            < uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
            <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
            documentacion aqui https://dev.twitter.com/mopub/overview/fabric

         */


    }
}