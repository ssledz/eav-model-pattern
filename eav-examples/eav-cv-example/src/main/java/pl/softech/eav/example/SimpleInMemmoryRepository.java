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
package pl.softech.eav.example;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;

import pl.softech.eav.domain.AbstractEntity;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
public class SimpleInMemmoryRepository<E extends AbstractEntity> implements CrudRepository<E, Long> {

	private static final SequenceGenerator sequenceGenerator = new SequenceGenerator();

	protected final Map<Long, E> key2entity = new HashMap<Long, E>();

	protected E findOne(Predicate<E> predicate) {
		return Iterables.getOnlyElement(Iterables.filter(key2entity.values(), predicate), null);
	}
	
	@Override
	public <S extends E> S save(S entity) {
		Long id = sequenceGenerator.next();
		setId(entity, id);
		key2entity.put(id, entity);
		return entity;
	}

	@Override
	public <S extends E> Iterable<S> save(Iterable<S> entities) {

		for (S entity : entities) {
			save(entity);
		}

		return entities;
	}

	@Override
	public E findOne(Long id) {
		return key2entity.get(id);
	}

	@Override
	public boolean exists(Long id) {
		return key2entity.containsKey(id);
	}

	@Override
	public Iterable<E> findAll() {
		return key2entity.values();
	}

	@Override
	public Iterable<E> findAll(Iterable<Long> ids) {

		Iterable<Long> filtered = Iterables.filter(ids, new Predicate<Long>() {

			@Override
			public boolean apply(Long key) {
				return key2entity.containsKey(key);
			}
		});

		return Iterables.transform(filtered, new Function<Long, E>() {

			@Override
			public E apply(Long input) {
				return key2entity.get(input);
			}
		});
	}

	@Override
	public long count() {
		return key2entity.size();
	}

	@Override
	public void delete(Long id) {
		key2entity.remove(id);

	}

	@Override
	public void delete(E entity) {
		delete(entity.getId());
	}

	@Override
	public void delete(Iterable<? extends E> entities) {
		for (E entity : entities) {
			delete(entity);
		}
	}

	@Override
	public void deleteAll() {
		key2entity.clear();

	}

	private static void setId(AbstractEntity entity, Long id) {
		try {
			Field f = AbstractEntity.class.getDeclaredField("id");
			f.setAccessible(true);
			f.set(entity, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static class SequenceGenerator {

		private Long next = 0l;

		public Long next() {
			return ++next;
		}

	}

}
