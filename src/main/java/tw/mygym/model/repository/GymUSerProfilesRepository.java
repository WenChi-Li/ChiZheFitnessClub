package tw.mygym.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.mygym.model.bean.GymUSerProfilesBean;


@Repository
public interface  GymUSerProfilesRepository extends JpaRepository<GymUSerProfilesBean, Integer> {

//	查詢帳號信箱
	Optional<GymUSerProfilesBean>  findByEmailAddress(String emailAddress);
	
//	查詢電話
	Optional<GymUSerProfilesBean>  findByUserPhone(String userPhone);
	
//	查詢帳號信箱與電話
	Optional<GymUSerProfilesBean>  findByEmailAddressAndUserPhone(String emailAddress, String userPhone);
	
}
