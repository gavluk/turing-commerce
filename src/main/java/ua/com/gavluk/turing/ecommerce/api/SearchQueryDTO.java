package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SearchQueryDTO {

    public static final String ALL_WORDS_IS_ON = "on";

    @NotBlank
    @JsonProperty("query_string")
    private String query_string;

    @Pattern(regexp = "^(on|off)$")
    @JsonProperty("all_words")
    private String all_words = ALL_WORDS_IS_ON;

    public SearchQueryDTO() {
    }

    public boolean isAllWords() {
        return ALL_WORDS_IS_ON.equals(all_words);
    }

    public String getQuery_string() {
        return query_string;
    }

    public void setQuery_string(String query_string) {
        this.query_string = query_string;
    }

    public String getAll_words() {
        return all_words;
    }

    public void setAll_words(String all_words) {
        this.all_words = all_words;
    }
}
