/*
 * Copyright 2015 the original author or authors.
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

package org.springframework.cloud.stream.module.transform;

import org.slf4j.Logger;				// ccb
import org.slf4j.LoggerFactory;				// ccb
import com.fasterxml.jackson.databind.ObjectMapper;	// ccb
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.config.SpelExpressionConverterConfiguration;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import java.util.Arrays;

/**
 * A Processor module that transforms messages using a SpEL expression.
 *
 * @author Eric Bottard
 * @author Marius Bogoevici
 */
@EnableBinding(Processor.class)
@Import(SpelExpressionConverterConfiguration.class)
@EnableConfigurationProperties(TransformProcessorProperties.class)
public class TransformProcessor {

	private static Logger logger = LoggerFactory.getLogger(TransformProcessor.class);

	@Autowired
	private TransformProcessorProperties properties;

	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public Object transform(Message<?> message) throws com.fasterxml.jackson.core.JsonProcessingException {
		logger.info("Transforming the data...maybe :) : " + message);
		logger.info("Expression output: " + properties.getExpression().getValue(message));

		// transform in JSON
		ObjectMapper om = new ObjectMapper();
		om.writeValueAsString(message);

				
		return properties.getExpression().getValue(message);
	}

}
