package com.example.simple_todo_app.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.simple_todo_app.FileDemandeHelper;
import com.example.simple_todo_app.FileHelper;
import com.example.simple_todo_app.R;

import java.util.ArrayList;

public class DemandeFragment extends Fragment {



    EditText editNote;
    EditText editNom;
    EditText editPrenom;
    EditText editDateNaissance;
    EditText editFiliere;
    EditText editClasse;
    Button addButton;

    EditText editContenu;
    EditText editObjectif;
    ListView noteList;

    ArrayList<String> noteArrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demande, container, false);

        //editNote = view.findViewById(R.id.editNote);
        editNom = view.findViewById(R.id.editNom);
        editPrenom = view.findViewById(R.id.editPrenom);
        editDateNaissance = view.findViewById(R.id.editDateNaissance);
        editFiliere = view.findViewById(R.id.editFiliere);
        editClasse = view.findViewById(R.id.editClasse);
        addButton = view.findViewById(R.id.addButton);
        editContenu = view.findViewById(R.id.contenudemande);
        editObjectif = view.findViewById(R.id.objectif);
        noteList = view.findViewById(R.id.noteList);

        noteArrayList = FileDemandeHelper.readDate(getActivity());
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, noteArrayList);
        noteList.setAdapter(arrayAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = editNom.getText().toString().trim();
                String prenom = editPrenom.getText().toString().trim();
                String filiere = editFiliere.getText().toString().trim();
                String objectif = editObjectif.getText().toString().trim();
                String contenu = editContenu.getText().toString().trim();
                String classe = editClasse.getText().toString().trim();

                // Créer le texte du message à envoyer
                String message = "Nom: " + nom + "\n" +
                        "Prénom: " + prenom + "\n" +
                        "Filière: " + filiere + "\n" +
                        "Classe: " + classe + "\n" +
                        "Objectif: " + objectif + "\n" +
                        "Contenu: " + contenu + "\n";

                // Créer l'intention ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Nouvelle demande");
                intent.putExtra(Intent.EXTRA_TEXT, message);

                // Vérifier si l'appareil possède une application de messagerie
                PackageManager packageManager = getActivity().getPackageManager();
                if (intent.resolveActivity(packageManager) != null) {
                    // Lancer l'application de messagerie par défaut
                    startActivity(Intent.createChooser(intent, "Envoyer par e-mail"));
                } else {
                    // Gérer le cas où aucune application de messagerie n'est disponible
                    Toast.makeText(getActivity(), "Aucune application de messagerie n'est installée", Toast.LENGTH_SHORT).show();
                }

                // Effacer les champs de saisie
                editNom.setText("");
                editPrenom.setText("");
                editFiliere.setText("");
                editObjectif.setText("");
                editContenu.setText("");
                editClasse.setText("");

                noteArrayList.add(message);
                FileHelper.writeData(noteArrayList, getActivity().getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
            }
        });


        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Supprimer");
                alert.setMessage("Voulez-vous supprimer cette Demande ?");
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

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demande, container, false);
        Button inscriptionButton = view.findViewById(R.id.inscription);
        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Créer une instance du fragment InscriptionFragment
                InscriptionFragment inscriptionFragment = new InscriptionFragment();

                // Obtenir le FragmentManager pour gérer les fragments
                FragmentManager fragmentManager = getFragmentManager();

                // Remplacer le fragment actuel par le fragment InscriptionFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_inscription, inscriptionFragment)
                        .addToBackStack(null) // Ajouter à la pile arrière pour pouvoir revenir au fragment précédent
                        .commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }*/
}
