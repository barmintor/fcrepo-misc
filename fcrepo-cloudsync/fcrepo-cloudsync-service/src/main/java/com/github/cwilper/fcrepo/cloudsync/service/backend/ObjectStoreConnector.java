package com.github.cwilper.fcrepo.cloudsync.service.backend;

import com.github.cwilper.fcrepo.cloudsync.api.ObjectStore;
import com.github.cwilper.fcrepo.dto.core.Datastream;
import com.github.cwilper.fcrepo.dto.core.DatastreamVersion;
import com.github.cwilper.fcrepo.dto.core.FedoraObject;

import java.io.InputStream;

public abstract class ObjectStoreConnector {

    public static final ObjectStoreConnector getInstance(ObjectStore store) {
        if (store.getType() != null && store.getType().length() > 0) {
            if (store.getType().equals("fedora")) {
                return new FedoraObjectStoreConnector(store);
            } else if (store.getType().equals("duracloud")) {
                return new DuraCloudObjectStoreConnector(store);
            } else {
                throw new IllegalArgumentException("Unrecognized ObjectStore type: " + store.getType());
            }
        } else {
            throw new IllegalArgumentException("ObjectStore type not specified");
        }
    }

    public abstract void countObjects(ObjectQuery query,
                                      ObjectCountResultHandler handler);

    public abstract void listObjects(ObjectQuery query,
                                     ObjectListResultHandler handler);

    public abstract FedoraObject getObject(String pid);

    public abstract InputStream getContent(FedoraObject o,
                                           Datastream ds,
                                           DatastreamVersion dsv);

}
