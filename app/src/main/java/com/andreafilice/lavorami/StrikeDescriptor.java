package com.andreafilice.lavorami;

import com.google.gson.annotations.SerializedName;

public class StrikeDescriptor {
    /// In this class, we get from the CDN the values of the strike and parse that datas into this Class.
    /// @SerializedName are the original names from the JSON file of our CDN
    /// @ATTRIBUTES
    /// @String isStrikeEnabled -> Returns a String but the values are: true <i>or</i> false.
    /// @String strikeDate -> Is the date of the Strike, catched from the JSON File.
    /// @String strikeCompanies -> Are the companies that make this Strike and their services can suffer problems.
    /// @String strikeGuaranteed -> Returns a String but the values are: sono garantite <i>or</i> non sono garantite.

    @SerializedName("enableStrike")
    private String isStrikeEnabled;
    @SerializedName("date")
    private String strikeDate;
    @SerializedName("companies")
    private String strikeCompanies;
    @SerializedName("guaranteed")
    private String strikeGuaranteed;

    public StrikeDescriptor(String isStrikeEnabled, String strikeDate, String strikeCompanies, String strikeGuaranteed) {
        this.isStrikeEnabled = isStrikeEnabled;
        this.strikeDate = strikeDate;
        this.strikeCompanies = strikeCompanies;
        this.strikeGuaranteed = strikeGuaranteed;
    }

    //*GETTERS
    /// Set-up the Getters to use into MainActivity.java for get some values.
    public String isStrikeEnabled() {return isStrikeEnabled;}
    public String getStrikeDate() {return strikeDate;}
    public String getStrikeCompanies() {return strikeCompanies;}
    public String getStrikeGuaranteed() {return strikeGuaranteed;}
}
