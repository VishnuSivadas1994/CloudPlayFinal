package com.vi.cloudplayfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CloudStorageHandler extends AppCompatActivity implements View.OnClickListener{
    private static final int REQ = 1;
    Button view;
    Uri path;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_storage_handler);
        storageReference= FirebaseStorage.getInstance().getReference();

        progressDialog=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();

        view=findViewById(R.id.selectmusic);

        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==this.view){
            showFiles();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ && resultCode==RESULT_OK){
            if (data.getClipData()!=null){
                int total=data.getClipData().getItemCount();
                //int c=SpeedMode.uri.size();
                for (int i=0;i<total;i++){
                    //Toast.makeText(this, data.getClipData().getItemAt(i).toString(), Toast.LENGTH_SHORT).show();

                    progressDialog.setMessage("Uploading Files...");
                    progressDialog.show();
                    //path=Uri.parse(SpeedMode.uri.get(c-1));
                    //c--;
                    path=data.getClipData().getItemAt(i).getUri();
                    //String filename=getFileName(fileuri);
                    String string=getFileName(path);
                    String folder=firebaseAuth.getCurrentUser().getEmail().toString();

                    StorageReference FileUpload=storageReference.child(folder).child(string);
                    final int iii=i;
                    FileUpload.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),"Done!!!",Toast.LENGTH_SHORT).show();


                        }
                    });


                }
            }
            if (data.getData()!=null){
                path=data.getData();
                String string=getFileName(path);
                String folder=firebaseAuth.getCurrentUser().getEmail().toString();
                progressDialog.setMessage("Uploading Files...");
                progressDialog.show();
                StorageReference FileUpload=storageReference.child(folder).child(/*String.valueOf(i)+"____"+*/string);
                //final int iii=i;
                FileUpload.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(),"Done!!!",Toast.LENGTH_SHORT).show();


                    }
                });
            }

        }
    }

    private void showFiles(){
        Intent intent=new Intent();
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select files"),REQ);

    }
    public String getFileName(Uri uri){
        String result=null;
        if (uri.getScheme().equals("content")){
            Cursor cursor=getContentResolver().query(uri,null,null,null,null);
            try {
                if (cursor!=null && cursor.moveToFirst()){
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if (result==null){
            result=uri.getPath();
            int cut=result.lastIndexOf('/');
            if (cut!=-1){
                result=result.substring(cut+1);
            }

        }
        return result;
    }
}
