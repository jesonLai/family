package sys.jpa.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends Repository<T,ID>,PagingAndSortingRepository<T,ID	>,JpaSpecificationExecutor<T>{}
