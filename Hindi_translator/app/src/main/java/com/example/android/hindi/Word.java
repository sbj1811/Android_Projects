package com.example.android.hindi;

/**
 * Created by sjani on 2/11/2018.
 */

public class Word {

    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Hindi translation for the word */
    private String mHindiTranslation;

    private int mImageResourceId;
    //private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Create a new Word object. Constructor 1
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param hindiTranslation is the word in the Hindi language
     */
    public Word(String defaultTranslation, String hindiTranslation) {
        mDefaultTranslation = defaultTranslation;
        mHindiTranslation = hindiTranslation;
    }

    /**
     * Create a new Word object. Constructor 2
     * @param defaultTranslation
     * @param hindiTranslation
     * @param imageResourceId
     */

    public Word(String defaultTranslation, String hindiTranslation, int imageResourceId) {
        mDefaultTranslation = defaultTranslation;
        mHindiTranslation = hindiTranslation;
        mImageResourceId = imageResourceId;
    }


    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Hindi translation of the word.
     */
    public String getHindiTranslation() {
        return mHindiTranslation;
    }

    /**
     * Get the Image for the word
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Checks if there is an image for this word
     * @return
     */
    public boolean hasImage (){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
