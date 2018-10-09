package com.apap.tutorial5.service;

import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.repository.DealerDb;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * DealerServiceImpl
 */
@Service
@Transactional
public class DealerServiceImpl implements DealerService{
	@Autowired
	private DealerDb dealerDb;

	@Override
	public Optional<DealerModel> getDealerDetailById(Long id) {
		// TODO Auto-generated method stub
		return dealerDb.findById(id);
	}

	@Override
	public void addDealer(DealerModel dealer) {
		// TODO Auto-generated method stub
		dealerDb.save(dealer);
		
	}

	@Override
	public void deleteDealer(Long id) {
		// TODO Auto-generated method stub
			dealerDb.deleteById(id);
		}

	@Override
	public void updateDealer(Long id, String alamat, String noTelp) {
		// TODO Auto-generated method stub
		DealerModel updateDealer = dealerDb.getOne(id);
		updateDealer.setAlamat(alamat);
		updateDealer.setNoTelp(noTelp);
		dealerDb.save(updateDealer);
		
	}

	@Override
	public List<DealerModel> viewAllDealer() {
		// TODO Auto-generated method stub
		return dealerDb.findAll();
	}
	
	

}
