package io.srv.mobile.mobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.srv.mobile.mobile.modal.Mobile;

/**
 * JPA Repository for CRUD operation
 * @author srvkm
 *
 */
@Repository
public interface MobileRespository extends JpaRepository<Mobile, Long>{

}
