package com.laioffer.jupiter.external;

public enum TwitchCredential {
    YOUR_CLIENT_ID("7bozusbbgy8tlw59xe6oifq5bidu51"),
    YOUR_TOKEN("Bearer ay6muxkvnfbi96cj5jrzwuvgp6791a");

    private String info;

    private TwitchCredential(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
