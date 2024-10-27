package com.memolovesyou.darkira;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class darkiraa {
    private Context context;


    // Constructor to initialize context
    public darkiraa(Context context) {
        this.context = context;
    }

    // Public methods that can be called from MainActivity
    public void openGoogle() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));

        context.startActivity(intent);
    }

    public void openAnime() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https://www.crunchyroll.com")); // Example for anime site
        context.startActivity(intent);
    }

    public void openWhatsApp() {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
        if (intent != null) {
            context.startActivity(intent);
        } else {
            // Handle WhatsApp not installed
        }
    }

    public void openYouTube() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https://www.youtube.com"));
        context.startActivity(intent);
    }

    public void openTwitter() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https://www.twitter.com"));
        context.startActivity(intent);
    }
}
