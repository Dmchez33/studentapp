package com.example.simple_todo_app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simple_todo_app.FileHelper;
import com.example.simple_todo_app.R;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class DodoListFragment extends Fragment {
    EditText editNote;
    Button addButton;
    ListView noteList;

    ArrayList<String> noteArrayList1 = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter1;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dodo_list, container, false);

        editNote = view.findViewById(R.id.editNote);
        addButton = view.findViewById(R.id.addButton1);
        noteList = view.findViewById(R.id.noteList1);

        noteArrayList1 = FileHelper.readDate(getActivity());

        arrayAdapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, noteArrayList1);

        noteList.setAdapter(arrayAdapter1);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = editNote.getText().toString().trim();
                noteArrayList1.add(note);
                editNote.setText("");
                FileHelper.writeData(noteArrayList1, getActivity().getApplicationContext());

                arrayAdapter1.notifyDataSetChanged();
            }
        });

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Supprimer");
                alert.setMessage("Voulez-vous supprimer cette note ?");
                alert.setCancelable(false);
                alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        noteArrayList1.remove(position);
                        arrayAdapter1.notifyDataSetChanged();
                        FileHelper.writeData(noteArrayList1, getActivity().getApplicationContext());
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        return view;
    }
}
