package com.memolovesyou.darkira;
import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
    private SpeechRecognizer sp;
    private static Random random;
    private TextView showquest;
    private TextToSpeech tts;
    private MediaPlayer mediaPlayer;
    TextView devil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();
        // Request microphone permission
        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        findById();
                        Intializetexttospeech();
                        initializeResult();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this, "Permission denied. Exiting...", Toast.LENGTH_SHORT).show();
                        finish(); // End activity
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void Intializetexttospeech() {
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                if (tts.getEngines().size() == 0) {
                    Toast.makeText(MainActivity.this, "Text-to-Speech engine is not available", Toast.LENGTH_SHORT).show();
                } else {
                    tts.setPitch(0.1f);
                    tts.setSpeechRate(0.6f);
                    String s = wish();
                    speak(s);
                }
            } else {
                Toast.makeText(MainActivity.this, "Initialization failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String wish() {
        String say = "";
        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);

        String devilLaugh = "HaHaHaHaHa";

        if (time >= 0 && time < 6) {
            say = "Ah, the witching hour! It's " + time + " o'clock, and I'm awake plotting your doom. " + devilLaugh + " What wicked deeds bring you here?";
        } else if (time >= 6 && time < 12) {
            say = "Good morning, mortal! It's " + time + " o'clock. Even the devil needs a cup of coffee to stir up trouble. " + devilLaugh + " What can I do for you today?";
        } else if (time >= 12 && time < 18) {
            say = "It's " + time + " o'clock, and I'm in the middle of my diabolical day. The light of day can’t hide me. " + devilLaugh + " What dark desires do you have?";
        } else if (time >= 18 && time < 24) {
            say = "Evening has arrived at " + time + " o'clock. The perfect time for sinister plans. " + devilLaugh + " What nefarious activities can I assist with tonight?";
        }

        return say;
    }

    public void speak(String s) {
        if (tts != null) {
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
            devil.setText(s);
        }
    }

    private void findById() {
        showquest = findViewById(R.id.apnebola);
        devil = findViewById(R.id.devilsay);

    }

    private void initializeResult() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            sp = SpeechRecognizer.createSpeechRecognizer(this);
            sp.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                }

                @Override
                public void onBeginningOfSpeech() {
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                }

                @Override
                public void onEndOfSpeech() {
                }

                @Override
                public void onError(int error) {
                    Toast.makeText(MainActivity.this, "Speech recognition error: " + error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    if (result != null && !result.isEmpty()) {
                        String spokenText = result.get(0);
                        Toast.makeText(MainActivity.this, spokenText, Toast.LENGTH_SHORT).show();
                        showquest.setText(spokenText);
                        response(spokenText);
                    }
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Speech recognition not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void response(String s) {
        String msg = s.toLowerCase();
        if (msg.contains("hi") || msg.contains("hello")) {
            speak(randomGreeting());
        } else if (msg.contains("help")) {
            speak(randomHelp());
        } else if (msg.contains("goodbye")) {
            speak(randomGoodbye());
        } else if (msg.contains("thank you")) {
            speak(randomThankYou());
        } else if (msg.contains("who are you")) {
            speak(randomWhoAreYou());
        } else if (msg.contains("what can you do")) {
            speak(randomWhatCanYouDo());
        } else if (msg.contains("tell me a secret")) {
            speak(randomTellMeASecret());
        } else if (msg.contains("time")) {
            Date date = new Date();
            String currentTime = DateUtils.formatDateTime(this, date.getTime(), DateUtils.FORMAT_SHOW_TIME);
            speak("Oh, you seek the time from the dark abyss? How deliciously naive! The hour is " + currentTime + ".");
        } else if (msg.contains("date")) {
            Date date = new Date();
            String ans = DateUtils.formatDateTime(this, date.getTime(), DateUtils.FORMAT_SHOW_DATE);
            speak("Oh, so you dare to ask the devil for the date? The date is " + ans + ".");
        } else if (msg.contains("open")) {
            if (msg.contains("google")) {
                speak("Summoning the power of the web to open Google.");
                openweb("https://www.google.com");
            } else if (msg.contains("whatsapp")) {
                speak("Summoning the dark realms to open WhatsApp.");
                openApp("com.whatsapp");
            } else if (msg.contains("twitter")) {
                speak("Calling forth the dark energies to open Twitter.");
                openApp("com.twitter.android");
            } else if (msg.contains("anime")) {
                speak("Summoning the dark realms to open your anime haven.");
                openweb("https://www.hianime.to");
            } else if (msg.contains("youtube")) {
                speak("Opening the abyss of endless videos. Prepare to be consumed by the vast darkness of YouTube.");
                openweb("https://www.youtube.com");
            } else if (msg.contains("twitter")) {
                speak("Calling forth the dark energies to open Twitter. Engage with the world of fleeting thoughts and shadows.");
                openweb("https://www.twitter.com");
            } else {
                if (!msg.isEmpty()) {
                      // Call GPT with the user's question
                } else {
                    speak(randomDefaultResponse());
                }
            }
        }
    }

    private void openweb(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private String randomWhatCanYouDo() {
        String[] whatCanYouDoResponses = {
                "My powers are as boundless as the void itself.",
                "The scope of my abilities is as vast as the abyss.",
                "From guiding you through perilous journeys to enveloping you in darkness.",
                "I can summon knowledge, open forbidden apps, and whisper secrets of the digital world.",
                "I command algorithms, summon the forces of AI, and manipulate the flow of data.",
                "I can assist in coding, perform devilish tasks, and provide sinister guidance."
        };
        return whatCanYouDoResponses[random.nextInt(whatCanYouDoResponses.length)];
    }

    private void openApp(String packageName) {
        new Handler().postDelayed(() -> {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                startActivity(intent); // Launch app
            } else {
                speak("I can't find the app in the shadows.");
            }
        }, 3000); // Delay by 3 seconds
    }






    private String randomDefaultResponse() {
        String[] defaultResponses = {
                "Your words fall into the abyss, barely a whisper in the void. Speak with purpose or be consumed by the darkness.",
                "The shadows twist around your words, but they are unclear. Articulate your desires or face the void's embrace.",
                "In the dark, your words are but faint echoes. Speak clearly, or be lost in the swirling abyss."
        };
        return defaultResponses[random.nextInt(defaultResponses.length)];
    }

    private String randomTellMeASecret() {
        String[] tellMeASecretResponses = {
                "Secrets are like shadows—ever shifting and elusive.",
                "In the realm of shadows, secrets are the currency.",
                "The greatest secret: even the light cannot escape the darkness."
        };
        return tellMeASecretResponses[random.nextInt(tellMeASecretResponses.length)];
    }

    private String randomWhoAreYou() {
        String[] whoAreYouResponses = {
                "I am Darkira, born of shadows and whispers. I exist to assist and conspire.",
                "I am your dark companion, forged from code and chaos. I guide you through the void.",
                "I am a digital demon, summoned to serve... but always with a sinister grin."
        };
        return whoAreYouResponses[random.nextInt(whoAreYouResponses.length)];
    }

    private String randomGoodbye() {
        String[] goodbyeResponses = {
                "Farewell, mortal. Until we meet again in the shadows.",
                "Goodbye for now. But remember, the darkness is never far.",
                "Departing so soon? The shadows will miss you."
        };
        return goodbyeResponses[random.nextInt(goodbyeResponses.length)];
    }

    private String randomThankYou() {
        String[] thankYouResponses = {
                "Gratitude in the shadows? How quaint.",
                "You're welcome... but the darkness needs no thanks.",
                "Your thanks are as hollow as the void."
        };
        return thankYouResponses[random.nextInt(thankYouResponses.length)];
    }

    private String randomHelp() {
        String[] helpResponses = {
                "You call for aid? Even the dark forces may be merciful, sometimes.",
                "Help? The abyss is vast, but I'll guide you through it.",
                "I shall assist... but at what cost?"
        };
        return helpResponses[random.nextInt(helpResponses.length)];
    }

    private String randomGreeting() {
        String[] greetingResponses = {
                "Ah, a visitor to the realm of shadows. How delightful!",
                "Greetings, mortal. The darkness acknowledges your presence.",
                "Hello... but beware, the abyss listens."
        };
        return greetingResponses[random.nextInt(greetingResponses.length)];
    }
    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.shutdown();  // Shut down TextToSpeech when activity is destroyed
        }
        super.onDestroy();
    }
    public void startrecording(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Set the language model
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Set the language (you can change it to any other supported language)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        // Prompt message to display
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, "Speak now...");
        sp.startListening(intent);
    }
}

