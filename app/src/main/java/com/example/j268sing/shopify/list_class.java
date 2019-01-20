package com.example.j268sing.shopify;


public class list_class {

    // variable for title of the product.
    private String mTitle;

    // variable for id of the product.
    private String mId;

    // variable to give image link of the book;
    private String mImage;

    // variable for name of the pdf
    private String mpdfname;


    /*
     * constructor for list_class object
     *
     * @param Name is the name of the book
     * @param Author is the name of the author
     * @param imageName is drawable reference ID that corresponds to the Android version
     * */

    public list_class(String title, String id, String image,  String pdfName){
        mTitle = title;
        mId = id;
        mImage = image;
        mpdfname = pdfName;
    }

    /**
     * Get the title of the product
     */

    public String getmTitle() {
        return mTitle;
    }

    /**
     * Get the name of the id of the product
     */

    public String getmId() {
        return mId;
    }



    /**
     * Get the imagelink fo downloading the image
     */


    public String getmImage(){ return mImage;}

    /**
     * Get the pdfname fo downloading the image
     */


    public String getMpdfname(){ return mpdfname;}

}





