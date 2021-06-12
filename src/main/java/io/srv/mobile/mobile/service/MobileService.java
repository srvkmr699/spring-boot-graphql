package io.srv.mobile.mobile.service;

import java.util.List;

import io.srv.mobile.mobile.modal.Mobile;

/**
 * Mobile service
 * @author srvkm
 *
 */
public interface MobileService {

	/**
	 *
	 * @return
	 */
	List<Mobile> getMobiles();

	/**
	 *
	 * @param id
	 * @return
	 */
	Mobile getMobileById(Long id);

	/**
	 *
	 * @param mobile
	 * @return
	 */
	Mobile saveMobile(Mobile mobile);

	/**
	 *
	 * @param mobile
	 * @return
	 */
	Mobile updatedMobile(Mobile mobile);

	/**
	 *
	 * @param id
	 */
	void deleteMobile(Long id);
	
}
