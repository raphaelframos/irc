package com.powellapps.irc.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.powellapps.irc.LoginActivity;
import com.powellapps.irc.MainActivity;
import com.powellapps.irc.R;
import com.powellapps.irc.model.User;
import com.powellapps.irc.utils.ConstantsUtils;
import com.powellapps.irc.utils.FirebaseUtils;

public class NickNameController {

    public static void alertaDeNickName (final FragmentActivity activity, final User user) {
        final AlertDialog.Builder alerta = new AlertDialog.Builder(activity);

        LinearLayout container = new LinearLayout(activity.getApplicationContext());
        container.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(60, -20, 60, 0);

        final EditText editTextlocal = new EditText(activity.getApplicationContext());

        editTextlocal.setLayoutParams(lp);
        editTextlocal.setHint("Informe seu apelido:");
        editTextlocal.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES|InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        container.addView(editTextlocal, lp);
        alerta.setView(container);

        alerta.setTitle("ATENÇÃO!:").setMessage("É preciso definir um apelido").setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseUtils.getBanco().collection(ConstantsUtils.USERS).whereEqualTo("nickname", editTextlocal.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            user.setNickname(editTextlocal.getText().toString());
                            FirebaseUtils.saveUser(user);
                            SharedPreferences sharedPreferences = activity.getSharedPreferences("NICKNAMES", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("nick",editTextlocal.getText().toString());
                            editor.commit();

                        } else {
                            Toast.makeText(activity, "Nick já utilizado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();



    }
}
