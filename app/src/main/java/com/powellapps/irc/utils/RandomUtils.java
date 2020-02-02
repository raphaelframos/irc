package com.powellapps.irc.utils;

import android.app.AlertDialog;
import android.content.Context;

public class RandomUtils {

    public static void mostraAlerta(String titulo, String mensagem, Context context) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle(titulo).setMessage(mensagem).setNeutralButton("Ok", (dialog, which) -> dialog.cancel()).show();
    }
}
