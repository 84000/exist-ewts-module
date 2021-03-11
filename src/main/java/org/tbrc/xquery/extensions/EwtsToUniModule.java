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

import org.exist.dom.QName;
import org.exist.xquery.AbstractInternalModule;
import org.exist.xquery.ErrorCodes;
import org.exist.xquery.FunctionDSL;
import org.exist.xquery.FunctionDef;
import org.exist.xquery.FunctionSignature;
import org.exist.xquery.value.FunctionParameterSequenceType;
import org.exist.xquery.value.FunctionReturnSequenceType;

import java.util.List;
import java.util.Map;

import static org.exist.xquery.FunctionDSL.functionDefs;

/**
 * A simple wrapper around the <a href="https://github.com/buda-base/ewts-converter">Ewts Converter</a>.
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public class EwtsToUniModule extends AbstractInternalModule {

    public static final String NAMESPACE_URI = "http://tbrc.org/xquery/ewts2unicode";
    public static final String PREFIX = "ewts";
    public static final String RELEASED_IN_VERSION = "eXist-5.2.0";

    // register the functions of the module
    public static final FunctionDef[] functions = functionDefs(
        functionDefs(EwtsFunctions.class,
                EwtsFunctions.FS_TO_UNICODE,
                EwtsFunctions.FS_TO_WYLIE
        )
    );

    public EwtsToUniModule(final Map<String, List<? extends Object>> parameters) {
        super(functions, parameters);
    }

    @Override
    public String getNamespaceURI() {
        return NAMESPACE_URI;
    }

    @Override
    public String getDefaultPrefix() {
        return PREFIX;
    }

    @Override
    public String getDescription() {
        return "Ewts Converter Module for eXist-db. See https://github.com/buda-base/ewts-converter" ;
    }

    @Override
    public String getReleaseVersion() {
        return RELEASED_IN_VERSION;
    }

    static FunctionSignature functionSignature(final String name, final String description,
                                               final FunctionReturnSequenceType returnType, final FunctionParameterSequenceType... paramTypes) {
        return FunctionDSL.functionSignature(new QName(name, NAMESPACE_URI), description, returnType, paramTypes);
    }

    static FunctionSignature[] functionSignatures(final String name, final String description,
            final FunctionReturnSequenceType returnType, final FunctionParameterSequenceType[][] variableParamTypes) {
        return FunctionDSL.functionSignatures(new QName(name, NAMESPACE_URI), description, returnType, variableParamTypes);
    }

    static class EwtsToUniModuleErrorCode extends ErrorCodes.ErrorCode {
        private EwtsToUniModuleErrorCode(final String code, final String description) {
            super(new QName(code, NAMESPACE_URI, PREFIX), description);
        }
    }
}
