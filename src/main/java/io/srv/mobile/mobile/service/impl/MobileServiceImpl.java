package io.srv.mobile.mobile.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.srv.mobile.mobile.modal.Mobile;
import io.srv.mobile.mobile.repository.MobileRespository;
import io.srv.mobile.mobile.service.MobileService;
import lombok.RequiredArgsConstructor;

/**
 * MobileServiceImpl
 * @author srvkm
 *
 */
@Service
@RequiredArgsConstructor
public class MobileServiceImpl implements MobileService {

	private final MobileRespository mobileRespository;

	/**
	 * Get list of mobiles
	 */
	@Override
	public List<Mobile> getMobiles() {
		return mobileRespository.findAll();
	}

	@Override
	public Mobile getMobileById(Long id) {
		return mobileRespository.findById(id).orElseThrow(() -> new RuntimeException("Data not found!"));
	}

	@Override
	public Mobile saveMobile(Mobile mobile) {
		return mobileRespository.save(mobile);
	}

	@Override
	public Mobile updatedMobile(Mobile mobile) {
		return mobileRespository.save(mobile);
	}

	@Override
	public void deleteMobile(Long id) {
		mobileRespository.deleteById(id);
	}
}
