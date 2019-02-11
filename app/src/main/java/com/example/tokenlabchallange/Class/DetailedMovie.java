package com.example.tokenlabchallange.Class;


public class DetailedMovie extends Movie {
    public boolean adult;
    public String backdrop_url;
    public Collection belongs_to_collection;
    public int budget;
    public String homepage;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public float popularity;
    public Company[] production_companies;
    public Production[] production_countries;
    public int revenue;
    public int runtime;
    public Language[] spoken_languages;
    public String status;
    public String tagline;
    public boolean video;
    public int vote_count;

    public class Collection{
        public int id;
        public String name;
        public String poster_url;
        public String backdrop_url;
    }

    public class Company{
        public int id;
        public String logo_url;
        public String name;
        public String origin_country;
    }

    public class Production{
        public String iso_3166_1;
        public String name;
    }

    public class Language{
        public String iso_639_1;
        public String name;
    }

}
