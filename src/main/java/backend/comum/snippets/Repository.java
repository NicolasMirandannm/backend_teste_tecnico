package backend.comum.snippets;

import backend.comum.valueObjects.UniqueIdentifier;

public interface Repository<T extends DomainEntity> {
    void save(T entity);
    void delete(UniqueIdentifier id);
    T findById(UniqueIdentifier id);
}
