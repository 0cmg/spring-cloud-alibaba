/*
 * Copyright (C) 2018 the original author or authors.
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

package com.alibaba.cloud.sentinel.datasource.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.util.ObjectUtils;

/**
 * Using By ConfigurationProperties.
 *
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 * @see NacosDataSourceProperties
 * @see ApolloDataSourceProperties
 * @see ZookeeperDataSourceProperties
 * @see FileDataSourceProperties
 */
public class DataSourcePropertiesConfiguration {

	@Valid
	private FileDataSourceProperties file;

	@Valid
	private NacosDataSourceProperties nacos;

	@Valid
	private ZookeeperDataSourceProperties zk;

	@Valid
	private ApolloDataSourceProperties apollo;

	public DataSourcePropertiesConfiguration() {
	}

	public DataSourcePropertiesConfiguration(FileDataSourceProperties file) {
		this.file = file;
	}

	public DataSourcePropertiesConfiguration(NacosDataSourceProperties nacos) {
		this.nacos = nacos;
	}

	public DataSourcePropertiesConfiguration(ZookeeperDataSourceProperties zk) {
		this.zk = zk;
	}

	public DataSourcePropertiesConfiguration(ApolloDataSourceProperties apollo) {
		this.apollo = apollo;
	}

	public FileDataSourceProperties getFile() {
		return file;
	}

	public void setFile(FileDataSourceProperties file) {
		this.file = file;
	}

	public NacosDataSourceProperties getNacos() {
		return nacos;
	}

	public void setNacos(NacosDataSourceProperties nacos) {
		this.nacos = nacos;
	}

	public ZookeeperDataSourceProperties getZk() {
		return zk;
	}

	public void setZk(ZookeeperDataSourceProperties zk) {
		this.zk = zk;
	}

	public ApolloDataSourceProperties getApollo() {
		return apollo;
	}

	public void setApollo(ApolloDataSourceProperties apollo) {
		this.apollo = apollo;
	}

	@JsonIgnore
	public List<String> getValidField() {
		List<String> fieldList = new ArrayList<>();
		for (Field field : this.getClass().getDeclaredFields()) {
			try {
				if (!ObjectUtils.isEmpty(field.get(this))) {
					fieldList.add(field.getName());
				}
			}
			catch (IllegalAccessException e) {
				// won't happen
			}
		}
		return fieldList;
	}

	@JsonIgnore
	public AbstractDataSourceProperties getValidDataSourceProperties() {
		List<String> invalidFields = getValidField();
		if (invalidFields.size() == 1) {
			try {
				this.getClass().getDeclaredField(invalidFields.get(0))
						.setAccessible(true);
				return (AbstractDataSourceProperties) this.getClass()
						.getDeclaredField(invalidFields.get(0)).get(this);
			}
			catch (IllegalAccessException e) {
				// won't happen
			}
			catch (NoSuchFieldException e) {
				// won't happen
			}
		}
		return null;
	}

}
