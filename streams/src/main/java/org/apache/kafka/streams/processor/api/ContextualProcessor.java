/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.streams.processor.api;

/**
 * An abstract implementation of {@link Processor} that manages the {@link ProcessorContext} instance.
 *
 * @param <KIn> the type of input keys
 * @param <VIn> the type of input values
 * @param <KOut> the type of output keys
 * @param <VOut> the type of output values
 */
public abstract class ContextualProcessor<KIn, VIn, KOut, VOut> implements Processor<KIn, VIn, KOut, VOut> {

    private ProcessorContext<KOut, VOut> context;

    protected ContextualProcessor() {}

    @Override
    public void init(final ProcessorContext<KOut, VOut> context) {
        this.context = context;
    }

    /**
     * Get the processor's context set during {@link #init(ProcessorContext) initialization}.
     *
     * @return the processor context; null only when called prior to {@link #init(ProcessorContext) initialization}.
     */
    protected final ProcessorContext<KOut, VOut> context() {
        return context;
    }
}
