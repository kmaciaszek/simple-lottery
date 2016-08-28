package com.kaz.lottery.persistence;

import com.kaz.lottery.exceptions.ExceptionCode;
import com.kaz.lottery.exceptions.ServiceException;
import com.kaz.lottery.exceptions.entity.ItemAlreadyExistsExceptionEntity;
import com.kaz.lottery.exceptions.entity.ItemNotFoundExceptionEntity;
import com.kaz.lottery.model.Identified;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This is a base class for different Objects which can be stored in the InMemoryObjectRepository.
 * The repository is just a simple HashMap and it is not being persisted to disk.
 */
public abstract class AbstractInMemoryObjectRepository<T extends Identified> implements ObjectRepository<T>{

    protected Map<String, T> repo = new HashMap<>();

    @Override
    public T create(T object) {
        if (!ensureId(object) && repo.containsKey(object.getId())) {
            throw new ServiceException(new ItemAlreadyExistsExceptionEntity(ExceptionCode.ITEM_ALREADY_EXISTS,
                    "The " + object.getClass().getSimpleName() + " with the given id = " + object.getId() + " already exists."));
        }
        repo.put(object.getId(), object);
        return object;
    }

    @Override
    public T read(String id) {
        if (id == null) {
            return null;
        }
        return repo.get(id);
    }

    @Override
    public T update(T object) {
        if (StringUtils.isBlank(object.getId()) || !repo.containsKey(object.getId())) {
            throw new ServiceException(new ItemNotFoundExceptionEntity(ExceptionCode.ITEM_NOT_FOUND,
                    "The " + object.getClass().getSimpleName() + " with the given id = " + object.getId() + " does not exist."));
        }
        repo.put(object.getId(), object);
        return object;
    }

    @Override
    public boolean delete(String id) {
        return repo.remove(id) != null;
    }

    private boolean ensureId(T object) {
        if (StringUtils.isBlank(object.getId())) {
            object.setId(object.getClass().getSimpleName() + "-" + UUID.randomUUID().toString());
            return true;
        }
        return false;
    }
}
