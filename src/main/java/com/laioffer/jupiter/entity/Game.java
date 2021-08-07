package com.laioffer.jupiter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

// these annotations are to handle some edge cases
// @JsonIgnoreProperties(ignoreUnknown = true) indicates that other fields in the response can be safely ignored.
@JsonIgnoreProperties(ignoreUnknown = true)
// @JsonInclude(JsonInclude.Include.NON_NULL) indicates that null fields can be skipped and not included.
@JsonInclude(JsonInclude.Include.NON_NULL)
// @JsonDeserialize indicates that Jackson needs to use Game.Builder when constructing a Game object from JSON strings.
@JsonDeserialize(builder = Game.Builder.class)
// GAME OBJECT --> JSON OBJECT
public class Game {
    // @JsonProperty("") indicates the mapping, the exact match is not required,
    // but it’s required for multi-word snake case and camel case conversions, like release_time to releaseTime.

    // these fields need to match the Twitch response
    // https://dev.twitch.tv/docs/api/reference#get-games
    @JsonProperty("id")
    private final String id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("box_art_url")
    private final String boxArtUrl;

    private Game(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.boxArtUrl = builder.boxArtUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBoxArtUrl() {
        return boxArtUrl;
    }

    // these annotations are to handle some edge cases
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    // JSON OBJECT --> GAME OBJECT
    public static class Builder {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("box_art_url")
        private String boxArtUrl;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder boxArtUrl(String boxArtUrl) {
            this.boxArtUrl = boxArtUrl;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
