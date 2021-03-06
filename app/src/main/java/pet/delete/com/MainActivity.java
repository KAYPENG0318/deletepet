        package pet.delete.com;

        import android.net.Uri;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;

        import java.util.ArrayList;

        import pet.delete.com.R;

public class MainActivity extends AppCompatActivity {

    //http://chikuo.tw/android-x-firebase-02-%E8%B3%87%E6%96%99%E5%AD%98%E5%8F%96/
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    TextView txtpetname;
    // TextView txtdate;
    EditText txtdate;
    String putPetname;
    String putPetdate;

    //從FIREBASE 取出資料
    public static  ArrayList<PetData> list;

    // Create a storage reference from our app
    //storage
    FirebaseStorage storage ;
    StorageReference storageRef ;

    String uri;
    //傳照片到 storageRef
    //字串轉為 URI
    Uri stroagepic ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtpetname = (TextView)findViewById(R.id.petName);
        //txtdate = (TextView)findViewById(R.id.Exdate);
        putPetname = txtpetname.getText().toString();

//        txtdate = (EditText)findViewById(R.id.date);

        // StorageReference imagesRef = storageRef.child("images");
        //String name = imagesRef.getName();

    }


    //刪除firebase資料
    public void buttonDelete(View v)
    {

        txtdate = (EditText)findViewById(R.id.date);
        putPetdate = txtdate.getText().toString();
        list=new ArrayList<>();


        //在這個子節點 notes 設定監聽 只要資料有變就會執行 ValueEventListener 這物件
        //這叫CALLBACK
        myRef.child("notes").addValueEventListener(new ValueEventListener() {
            PetData note;


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("hello-->", "" );

                //  dataSnapshot ，取得FIREBASE 裡 notes 節點裡全部內容
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    //FIREBASE 裡取出來的資料
                    note = noteDataSnapshot.getValue(PetData.class);
                    String key = noteDataSnapshot.getKey();
                    //Log.d("ID",key);

                    String fbdate =note.getdate();
                    //Log.d("date",fbdate);


                    uri =note.getUri();
                    //Log.d("uri",uri);
                    //stroagepic =Uri.parse(uri);

                    //取出uri

                    //KEY
                    //取到 images
                    //StorageReference imagesRef = storageRef.child("images");
//                    String name = storageRef.getName();
//                    Log.d("imagesRef",name);
//
//                    //抓出strogae路徑  程式可以跑 取到是空的
//                    String path = storageRef.getPath();
//                    Log.d("path",path);

                    int result = putPetdate.compareTo(fbdate);
                    if(result >= 0)
                    //if(putPetdate.equals(fbdate))
                    {

                        //刪除Firebase那筆資料 這筆key刪掉
                        myRef.child("notes").child(key).removeValue();

                        Delete(uri);

                        //應該是這裡要刪stroage
                        //TEST ERROR
                        //uri="gs://petfirebaseproject-32779.appspot.com/image/1522771685593";

                    }

                    //OK  這邊可以接
//                    list.add(note);
//                    Log.d("PDAlistsize-->", "" + list.size());

                }
//                for(int i=0; i<list.size();i++){
//                    String date = list.get(i).getdate();
//                    Log.d("date--",date);
//                    String petName = list.get(i).getPetName();
//                    Log.d("petName--",petName);
//                    //String key =  myRef.getKey();
//                    //Log.d("key--",list.get(i));
//                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Toast.makeText(MainActivity.this,"已執行",Toast.LENGTH_SHORT).show();
    }


    public void  Delete(String uri)
    {

        Toast.makeText(MainActivity.this,"Delete----",Toast.LENGTH_SHORT).show();

        storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        //StorageReference storageRef = storage.getReference();

        StorageReference photoRef = storage.getReferenceFromUrl(uri);

        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this,"onSuccess",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Toast.makeText(MainActivity.this,"onFailure",Toast.LENGTH_SHORT).show();
            }
        });

//// Create a reference to the file to delete
//
//        // Create a reference to the file to delete
//
//       // StorageReference desertRef = storageRef.child("image/1522774013269.jpeg");
//
//// Delete the file
//        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                // File deleted successfully
//                Toast.makeText(MainActivity.this,"onSuccess",Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Uh-oh, an error occurred!
//                Toast.makeText(MainActivity.this,"onFailure",Toast.LENGTH_SHORT).show();
//            }
//        });



   }




}