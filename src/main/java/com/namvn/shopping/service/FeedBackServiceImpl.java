package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.FeedBack;
import com.namvn.shopping.persistence.repository.FeedBackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Autowired
    private FeedBackDao mFeedBackDao;

    @Override
    public void add(FeedBack feedBack) {
        mFeedBackDao.addQuery(feedBack);
    }
}
