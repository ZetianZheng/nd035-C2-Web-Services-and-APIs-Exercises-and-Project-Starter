package com.udacity.pricing.domain.price;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * convert to Spring data rest client:
 */
@Repository
//public class PriceRepository {
public interface PriceRepository extends CrudRepository<Price, Long>{

}
