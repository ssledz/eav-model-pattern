/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
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
package pl.softech.eav.infrastructure.jpa;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class TableNamingStrategy extends DefaultNamingStrategy {

	private static final long serialVersionUID = 1L;

	private final static String TABLE_PREFIX = "eav";

	private final static String SEPARATOR = "_";

	@Override
	public String tableName(String tableName) {

		StringBuilder sb = new StringBuilder();
		sb.append(TABLE_PREFIX);
		sb.append(SEPARATOR);
		sb.append(tableName);

		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(TableNamingStrategy.class.getName());
	}

}
