package in.skonda.sqliteexamplewithdb;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
String a,b,c,d;
    MyDb myDb;
    String strings="";
    File myFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFile=new File(getFilesDir()+"/test.pdf");
    }



    public void pdfCreate(View view) {

        try {
            FileOutputStream output = new FileOutputStream(myFile);
            //Step 1
            Document document = new Document();
            //Step 2
            PdfWriter.getInstance(document, output);

            //Step 3
            document.open();

            //Step 4 Add content

                document.add(new Paragraph(strings));

            //Step 5: Close the document
            document.close();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pdfOpen(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(myFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void insertData(View view) {
        myDb =new MyDb(this);
        a="a1";
        b="b2";
        c="c3";
        d="d4";
        myDb.open();
        Long l=myDb.insertData(a,b,c,d);
        Cursor c=myDb.select();
        Log.d("code",l+" "+c.getCount());
        c.moveToFirst();
        do{
            Log.d("code",c.getPosition()+"");
            //strings[c.getPosition()]=c.getString(0)+","+c.getString(1)+","+c.getString(2)+","+c.getString(3);
            strings="\n"+strings+c.getString(0)+","+c.getString(1)+","+c.getString(2)+","+c.getString(3);
        }while (c.moveToNext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}