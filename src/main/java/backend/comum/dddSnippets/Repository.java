package backend.comum.dddSnippets;

import backend.comum.valueObjects.UniqueIdentifier;

public interface Repository<T extends DomainEntity> {
    void save(T entity);
    void delete(T entity);
    void update(T entity);
    T findById(UniqueIdentifier id);
}