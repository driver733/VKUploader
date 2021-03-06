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
package com.driver733.vkuploader.wallpost.attachment.support.strings;

import com.driver733.vkuploader.wallpost.attachment.support.QueryResultsBasic;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jcabi.aspects.Immutable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.cactoos.list.StickyList;

/**
 * Maps queries queriesResults to Attachment attachmentStrings.
 * <p>
 * Additional info
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class AttachmentsFromResults implements AttachmentStrings {

    /**
     * Group ID.
     */
    private final int group;

    /**
     * JsonArray that contains the
     *  {@link QueryResultsBasic}
     *  of the queries.
     */
    private final JsonArray root;

    /**
    * Ctor.
    * @param root JsonArray that contains the
    *  {@link QueryResultsBasic}
    *  of the queries.
    * @param group Group ID.
    */
    public AttachmentsFromResults(final JsonArray root, final int group) {
        this.root = root;
        this.group = group;
    }

    @Override
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public List<String> attachmentStrings() throws IOException {
        final List<String> list = new ArrayList<>(this.root.size());
        for (final JsonElement element : this.root) {
            list.addAll(
                new AttachmentStringsFromJson(element, this.group)
                    .attachmentStrings()
            );
        }
        return new StickyList<>(
            list
        );
    }

}
