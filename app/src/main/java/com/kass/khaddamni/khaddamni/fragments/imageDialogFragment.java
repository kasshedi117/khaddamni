package com.kass.khaddamni.khaddamni.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.util.Base64;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kass.khaddamni.khaddamni.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import info.hoang8f.widget.FButton;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

/**
 * Created by omar on 11/12/2016.
 */


public class imageDialogFragment extends DialogFragment {
    Bitmap bitmap;
    ImageView imageToUpload;
    String UPLOAD_URL=LINK+"uploadProfileImage.php";

    private DialogInterface.OnDismissListener onDismissListener;

    public static imageDialogFragment newInstance(int title) {
        imageDialogFragment frag = new imageDialogFragment();
        return  frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View view= inflater.inflate(R.layout.alert_image, container, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String idUser = preferences.getString("id", "");
        FButton uploadImage=(FButton) view.findViewById(R.id.uploadImage);
        FButton confirmButton=(FButton) view.findViewById(R.id.confirmUpload);
        FButton cancelButton=(FButton) view.findViewById(R.id.cancelUpload);
        imageToUpload=(ImageView) view.findViewById(R.id.dialog_imageview);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
             startActivityForResult(Intent.createChooser(in, "Select Picture"), 1);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                getDialog().dismiss();
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
                        params.put("idUser", idUser);

                        //returning parameters
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                //Creating a Request Queue
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                //Adding request to the queue
                requestQueue.add(stringRequest);
                requestQueue.start();


            }
        });








        return  view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("omar wsolt lenna tawa");
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);



                //Setting the Bitmap to ImageView
                imageToUpload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}