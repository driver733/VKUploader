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
package com.driver733.vkuploader.wallpost;

import com.jcabi.aspects.Immutable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import org.cactoos.Scalar;
import org.cactoos.scalar.Constant;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Immutable {@link Properties}.
 *
 * @since 0.1
 */
@Immutable
public final class PropsFile implements Props {

    /**
     * Properties.
     */
    private final UncheckedScalar<Properties> origin;

    /**
     * Output stream.
     */
    private final UncheckedScalar<OutputStream> output;

    /**
     * Ctor.
     * @param file Properties.
     */
    public PropsFile(
        final File file
    ) {
        this(
            new Constant<>(
                () -> file
            )
        );
    }

    /**
     * Ctor.
     * @param path Path to the properties.
     */
    public PropsFile(
        final Path path
    ) {
        this(
            new Constant<>(
                path::toFile
            )
        );
    }

    /**
     * Ctor.
     * @param scalar Scalar with {@link File}.
     */
    private PropsFile(
        final Scalar<Scalar<File>> scalar
    ) {
        this.origin = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final File file = scalar.value().value();
                    final Properties props = new Properties();
                    if (
                        file.exists()
                        ) {
                        try (
                            InputStream fis = Files.newInputStream(
                                file.toPath()
                            )
                        ) {
                            props.load(
                                fis
                            );
                        }
                    } else {
                        try (
                            OutputStream fos = Files.newOutputStream(
                                file.toPath()
                            )
                        ) {
                            props.store(
                                fos,
                                null
                            );
                        }
                    }
                    return props;
                }
            )
        );
        this.output = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> Files.newOutputStream(
                    scalar.value().value().toPath()
                )
            )
        );
    }

    @Override
    public boolean containsKey(final String key) {
        return this.origin
            .value()
            .containsKey(key);
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return this.origin.value()
            .entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    entry -> entry.getKey().toString(),
                    entry -> entry.getValue().toString()
                )
        ).entrySet();
    }

    @Override
    public String property(
        final String key
    ) {
        return this.origin
            .value()
            .getProperty(
                key
            );
    }

    @Override
    public PropsFile with(
        final String key,
        final String value
    ) throws IOException {
        final Properties props = this.origin.value();
        props.setProperty(
            key,
            value
        );
        props.store(
            this.output.value(),
            null
        );
        return this;
    }

}
