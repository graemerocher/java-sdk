/*
 * Copyright 2024-2024 the original author or authors.
 */

package io.modelcontextprotocol.server;

import org.junit.jupiter.api.Timeout;

import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import io.modelcontextprotocol.spec.McpStreamableServerTransportProvider;

import static io.modelcontextprotocol.util.McpJsonMapperUtils.JSON_MAPPER;

/**
 * Tests for {@link McpSyncServer} using
 * {@link HttpServletStreamableServerTransportProvider}.
 *
 * @author Christian Tzolov
 */
@Timeout(15)
class HttpServletStreamableSyncServerTests extends AbstractMcpSyncServerTests {

	protected McpStreamableServerTransportProvider createMcpTransportProvider() {
		return HttpServletStreamableServerTransportProvider.builder()
			.jsonMapper(JSON_MAPPER)
			.mcpEndpoint("/mcp/message")
			.build();
	}

	@Override
	protected McpServer.SyncSpecification<?> prepareSyncServerBuilder() {
		return McpServer.sync(createMcpTransportProvider());
	}

}
