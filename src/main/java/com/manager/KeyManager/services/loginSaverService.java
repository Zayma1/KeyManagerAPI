package com.manager.KeyManager.services;

import org.springframework.stereotype.Service;

@Service
public class loginSaverService {

    int lastID = 0;

    public int getLastID() {
        return lastID;
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

}
