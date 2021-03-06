/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Mikhail Yakushin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.driver733.vkuploader.wallpost.attachment.support;

import com.driver733.vkuploader.wallpost.attachment.support.strings.AttachmentStringsFromJson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jcabi.aspects.Immutable;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test for {@link AttachmentStringsFromJson}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle IndentationCheck (500 lines)
 */
@Immutable
public final class AttachmentStringsFromJsonTest {

    @Test
    public void testObject() throws IOException {
        Assertions.assertThat(
            new AttachmentStringsFromJson(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson(
                        String.join(
                            "",
                            "{",
                            "\"owner_id\"  : 1111111,",
                            "\"id\"        : 1000000,",
                            "\"artist\"    : \"Test Artist1\",",
                            "\"title\"     : \"Test Title1\",",
                            "\"url\"       : \"http://test1.com\"",
                            "}"
                        ),
                        JsonObject.class
                    )
                    .getAsJsonObject(),
                1
            ).attachmentStrings()
        ).containsOnly("audio1111111_1000000");
    }

    @Test
    public void testArray() throws IOException {
        Assertions.assertThat(
            new AttachmentStringsFromJson(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson(
                        String.join(
                            "",
                            "[",
                            "{",
                            "\"owner_id\"  : 111111,",
                            "\"id\"        : 3000000,",
                            "\"artist\"    : \"Test Artist2\",",
                            "\"title\"     : \"Test Title2\",",
                            "\"url\"       : \"http://test2.com\" ",
                            "},",
                            "{",
                            "\"owner_id\"  : 2222222,",
                            "\"id\"        : 2000000,",
                            "\"artist\"    : \"Test Artist3\",",
                            "\"title\"     : \"Test Title3\",",
                            "\"url\"       : \"http://test3.com\" ",
                            "}",
                            "]"
                        ),
                        JsonArray.class
                    )
                    .getAsJsonArray(),
                1
            ).attachmentStrings()
        ).containsOnly(
            "audio111111_3000000",
            "audio2222222_2000000"
        );
    }

    @Test(expected = IOException.class)
    public void testException() throws IOException {
        Assertions.assertThat(
            new AttachmentStringsFromJson(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson(
                        String.join(
                            "",
                            "[",
                            "{",
                            "\"ppowner_id\" : 111111,",
                            "\"iid\"        : 30000001,",
                            "\"martist\"    : \"Test Artist2\",",
                            "\"dtitle\"     : \"Test Title2\",",
                            "\"furl\"       : \"http://test2.com\"",
                            "},",
                            "{",
                            "\"downer_id\"  : 2222222,",
                            "\"fid\"        : 20000001,",
                            "\"dartist\"    : \"Test Artist3\",",
                            "\"ftitle\"     : \"Test Title3\",",
                            "\"durl\"       : \"http://test3.com\"",
                            "}",
                            "]"
                        ),
                        JsonArray.class
                    ).getAsJsonArray(),
                1
            ).attachmentStrings()
        ).containsOnly(
            "audio111111_30000001",
            "audio2222222_20000001"
        );
    }

}
