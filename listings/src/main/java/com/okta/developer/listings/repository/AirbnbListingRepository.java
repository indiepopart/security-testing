package com.okta.developer.listings.repository;

import com.okta.developer.listings.model.AirbnbListing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(collectionResourceRel = "airbnb", path="airbnb")
public interface AirbnbListingRepository extends MongoRepository<AirbnbListing, String> {

    @Override
    @PreAuthorize("hasAuthority('LISTING_create')")
    AirbnbListing save(AirbnbListing s);


}
