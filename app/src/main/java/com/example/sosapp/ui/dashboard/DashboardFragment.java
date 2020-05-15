package com.example.sosapp.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sosapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Set existing values in text field
        SharedPreferences spref_read = getActivity().getPreferences(Context.MODE_PRIVATE);


        FloatingActionButton fab = root.findViewById(R.id.save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor = spref.edit();

                String phone1, name1, phone2, name2, phone3, name3, phone4, name4, phone5, name5;
                
                phone1 = ((EditText) root.findViewById(R.id.phone1)).getText().toString();
                name1 = ((EditText) root.findViewById(R.id.name1)).getText().toString();
                phone2 = ((EditText) root.findViewById(R.id.phone2)).getText().toString();
                name2 = ((EditText) root.findViewById(R.id.name2)).getText().toString();
                phone3 = ((EditText) root.findViewById(R.id.phone3)).getText().toString();
                name3 = ((EditText) root.findViewById(R.id.name3)).getText().toString();
                phone4 = ((EditText) root.findViewById(R.id.phone4)).getText().toString();
                name4 = ((EditText) root.findViewById(R.id.name4)).getText().toString();
                phone5 = ((EditText) root.findViewById(R.id.phone5)).getText().toString();
                name5 = ((EditText) root.findViewById(R.id.name5)).getText().toString();

                String[] phone = {phone1, phone2, phone3, phone4, phone5};
                String[] name = {name1, name2, name3, name4, name5};

                for(int i=0;i<5;i++)
                {
                    if(!phone[i].isEmpty())
                    {
                        editor.putString("phone" + String.valueOf(i+1), phone[i]);
                    }
                    if(!name[i].isEmpty())
                    {
                        editor.putString("name" + String.valueOf(i+1), name[i]);
                    }
                }
                editor.commit();
                Snackbar.make(v, "Saved successfully.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return root;
    }
}
