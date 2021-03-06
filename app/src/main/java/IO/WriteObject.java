package IO;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import AppModels.CharSheet;


public class WriteObject {

    //need context to access file path
    private Context context;

    //constructor for context
    public WriteObject(Context context){
        this.context=context;
    }


    public void serializeCharacter(CharSheet character) {

        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        String path =  context.getFilesDir().getAbsolutePath();
        String fileName = (character.getCharacterName()).replaceAll(" ", "_") + ".ser";
        File characterFile = new File(path + "/" + fileName);

        try {

            fout = new FileOutputStream(characterFile);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(character);


        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
