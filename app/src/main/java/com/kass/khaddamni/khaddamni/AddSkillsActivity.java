package com.kass.khaddamni.khaddamni;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.jpttrindade.tagview.OnTagClickListener;
import br.com.jpttrindade.tagview.Tag;
import br.com.jpttrindade.tagview.TagView;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;


public class AddSkillsActivity extends AppCompatActivity {



   LinearLayout v;
  TagView tagView;
    AutoCompleteTextView textViewSkill;
    Button uploadImage;
    ImageView imageOffre;
    String url;
    int id=2;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String UPLOAD_URL =LINK+"upload.php";
    String[] skills={"Android ","java","IOS","SQL","JDBC","Web services","IOT"};
    List<String> skillsList = new ArrayList<>();
    List<String> skillsList1 = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skills);
        for(int i=0; i<skills.length;i++)
        {
            skillsList.add(skills[i]);
            skillsList1.add(skills[i]);
        }


        tagView = (br.com.jpttrindade.tagview.TagView) findViewById(R.id.tagview);

        id = getIntent().getIntExtra("id",1);
        System.out.println(id);

        v=(LinearLayout) findViewById(R.id.viewgroup) ;
        textViewSkill=(AutoCompleteTextView) findViewById(R.id.skill) ;
        uploadImage=(Button) findViewById(R.id.uploadImage);
        imageOffre=(ImageView) findViewById(R.id.imageOffre) ;

        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,skills);

        textViewSkill.setAdapter(adapter);
        textViewSkill.setThreshold(1);


        tagView.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position, int clickType) {
                switch (clickType) {
                    case TagView.ONCLICK_EDIT:
                        Log.d("DEBUG", "OnClickEdit");
                        break;
                    case TagView.ONCLICK_REMOVE:
                        Log.d("DEBUG", "OnClickRemove");
                        break;
                    case TagView.ONCLICK_DEFAULT:
                        Log.d("DEBUG", "OnClickDefault");
                }

            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


    }



    public void buttonAddSkillsClicked(View v)
    {



              if(!textViewSkill.getText().toString().equals("")) {
                  if(!skillsList.contains(textViewSkill.getText().toString()))
                  {
                      if(skillsList1.contains(textViewSkill.getText().toString()))
                      {
                          AlertDialog alertDialog = new AlertDialog.Builder(AddSkillsActivity.this).create();
                          alertDialog.setTitle("Offer Skills");
                          alertDialog.setMessage("You already added this skill! choose another one!");
                          alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {
                                          dialog.dismiss();
                                      }
                                  });
                          alertDialog.show();
                      }
                      else
                      {
                          AlertDialog alertDialog = new AlertDialog.Builder(AddSkillsActivity.this).create();
                          alertDialog.setTitle("Offer Skills");
                          alertDialog.setMessage("This skill is not accepted!");
                          alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {
                                          dialog.dismiss();
                                      }
                                  });
                          alertDialog.show();
                      }
                  }

                    else {
                      Tag tag = new Tag(textViewSkill.getText().toString(), Color.GRAY);

                      tagView.addTag(tag);
                      skillsList.remove(textViewSkill.getText().toString());

                      RequestQueue rq = Volley.newRequestQueue(this);


                      url = LINK + "addSkill.php?id=" + id + "&skill=" + textViewSkill.getText().toString().replace(" ", "+++");
                      System.out.println(url);
                      textViewSkill.setText("");
                      StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                          @Override
                          public void onResponse(String response) {
                              System.out.println(response);

                          }
                      }, new Response.ErrorListener() {

                          @Override
                          public void onErrorResponse(VolleyError error) {

                          }
                      }
                      );

                      rq.add(stringRequest);
                      rq.start();

                  }
              }





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageOffre.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void buttonConfirmClicked(View v)
    {

        System.out.println(UPLOAD_URL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        System.out.println("good job omar");
                        System.out.println(s);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog


                        //Showing toast
                        System.out.println("erreur");
                        System.out.println(volleyError);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name


                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("image", image);
                params.put("idOffre", id+"");

                //returning parameters
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        requestQueue.start();





        Intent i= new Intent(getBaseContext(),AllActivity.class);
        startActivity(i);




    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
