package com.example.android.jokeprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JokeProvider {

        private static final List<String> jokes = new ArrayList<>();
        Random random = new Random();

        static {
            jokes.add("Q. What\'s the difference between ignorance and apathy? A. I don\'t know and I don\'t care.");
            jokes.add("Did you hear about the semi-colon that broke the law? He was given two consecutive sentences.");
            jokes.add("I own the world\'s worst thesaurus. Not only is it awful, it\'s awful");
            jokes.add("Don\'t you hate it when someone answers their own questions? I do.");
            jokes.add("So what if I don\'t know what \"Armageddon\" means? It\'s not the end of the world.");
            jokes.add("I have clean conscience. I haven\'t used it once till now.");
            jokes.add("Do I lose when the police officer says papers and I say scissors?.");
            jokes.add("My girlfriend and I often laugh about how competitive we are. But I laugh more.");
            jokes.add("A conference call is the best way for a dozen people to say \"bye\" 300 times.");
            jokes.add("If I got 50 cents for every failed math exam, I\'d have $6.30 now.");
        }

        public String getAJoke(){
            int index = random.nextInt(jokes.size());
            return jokes.get(index);
        }

}
