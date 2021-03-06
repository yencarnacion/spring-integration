/*
 * Copyright 2016-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.monitor;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.springframework.jmx.export.metadata.JmxAttributeSource;
import org.springframework.jmx.export.naming.MetadataNamingStrategy;

/**
 * The {@link MetadataNamingStrategy} naming extension to extract an {@link ObjectName}
 * from the
 * {@link org.springframework.integration.support.management.LifecycleMessageSourceMetrics}
 * or
 * {@link org.springframework.integration.support.management.LifecycleMessageHandlerMetrics}
 * managed bean's delegate.
 * <p>
 * Otherwise delegate to the {@link MetadataNamingStrategy#getObjectName} as is.
 *
 * @author Gary Russell
 * @author Artem Bilan
 *
 * @since 4.3
 */
public class IntegrationMetadataNamingStrategy extends MetadataNamingStrategy {

	public IntegrationMetadataNamingStrategy(JmxAttributeSource attributeSource) {
		super(attributeSource);
	}

	@Override
	public ObjectName getObjectName(Object managedBean, String beanKey) throws MalformedObjectNameException {
		return super.getObjectName(extractManagedBean(managedBean), beanKey);
	}

	@SuppressWarnings("deprecation")
	private Object extractManagedBean(Object managedBean) {
		if (managedBean instanceof org.springframework.integration.support.management.LifecycleMessageSourceMetrics) {
			return ((org.springframework.integration.support.management.LifecycleMessageSourceMetrics) managedBean)
					.getDelegate();
		}
		else if (managedBean instanceof org.springframework.integration.support.management.LifecycleMessageHandlerMetrics) {
			return ((org.springframework.integration.support.management.LifecycleMessageHandlerMetrics) managedBean)
					.getDelegate();
		}
		return managedBean;
	}

}
