package com.vi.cloudplayfinal;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class BackupHandler extends AppCompatActivity {

    private static final int RESULT_LOAD_MUSIC = 1;
    Button selectfiles;
    RecyclerView recyclerView;
    List<String> filenamelist,filesdonelist;
    BackupAdapter backupAdapter;
    StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_handler);

        mStorageReference= FirebaseStorage.getInstance().getReference();

        selectfiles=findViewById(R.id.selectfiles);
        recyclerView=findViewById(R.id.recbackup);
        filenamelist=new ArrayList<>();
        filesdonelist=new ArrayList<>();
        backupAdapter=new BackupAdapter(filenamelist,filesdonelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(backupAdapter);

        selectfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("audio/mp3");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SELECT MUSIC"),RESULT_LOAD_MUSIC);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_LOAD_MUSIC && resultCode==RESULT_OK){
            if (data.getClipData()!=null){//clip data fo multi


                int totalItemSelected=data.getClipData().getItemCount();
                for (int i=0;i<totalItemSelected;i++){

                    Uri fileuri=data.getClipData().getItemAt(i).getUri();
                    String filename=getFileName(fileuri);

                    filenamelist.add(filename);
                    filesdonelist.add("uploading");
                    backupAdapter.notifyDataSetChanged();

                    StorageReference FileUpload=mStorageReference.child("username").child(filename);
                    final int iii=i;
                    FileUpload.putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Done!!!",Toast.LENGTH_SHORT).show();
                            filesdonelist.remove(iii);

                            backupAdapter.notifyDataSetChanged();

                        }
                    });


                }

                Toast.makeText(getApplicationContext(),"MultiFiles",Toast.LENGTH_SHORT).show();


            }
            else if (data.getData() !=null){

                Toast.makeText(getApplicationContext(),"SingleFiles",Toast.LENGTH_SHORT).show();





            }


        }
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
