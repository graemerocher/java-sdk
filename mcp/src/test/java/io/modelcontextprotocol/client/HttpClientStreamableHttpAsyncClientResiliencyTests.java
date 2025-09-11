/*
 * Copyright 2024-2024 the original author or authors.
 */

package io.modelcontextprotocol.client;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpClientTransport;
import reactor.test.StepVerifier;

import static io.modelcontextprotocol.util.McpJsonMapperUtils.JSON_MAPPER;

@Timeout(15)
public class HttpClientStreamableHttpAsyncClientResiliencyTests extends AbstractMcpAsyncClientResiliencyTests {

	@Override
	protected McpClientTransport createMcpTransport() {
		return HttpClientStreamableHttpTransport.builder(host).jsonMapper(JSON_MAPPER).build();
	}

	@Test
	void testPingWithExactExceptionType() {
		withClient(createMcpTransport(), mcpAsyncClient -> {
			StepVerifier.create(mcpAsyncClient.initialize()).expectNextCount(1).verifyComplete();

			disconnect();

			StepVerifier.create(mcpAsyncClient.ping()).expectError(IOException.class).verify();

			reconnect();

			StepVerifier.create(mcpAsyncClient.ping()).expectNextCount(1).verifyComplete();
		});
	}

}
