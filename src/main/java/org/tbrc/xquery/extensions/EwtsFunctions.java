/*
 * Copyright © 2021 Evolved Binary (tech@evolvedbinary.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tbrc.xquery.extensions;

import io.bdrc.ewtsconverter.EwtsConverter;
import org.exist.xquery.BasicFunction;
import org.exist.xquery.FunctionSignature;
import org.exist.xquery.XPathException;
import org.exist.xquery.XQueryContext;
import org.exist.xquery.value.Sequence;
import org.exist.xquery.value.StringValue;
import org.exist.xquery.value.Type;


import java.util.Optional;

import static org.exist.xquery.FunctionDSL.optParam;
import static org.exist.xquery.FunctionDSL.returnsOpt;
import static org.tbrc.xquery.extensions.EwtsToUniModule.functionSignature;

/**
 * Ewts Converter function wrappers.
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public class EwtsFunctions extends BasicFunction {

    private static final String FS_TO_UNICODE_NAME = "toUnicode";
    static final FunctionSignature FS_TO_UNICODE = functionSignature(
            FS_TO_UNICODE_NAME,
            "This function converts Extended Wylie strings to Unicode.",
            returnsOpt(Type.STRING, "The resulting Unicode string."),
            optParam("wylie-string", Type.STRING, "An Extended Wylie String")
    );

    private static final String FS_TO_WYLIE_NAME = "toWylie";
    static final FunctionSignature FS_TO_WYLIE = functionSignature(
            FS_TO_WYLIE_NAME,
            "This function converts Tibetan Unicode strings to Extended Wylie. Other language planes are passed through unchanged.",
            returnsOpt(Type.STRING, "The resulting Extended Wylie string."),
            optParam("unicode-string", Type.STRING, "The string containing Unicode.")
    );

    public EwtsFunctions(final XQueryContext context, final FunctionSignature signature) {
        super(context, signature);
    }

    @Override
    public Sequence eval(final Sequence[] args, final Sequence contextSequence) throws XPathException {
        switch (getName().getLocalPart()) {

            case FS_TO_UNICODE_NAME:
                final Optional<String> wsu = args[0].isEmpty() ? Optional.empty() : Optional.of(args[0].itemAt(0).getStringValue());
                final Optional<String> unicodeString = toUnicode(wsu);
                return unicodeString.map(str -> (Sequence)new StringValue(str)).orElse(Sequence.EMPTY_SEQUENCE);

            case FS_TO_WYLIE_NAME:
                final Optional<String> usw = args[0].isEmpty() ? Optional.empty() : Optional.of(args[0].itemAt(0).getStringValue());
                final Optional<String> wylieString = toWylie(usw);
                return wylieString.map(str -> (Sequence)new StringValue(str)).orElse(Sequence.EMPTY_SEQUENCE);

            default:
                throw new XPathException(this, "No function: " + getName() + "#" + getSignature().getArgumentCount());
        }
    }

    /**
     * Converts a Wylie String to a Unicode String.
     *
     * @param wylieString the Wylie string.
     *
     * @return The unicode String
     */
    private Optional<String> toUnicode(final Optional<String> wylieString) {
        if (!wylieString.isPresent()) {
            return Optional.empty();
        }

        final String ws = wylieString.get();

        final EwtsConverter wl = new EwtsConverter();
        return Optional.of(wl.toUnicode(ws));
    }

    /**
     * Converts a Unicode String to a Wylie String.
     *
     * @param unicodeString the Unicode string.
     *
     * @return The Wylie string.
     */
    private Optional<String> toWylie(final Optional<String> unicodeString) {
        if (!unicodeString.isPresent()) {
            return Optional.empty();
        }

        final String us = unicodeString.get();

        final EwtsConverter wl = new EwtsConverter();
        return Optional.of(wl.toWylie(us));
    }
}
