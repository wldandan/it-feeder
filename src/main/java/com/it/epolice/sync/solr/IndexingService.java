package com.it.epolice.sync.solr;

import com.it.epolice.domain.Image;

public interface IndexingService {

    public void index(Image image);

    public void index(Iterable<Image> listings);

    public void delete(String listingId);

}
