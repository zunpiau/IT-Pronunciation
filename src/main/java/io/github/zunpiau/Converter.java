package io.github.zunpiau;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("FieldCanBeLocal")
public class Converter {


    private final String sourceFile = "words.yaml";
    private final String destFile = "words.md";

    public static void main(String[] args) throws IOException {
        new Converter ().perform ();
    }

    public void perform() throws IOException {
        Translator translator = new Translator ();

        try (BufferedWriter writer = new BufferedWriter (new FileWriter (destFile))) {
            YAMLParser parser = new YAMLFactory ()
                    .createParser (ClassLoader.getSystemResource (sourceFile));
            JsonToken token;
            while ((token = parser.nextToken ()) != null) {
                if (token.id () == JsonToken.FIELD_NAME.id ()) {
                    writer.write ("- " + parser.getText ());
                    writer.newLine ();
                    writer.newLine ();
                    writer.write ("| 单词 | 发音 |");
                    writer.newLine ();
                    writer.write ("| -- | -- |");
                    writer.newLine ();
                } else if (token.id () == JsonToken.VALUE_STRING.id ()) {
                    writer.write (translator.query (parser.getText ()).toString ());
                    writer.newLine ();
                } else if (token.id () == JsonToken.END_ARRAY.id ()) {
                    writer.newLine ();
                }
            }
            writer.flush ();
        }

    }

}
