/*
 * Copyright 2004-2015 Joe Walnes, Guillaume Chauvet.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bimedia.squiggle.literal;

import com.bimedia.squiggle.Literal;
import com.bimedia.squiggle.output.Output;

public abstract class LiteralWithSameRepresentationInJavaAndSql extends Literal {

    private final Object literalValue;

    protected LiteralWithSameRepresentationInJavaAndSql(Object literalValue) {
        this.literalValue = literalValue;
    }

    public void write(Output out) {
        out.print(literalValue);
    }
}
