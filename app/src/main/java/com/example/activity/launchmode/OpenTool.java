package com.example.activity.launchmode;

import android.content.Context;
import android.content.Intent;

public final class OpenTool {
    public static void openStandard(Context context){
        Intent intent = new Intent(context, StandardActivity.class);
        context.startActivity(intent);
    }

    public static void openSingleTop(Context context){
        Intent intent = new Intent(context, SingleTopActivity.class);
        context.startActivity(intent);
    }

    public static void openSingleTask(Context context){
        Intent intent = new Intent(context, SingleTaskActivity.class);
        context.startActivity(intent);
    }

    public static void openSingleInstance(Context context){
        Intent intent = new Intent(context, SingleInstanceActivity.class);
        context.startActivity(intent);
    }
}
