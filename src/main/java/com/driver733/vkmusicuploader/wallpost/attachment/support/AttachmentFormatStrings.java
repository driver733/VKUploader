/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Mikhail Yakushin
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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jcabi.aspects.Immutable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public final class AttachmentFormatStrings {

    /**
     * A {@link JsonElement} that contains attachment string(s).
     */
    private final JsonElement root;

    /**
     * Ctor.
     * @param root A {@link JsonElement} that contains attachment string(s).
     */
    public AttachmentFormatStrings(final JsonElement root) {
        this.root = root;
    }

    /**
     * Extracts attachment string from the provided Json Elements.
     * @return A {@link List} with attachment string(s).
     * @throws IOException If attachment string cannot be extracted.
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public List<String> attachmentStrings() throws IOException {
        final List<String> list;
        try {
            if (this.root.isJsonArray()) {
                list = new ArrayList<>(this.root.getAsJsonArray().size());
                final JsonArray array = this.root.getAsJsonArray();
                for (final JsonElement element : array) {
                    list.add(
                        new AttachmentFormatFromJsonObject(
                            element.getAsJsonObject()
                        ).attachmentFormat()
                    );
                }
            } else {
                list = new ArrayList<>(1);
                list.add(
                    new AttachmentFormatFromJsonObject(
                        this.root
                    ).attachmentFormat()
                );
            }
        } catch (final IOException ex) {
            throw new IOException("Could not extract attachment string.", ex);
        }
        return list;
    }

}
