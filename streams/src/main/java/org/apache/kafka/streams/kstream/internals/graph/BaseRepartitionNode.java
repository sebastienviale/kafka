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

package org.apache.kafka.streams.kstream.internals.graph;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.processor.StreamPartitioner;
import org.apache.kafka.streams.processor.internals.InternalTopicProperties;

public abstract class BaseRepartitionNode<K, V> extends GraphNode {

    protected final Serde<K> keySerde;
    protected final Serde<V> valueSerde;
    protected final String sinkName;
    protected final String sourceName;
    protected final String repartitionTopic;
    protected final StreamPartitioner<K, V> partitioner;
    protected final InternalTopicProperties internalTopicProperties;
    protected ProcessorParameters<K, V, K, V> processorParameters;

    BaseRepartitionNode(final String nodeName,
                        final String sourceName,
                        final ProcessorParameters<K, V, K, V> processorParameters,
                        final Serde<K> keySerde,
                        final Serde<V> valueSerde,
                        final String sinkName,
                        final String repartitionTopic,
                        final StreamPartitioner<K, V> partitioner,
                        final InternalTopicProperties internalTopicProperties) {
        super(nodeName);

        this.keySerde = keySerde;
        this.valueSerde = valueSerde;
        this.sinkName = sinkName;
        this.sourceName = sourceName;
        this.repartitionTopic = repartitionTopic;
        this.processorParameters = processorParameters;
        this.partitioner = partitioner;
        this.internalTopicProperties = internalTopicProperties;
    }

    Serializer<V> valueSerializer() {
        return valueSerde != null ? valueSerde.serializer() : null;
    }

    Deserializer<V> valueDeserializer() {
        return valueSerde != null ? valueSerde.deserializer() : null;
    }

    Serializer<K> keySerializer() {
        return keySerde != null ? keySerde.serializer() : null;
    }

    Deserializer<K> keyDeserializer() {
        return keySerde != null ? keySerde.deserializer() : null;
    }

    public void setProcessorParameters(final ProcessorParameters<K, V, K, V> processorParameters) {
        this.processorParameters = processorParameters;
    }

    public ProcessorParameters<K, V, K, V> processorParameters() {
        return processorParameters;
    }

    @Override
    public String toString() {
        return "BaseRepartitionNode{" +
               "keySerde=" + keySerde +
               ", valueSerde=" + valueSerde +
               ", sinkName='" + sinkName + '\'' +
               ", sourceName='" + sourceName + '\'' +
               ", repartitionTopic='" + repartitionTopic + '\'' +
               ", processorParameters=" + processorParameters + '\'' +
               ", partitioner=" + partitioner +
               ", internalTopicProperties=" + internalTopicProperties +
               "} " + super.toString();
    }

    public abstract static class BaseRepartitionNodeBuilder<K, V, RepartitionNode extends BaseRepartitionNode<K, V>> {
        protected String nodeName;
        protected ProcessorParameters<K, V, K, V> processorParameters;
        protected Serde<K> keySerde;
        protected Serde<V> valueSerde;
        protected String sinkName;
        protected String sourceName;
        protected String repartitionTopic;
        protected StreamPartitioner<K, V> partitioner;
        protected InternalTopicProperties internalTopicProperties = InternalTopicProperties.empty();

        public BaseRepartitionNodeBuilder<K, V, RepartitionNode> withProcessorParameters(final ProcessorParameters<K, V, K, V> processorParameters) {
            this.processorParameters = processorParameters;
            return this;
        }

        public BaseRepartitionNodeBuilder<K, V, RepartitionNode> withKeySerde(final Serde<K> keySerde) {
            this.keySerde = keySerde;
            return this;
        }

        public BaseRepartitionNodeBuilder<K, V, RepartitionNode> withValueSerde(final Serde<V> valueSerde) {
            this.valueSerde = valueSerde;
            return this;
        }

        public BaseRepartitionNodeBuilder<K, V, RepartitionNode> withSinkName(final String sinkName) {
            this.sinkName = sinkName;
            return this;
        }

        public BaseRepartitionNodeBuilder<K, V, RepartitionNode> withSourceName(final String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public BaseRepartitionNodeBuilder<K, V, RepartitionNode> withRepartitionTopic(final String repartitionTopic) {
            this.repartitionTopic = repartitionTopic;
            return this;
        }

        public BaseRepartitionNodeBuilder<K, V, RepartitionNode> withStreamPartitioner(final StreamPartitioner<K, V> partitioner) {
            this.partitioner = partitioner;
            return this;
        }

        public BaseRepartitionNodeBuilder<K, V, RepartitionNode> withNodeName(final String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public BaseRepartitionNodeBuilder<K, V, RepartitionNode> withInternalTopicProperties(final InternalTopicProperties internalTopicProperties) {
            this.internalTopicProperties = internalTopicProperties;
            return this;
        }

        public abstract RepartitionNode build();
    }
}
