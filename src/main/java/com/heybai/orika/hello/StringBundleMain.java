package com.heybai.orika.hello;

import ma.glasnost.orika.*;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.*;

public class StringBundleMain {

    public static void main(String[] args) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(StringBundle.class, BundleDto.class)
                .byDefault()
                .customize(new CustomMapper<StringBundle, BundleDto>() {
                    @Override
                    public void mapAtoB(StringBundle stringBundle, BundleDto bundleDto, MappingContext context) {
                        for (StringValue sv : stringBundle.map.values()) {
                            bundleDto.langs.add(new LanguageDto(sv.language, sv.value));
                        }
                    }

                    @Override
                    public void mapBtoA(BundleDto bundleDto, StringBundle stringBundle, MappingContext context) {
                        for (LanguageDto ld : bundleDto.langs) {
                            stringBundle.map.put(ld.lang, new StringValue(ld.lang, ld.text));
                        }
                    }
                })
                .register();

        MapperFacade mapperFacade = mapperFactory.getMapperFacade();

        // Source
        StringBundle stringBundle = new StringBundle();
        stringBundle.map.put(Language.RU, new StringValue(Language.RU, "Беларусь"));
        stringBundle.map.put(Language.EN, new StringValue(Language.EN, "Belarus"));
        stringBundle.map.put(Language.BY, new StringValue(Language.BY, "Бяла"));
        System.out.println("source: " + stringBundle);

        // Result
        BundleDto dto = mapperFacade.map(stringBundle, BundleDto.class);
        System.out.println("res: " + dto);

        // Back
        StringBundle stringBundle2 = mapperFacade.map(dto, StringBundle.class);
        System.out.println("back: " + stringBundle2);
    }

    public static class StringBundle {
        public Map<Language, StringValue> map = new HashMap<Language, StringValue>();

        @Override
        public String toString() {
            return "StringBundle{" +
                    "map=" + map +
                    '}';
        }
    }

    public static class StringValue {
        public Language language;
        public String value;

        public StringValue() {
        }

        public StringValue(Language language, String value) {
            this.language = language;
            this.value = value;
        }

        @Override
        public String toString() {
            return "StringValue{" +
                    "language=" + language +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public static enum Language {
        RU, EN, BY;
    }

    public static class BundleDto {
        public Set<LanguageDto> langs = new HashSet<LanguageDto>();

        @Override
        public String toString() {
            return "BundleDto{" +
                    "langs=" + langs +
                    '}';
        }
    }

    public static class LanguageDto {
        public Language lang;
        public String text;

        public LanguageDto() {
        }

        public LanguageDto(Language lang, String text) {
            this.lang = lang;
            this.text = text;
        }

        @Override
        public String toString() {
            return "LanguageDto{" +
                    "lang=" + lang +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

}
