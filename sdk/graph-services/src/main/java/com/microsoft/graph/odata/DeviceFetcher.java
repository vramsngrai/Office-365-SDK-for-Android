/*******************************************************************************
 Copyright (c) Microsoft Open Technologies, Inc. All Rights Reserved.
 Licensed under the MIT or Apache License; see LICENSE in the source repository
 root for authoritative license information.﻿

 **NOTE** This code was generated by a tool and will occasionally be
 overwritten. We welcome comments and issues regarding this code; they will be
 addressed in the generation tool. If you wish to submit pull requests, please
 do so for the templates in that tool.

 This code was generated by Vipr (https://github.com/microsoft/vipr) using
 the T4TemplateWriter (https://github.com/msopentech/vipr-t4templatewriter).
 ******************************************************************************/
package com.microsoft.graph.odata;

import com.microsoft.graph.*;
import com.google.common.util.concurrent.*;
import com.microsoft.services.odata.*;
import com.microsoft.services.odata.Readable;
import com.microsoft.services.odata.interfaces.*;

/**
 * The type  DeviceFetcher.
 */
public class DeviceFetcher extends ODataEntityFetcher<Device,DeviceOperations> 
                                     implements Readable<Device> {

     /**
     * Instantiates a new DeviceFetcher.
     *
     * @param urlComponent the url component
     * @param parent the parent
     */
     public DeviceFetcher(String urlComponent, ODataExecutable parent) {
        super(urlComponent, parent, Device.class, DeviceOperations.class);
    }

     /**
     * Add parameter.
     *
     * @param name the name
     * @param value the value
     * @return the fetcher
     */
    public DeviceFetcher addParameter(String name, Object value) {
        addCustomParameter(name, value);
        return this;
    }

     /**
     * Add header.
     *
     * @param name the name
     * @param value the value
     * @return the fetcher
     */
    public DeviceFetcher addHeader(String name, String value) {
        addCustomHeader(name, value);
        return this;
    }

        
     /**
     * Gets registered owners.
     *
     * @return the registered owners
     */
    public ODataCollectionFetcher<DirectoryObject, DirectoryObjectFetcher, DirectoryObjectCollectionOperations> getRegisteredOwners() {
        return new ODataCollectionFetcher<DirectoryObject, DirectoryObjectFetcher, DirectoryObjectCollectionOperations>("registeredOwners", this, DirectoryObject.class, DirectoryObjectCollectionOperations.class);
    }

    /**
     * Gets registered owner.
     *
     * @return the registered owner
     */
    public DirectoryObjectFetcher getRegisteredOwner(String id){
         return new ODataCollectionFetcher<DirectoryObject, DirectoryObjectFetcher, DirectoryObjectCollectionOperations>("registeredOwners", this, DirectoryObject.class, DirectoryObjectCollectionOperations.class).getById(id);
    }

     /**
     * Gets registered users.
     *
     * @return the registered users
     */
    public ODataCollectionFetcher<DirectoryObject, DirectoryObjectFetcher, DirectoryObjectCollectionOperations> getRegisteredUsers() {
        return new ODataCollectionFetcher<DirectoryObject, DirectoryObjectFetcher, DirectoryObjectCollectionOperations>("registeredUsers", this, DirectoryObject.class, DirectoryObjectCollectionOperations.class);
    }

    /**
     * Gets registered user.
     *
     * @return the registered user
     */
    public DirectoryObjectFetcher getRegisteredUser(String id){
         return new ODataCollectionFetcher<DirectoryObject, DirectoryObjectFetcher, DirectoryObjectCollectionOperations>("registeredUsers", this, DirectoryObject.class, DirectoryObjectCollectionOperations.class).getById(id);
    }

}
