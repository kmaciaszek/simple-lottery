package com.kaz.lottery.persistence;

import com.kaz.lottery.model.Identified;

/**
 * This is a common interface for object repository.
 */
public interface ObjectRepository<T extends Identified> {

    /**
     * This method persists the T object.
     * @param object The object to persist.
     * @return The persisted object.
     */
    public T create(T object);

    /**
     * This method reads the T object by id.
     * @param id The id of the T object.
     * @return The T object identified by id.
     */
    public T read(String id);

    /**
     * This method updates the T object.
     * @param object The T object to update.
     * @return The updated T object.
     */
    public T update(T object);

    /**
     * This method deletes the object by id.
     * @param id The id of an object to delete.
     * @return True if there was an object with given id, otherwise false.
     */
    public boolean delete(String id);
}
