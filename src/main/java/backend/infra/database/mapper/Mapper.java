package backend.infra.database.mapper;

public interface Mapper<DocumentSchema, DomainEntity> {
  
  DomainEntity toDomain(DocumentSchema documentSchema);
  DocumentSchema toPersistence(DomainEntity domainEntity);
  
}
