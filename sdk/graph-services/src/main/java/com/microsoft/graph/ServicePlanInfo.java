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
package com.microsoft.graph;

/**
 * The type Service Plan Info.
*/
public class ServicePlanInfo extends ODataBaseEntity {

    public ServicePlanInfo(){
        setODataType("#Microsoft.Graph.ServicePlanInfo");
    }

    private java.util.UUID servicePlanId;

    /**
    * Gets the service Plan Id.
    *
    * @return the java.util.UUID
    */
    public java.util.UUID getservicePlanId() {
        return this.servicePlanId; 
    }

    /**
    * Sets the service Plan Id.
    *
    * @param value the java.util.UUID
    */
    public void setservicePlanId(java.util.UUID value) { 
        this.servicePlanId = value; 
    }

    private String servicePlanName;

    /**
    * Gets the service Plan Name.
    *
    * @return the String
    */
    public String getservicePlanName() {
        return this.servicePlanName; 
    }

    /**
    * Sets the service Plan Name.
    *
    * @param value the String
    */
    public void setservicePlanName(String value) { 
        this.servicePlanName = value; 
    }
}
