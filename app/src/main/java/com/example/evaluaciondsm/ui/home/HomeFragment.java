package com.example.evaluaciondsm.ui.home;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaluaciondsm.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private EditText txt_code, txt_price, txt_description;

    SQLiteDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txt_code = binding.txtCode;
        txt_price = binding.txtPrice;
        txt_description = binding.txtDescription;

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        binding.btnSearchP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Metodo para guardar los productos
    public void save() {
        if (txt_code.getText().toString().isEmpty() || txt_price.getText().toString().isEmpty() || txt_description.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        db = getActivity().getBaseContext().openOrCreateDatabase("evaluacion", getActivity().MODE_PRIVATE, null);
        db.execSQL("create table if not exists products(code int primary key, price real, description text)");
        db.execSQL("insert into products values(" + txt_code.getText().toString() + "," + txt_price.getText().toString() + ",'" + txt_description.getText().toString() + "')");
        Toast.makeText(getActivity(), "Producto registrado", Toast.LENGTH_SHORT).show();
        db.close();
    }

    //Metodo para buscar productos
    public void search() {
        if (txt_code.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Debe ingresar un codigo", Toast.LENGTH_SHORT).show();
            return;
        }
        db = getActivity().getBaseContext().openOrCreateDatabase("evaluacion", getActivity().MODE_PRIVATE, null);
        db.execSQL("create table if not exists products(code int primary key, price real, description text)");
        db.execSQL("select * from products where code=" + txt_code.getText());
        Cursor cursor = db.rawQuery("select * from products where code=" + txt_code.getText(), null);
        if (cursor.moveToFirst()) {
            txt_price.setText(cursor.getString(1));
            txt_description.setText(cursor.getString(2));
        } else {
            Toast.makeText(getActivity(), "No existe un producto con dicho codigo", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void delete() {
        if (txt_code.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Debe ingresar un codigo", Toast.LENGTH_SHORT).show();
            return;
        }
        db = getActivity().getBaseContext().openOrCreateDatabase("evaluacion", getActivity().MODE_PRIVATE, null);
        db.execSQL("create table if not exists products(code int primary key, price real, description text)");
        db.execSQL("delete from products where code=" + txt_code.getText());
        Toast.makeText(getActivity(), "Producto eliminado", Toast.LENGTH_SHORT).show();
        db.close();

    }

    public void edit() {
        if (txt_code.getText().toString().isEmpty() || txt_price.getText().toString().isEmpty() || txt_description.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        db = getActivity().getBaseContext().openOrCreateDatabase("evaluacion", getActivity().MODE_PRIVATE, null);
        db.execSQL("create table if not exists products(code int primary key, price real, description text)");
        db.execSQL("update products set price=" + txt_price.getText() + ", description='" + txt_description.getText() + "' where code=" + txt_code.getText());
        db.close();
    }

    public void cleanForm() {
    }

}