package com.example.simple_todo_app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.simple_todo_app.FileHelper;
import com.example.simple_todo_app.R;

import java.util.ArrayList;

public class InscriptionFragment extends Fragment {
    EditText editNote;
    EditText editNom;
    EditText editPrenom;
    EditText editDateNaissance;
    EditText editFiliere;
    EditText editClasse;
    Button addButton;
    ListView noteList;

    ArrayList<String> noteArrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);

        //editNote = view.findViewById(R.id.editNote);
        editNom = view.findViewById(R.id.editNom);
        editPrenom = view.findViewById(R.id.editPrenom);
        editDateNaissance = view.findViewById(R.id.editDateNaissance);
        editFiliere = view.findViewById(R.id.editFiliere);
        editClasse = view.findViewById(R.id.editClasse);
        addButton = view.findViewById(R.id.addButton);
        noteList = view.findViewById(R.id.noteList);

        noteArrayList = FileHelper.readDate(getActivity());
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, noteArrayList);
        noteList.setAdapter(arrayAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = editNom.getText().toString().trim();
                noteArrayList.add(note);
                editNote.setText("");
                FileHelper.writeData(noteArrayList, getActivity().getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
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
                        noteArrayList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        FileHelper.writeData(noteArrayList, getActivity().getApplicationContext());
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        return view;
    }
}
