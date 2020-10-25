package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.regex.Pattern;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if any of the slabfish names are met.</p>
 *
 * @author Ocelot
 */
public class SlabfishRenameCondition implements SlabfishCondition {
    private final Pattern pattern;

    private SlabfishRenameCondition(Pattern pattern) {
        this.pattern = pattern;
    }

    private SlabfishRenameCondition(String[] names, boolean caseSensitive) {
        StringBuilder regexBuilder = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            regexBuilder.append("\\b").append(names[i]);
            if (i < names.length - 1)
                regexBuilder.append("|");
        }

        this.pattern = Pattern.compile(regexBuilder.toString(), (!caseSensitive ? Pattern.CASE_INSENSITIVE : 0) | Pattern.UNICODE_CASE);
    }

    /**
     * Creates a new {@link SlabfishRenameCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
        if ((json.has("names") || json.has("caseSensitive")) && json.has("regex"))
            throw new JsonSyntaxException("Either 'regex' or 'names' and 'caseSensitive' can be present.");
        if (!json.has("names") && !json.has("caseSensitive") && !json.has("regex"))
            throw new JsonSyntaxException("Either 'regex' or 'names' and 'caseSensitive' must be present.");
        return json.has("regex") ? new SlabfishRenameCondition(Pattern.compile(json.get("regex").getAsString())) : new SlabfishRenameCondition(context.deserialize(json.get("names"), String[].class), json.has("caseSensitive") && json.get("caseSensitive").getAsBoolean());
    }

    @Override
    public SlabfishConditionType getType() {
        return SlabfishConditionType.RENAME;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return context.getAction() == SlabfishConditionContext.Event.RENAME && this.pattern.matcher(context.getName()).matches();
    }
}
