package com.angelagloria.ugd_datapersistent9700;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AddFragment extends Fragment {

    TextInputEditText editText, editText1, editText2;
    Button addBtn,cancelBtn;
    TextInputLayout name_layout,number_layout,age_layout;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addUser();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(AddFragment.this).commit();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        editText = view.findViewById(R.id.input_name);
        editText1 = view.findViewById(R.id.input_number);
        editText2 = view.findViewById(R.id.input_age);

        addBtn = view.findViewById(R.id.btn_add);
        cancelBtn = view.findViewById(R.id.btn_cancel);

        name_layout = view.findViewById(R.id.input_name_layout);
        number_layout = view.findViewById(R.id.input_number_layout);
        age_layout = view.findViewById(R.id.input_age_layout);

        return view;
    }


    private void addUser(){
        final String name = editText.getText().toString();
        final String number = editText1.getText().toString();
        final String age = editText2.getText().toString();

        if(editText.getText().toString().equals("")){
            name_layout.setError("Please fill name correctly");
        }
        if(editText1.getText().toString().equals("")){
            number_layout.setError("Please fill number correctly");
        }
        if(editText2.getText().toString().equals("")){
            age_layout.setError("Please fill age correctly");
        }
        else
        {
            class AddUser extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    User user = new User();
                    user.setFullName(name);
                    user.setNumber(number);
                    user.setAge(age);

                    DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                            .userDao()
                            .insert(user);
                    return null;
                }


                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(getActivity().getApplicationContext(), "User saved", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                    editText1.setText("");
                    editText2.setText("");
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.hide(AddFragment.this).commit();
                }
            }

            AddUser add = new AddUser();
            add.execute();
        }
    }


}
